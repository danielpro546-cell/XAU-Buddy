package com.xaubuddy.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

public class MainActivity extends Activity {

    TextView dashboard;
    TextView riskWarning;

    EditText riskInput;

    Button refreshButton;
    Button connectButton;
    EditText serverInput;
EditText accountInput;
EditText passwordInput;

Button loginButton;
    Button applyRiskButton;
    Button customBalanceButton;
    Button uploadH1Button;
Button uploadM5Button;
Button analyzeButton;

Uri h1Image;
Uri m5Image;

    MarketData marketData = new MarketData();
    StrategyEngine strategy = new StrategyEngine();
    RiskManager riskManager = new RiskManager();
    MT5Connector mt5 = new MT5Connector();

    TradeSimulator tradeSimulator;
    DemoPriceSimulator demoPrice = new DemoPriceSimulator();

    TradeJournal tradeJournal;
    TradeStorage tradeStorage;

    Handler handler = new Handler();

    boolean tradeSaved = false;
    boolean waitingNewSetup = false;
    boolean candleTraded = false;

    long lastTradeCloseTime = 0;
    long tradeCooldown = 30000;

    String lastSignal = "";

    Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            loadData();
            handler.postDelayed(this, 10000);
        }
    };

    private void loadData() {

        marketData.updateLiveData();

        if (!demoPrice.running) {
            demoPrice.start(marketData.price);
        }

        if (marketData.dataReady) {

            strategy.analyze(marketData);

            riskManager.calculate(
                    marketData.price,
                    strategy.signal
            );

            checkNewSetup();
            checkEntry();

            double price = demoPrice.movePrice(
                    strategy.signal
            );

            tradeSimulator.update(price);

            checkClose();
            showDashboard();

        } else {
            dashboard.setText("WAITING DATA...");
        }
    }

    private void checkNewSetup() {

        if (
                marketData.m5Signal.equals("BUY SETUP")
                || marketData.m5Signal.equals("SELL SETUP")
        ) {
            if (!marketData.m5Signal.equals(lastSignal)) {
                waitingNewSetup = false;
                candleTraded = false;
            }
        }
    }

    private void checkEntry() {

        if (
                strategy.signal.equals("BUY")
                || strategy.signal.equals("SELL")
        ) {

            if (
                    strategy.confidence >= 70
                    && riskManager.canTrade
                    && tradeSimulator.status.equals("NO TRADE")
                    && !waitingNewSetup
                    && !candleTraded
                    && System.currentTimeMillis()
                    - lastTradeCloseTime >= tradeCooldown
            ) {

                if (strategy.signal.equals(lastSignal)) {
                    return;
                }

                tradeSaved = false;
                tradeStorage.saveTradeSaved(false);

                tradeSimulator.openTrade(
                        strategy.signal,
                        marketData.price,
                        riskManager.sl,
                        riskManager.tp1
                );

                lastSignal = strategy.signal;
                candleTraded = true;
            }
        }
    }

    private void checkClose() {

        if (
                (
                        tradeSimulator.status.equals("TP HIT")
                        || tradeSimulator.status.equals("SL HIT")
                )
                && !tradeSaved
        ) {

            tradeSaved = true;

            tradeJournal.addTrade(
                    tradeSimulator.type,
                    tradeSimulator.entry,
                    tradeSimulator.current,
                    tradeSimulator.profit,
                    tradeSimulator.lastResult
            );

            tradeStorage.saveLastTrade(
                    tradeJournal.getLastTrade()
            );

            tradeStorage.saveTradeSaved(true);

            tradeStorage.saveStats(
                    tradeJournal.totalTrades,
                    tradeJournal.wins,
                    tradeJournal.losses,
                    tradeJournal.totalProfit
            );

            tradeSimulator.totalTrades =
                    tradeJournal.totalTrades;

            tradeSimulator.winTrades =
                    tradeJournal.wins;

            tradeSimulator.lossTrades =
                    tradeJournal.losses;

            tradeSimulator.calculateWinRate();
            tradeSimulator.resetTrade();

            waitingNewSetup = true;
            lastTradeCloseTime = System.currentTimeMillis();
        }
    }

    private void setBalance(double value) {

        riskManager.setBalance(value);

        riskManager.calculate(
                marketData.price,
                strategy.signal
        );

        showDashboard();
    }

    private void showCustomBalanceDialog() {

        EditText input = new EditText(this);
        input.setInputType(8194);
        input.setHint("Enter balance in USD");

        new AlertDialog.Builder(this)
                .setTitle("Custom Balance")
                .setMessage("Enter a balance greater than zero.")
                .setView(input)
                .setNegativeButton("CANCEL", null)
                .setPositiveButton("SET", (dialog, which) -> {
                    try {
                        double value = Double.parseDouble(
                                input.getText().toString().trim()
                        );

                        if (value <= 0 || Double.isNaN(value)
                                || Double.isInfinite(value)) {
                            Toast.makeText(
                                    this,
                                    "Enter a valid positive balance",
                                    Toast.LENGTH_LONG
                            ).show();
                            return;
                        }

                        setBalance(value);

                    } catch (Exception e) {
                        Toast.makeText(
                                this,
                                "Invalid balance",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                })
                .show();
    }

    private void applyRiskSettings() {

        try {
            double value = Double.parseDouble(
                    riskInput.getText().toString().trim()
            );

            if (value <= 0 || value > 10
                    || Double.isNaN(value)
                    || Double.isInfinite(value)) {
                Toast.makeText(
                        this,
                        "Risk must be greater than 0 and at most 10%",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            riskManager.setRiskPercent(value);

            riskManager.calculate(
                    marketData.price,
                    strategy.signal
            );

            showDashboard();

            Toast.makeText(
                    this,
                    "Risk setting updated",
                    Toast.LENGTH_SHORT
            ).show();

        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Enter a valid risk percentage",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void showDashboard() {

        String time = java.text.DateFormat
                .getTimeInstance()
                .format(new java.util.Date());

        String riskStatus;

        if (riskManager.canTrade) {
            riskStatus = "RISK CHECK: PASSED";
        } else {
            riskStatus = "RISK CHECK: BLOCKED";
        }

        dashboard.setText(
                "===== XAU BUDDY V6.1 =====\n\n"

                + "PRICE: " + marketData.price
                + "\nEMA20: " + marketData.ema20
                + "\nEMA50: " + marketData.ema50
                + "\nRSI14: " + marketData.rsi14

                + "\n\nH1: " + marketData.h1Bias
                + "\nM5: " + marketData.m5Signal

                + "\n\nBOS: " + strategy.bos
                + "\nCHoCH: " + strategy.choch
                + "\nFVG: " + strategy.fvg
                + "\nLiquidity: " + strategy.liquidity

                + "\n\nSIGNAL: " + strategy.signal
                + "\nCONFIDENCE: " + strategy.confidence + "%"

                + "\n\nENTRY: " + riskManager.entry
                + "\nSL: " + riskManager.sl
                + "\nTP1: " + riskManager.tp1
                + "\nTP2: " + riskManager.tp2
                + "\nRR: " + riskManager.rr

                + "\n\nBALANCE: $" + money(riskManager.balance)
                + "\nPLANNED RISK: " + riskManager.riskPercent + "%"
                + "\nRISK AMOUNT: $" + money(riskManager.riskMoney)
                + "\nLOT SIZE: " + String.format(
                        java.util.Locale.US,
                        "%.2f",
                        riskManager.lotSize
                )
                + "\nESTIMATED ACTUAL RISK: $"
                + money(riskManager.actualRiskMoney)

                + "\n" + riskStatus
                + "\nWARNING: " + riskManager.warning

                + "\n\nTRADE STATUS: " + tradeSimulator.status
                + "\nTYPE: " + tradeSimulator.type
                + "\nTRADE ENTRY: " + tradeSimulator.entry
                + "\nCURRENT: " + tradeSimulator.current
                + "\nPROFIT: " + tradeSimulator.profit

                + "\n\nWIN RATE: " + tradeSimulator.winRate + "%"
                + "\nTOTAL TRADES: " + tradeSimulator.totalTrades
                + "\nWINS: " + tradeSimulator.winTrades
                + "\nLOSSES: " + tradeSimulator.lossTrades

                + "\n\n===== JOURNAL ====="
                + "\nTOTAL: " + tradeJournal.totalTrades
                + "\nWIN: " + tradeJournal.wins
                + "\nLOSS: " + tradeJournal.losses
                + "\nPROFIT: " + tradeJournal.totalProfit

                + "\n\nDATA: " + marketData.getStatus()
                + "\nMODE: DEMO ONLY"
                + "\nVERSION: V6.1"
                + "\nMT5: " + mt5.getStatus()
                + "\nWAITING NEW SETUP: " + waitingNewSetup
                + "\n\nUPDATE: " + time
        );

        if (riskManager.canTrade) {
            riskWarning.setText("Risk check passed");
            riskWarning.setTextColor(
                    android.graphics.Color.GREEN
            );
        } else {
            riskWarning.setText(
                    "TRADE BLOCKED\n" + riskManager.warning
            );
            riskWarning.setTextColor(
                    android.graphics.Color.RED
            );
        }
    }

    private String money(double value) {
        return String.format(
                java.util.Locale.US,
                "%.2f",
                value
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        tradeStorage = new TradeStorage(this);
        tradeJournal = new TradeJournal(tradeStorage);

        tradeSaved = tradeStorage.getTradeSaved();

        tradeSimulator = new TradeSimulator(tradeStorage);

        tradeJournal.loadStats(
                tradeStorage.getTotal(),
                tradeStorage.getWins(),
                tradeStorage.getLosses(),
                tradeStorage.getProfit()
        );

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 24, 30, 24);
        layout.setBackgroundColor(
                android.graphics.Color.BLACK
        );

        TextView title = new TextView(this);
        title.setText("XAU Buddy V6.1");
        title.setTextSize(28);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(
                android.graphics.Color.WHITE
        );

        TextView balanceLabel = new TextView(this);
        balanceLabel.setText("Select Demo Balance (USD)");
        balanceLabel.setTextColor(
                android.graphics.Color.WHITE
        );

        LinearLayout balanceRow = new LinearLayout(this);
        balanceRow.setOrientation(LinearLayout.HORIZONTAL);

        Button b10 = new Button(this);
        b10.setText("$10");
        Button b50 = new Button(this);
        b50.setText("$50");
        Button b100 = new Button(this);
        b100.setText("$100");

        customBalanceButton = new Button(this);
        customBalanceButton.setText("CUSTOM");

        balanceRow.addView(b10, new LinearLayout.LayoutParams(
                0, -2, 1
        ));
        balanceRow.addView(b50, new LinearLayout.LayoutParams(
                0, -2, 1
        ));
        balanceRow.addView(b100, new LinearLayout.LayoutParams(
                0, -2, 1
        ));

        LinearLayout customRow = new LinearLayout(this);
        customRow.addView(customBalanceButton,
                new LinearLayout.LayoutParams(-1, -2));

        b10.setOnClickListener(v -> setBalance(10));
        b50.setOnClickListener(v -> setBalance(50));
        b100.setOnClickListener(v -> setBalance(100));
        customBalanceButton.setOnClickListener(
                v -> showCustomBalanceDialog()
        );

        TextView riskLabel = new TextView(this);
        riskLabel.setText("Risk Percentage (0.01% - 10%)");
        riskLabel.setTextColor(
                android.graphics.Color.WHITE
        );

        riskInput = new EditText(this);
        riskInput.setSingleLine(true);
        riskInput.setInputType(8194);
        riskInput.setText(
                String.valueOf(riskManager.riskPercent)
        );
        riskInput.setHint("Example: 0.5");
        riskInput.setTextColor(
                android.graphics.Color.WHITE
        );
        riskInput.setHintTextColor(
                android.graphics.Color.LTGRAY
        );

        applyRiskButton = new Button(this);
        applyRiskButton.setText("APPLY RISK %");
        applyRiskButton.setOnClickListener(
                v -> applyRiskSettings()
        );

        riskWarning = new TextView(this);
        riskWarning.setTextColor(
                android.graphics.Color.RED
        );
        riskWarning.setTextSize(14);

        refreshButton = new Button(this);
        refreshButton.setText("REFRESH DATA");
        refreshButton.setOnClickListener(v -> loadData());

        connectButton = new Button(this);
        connectButton.setText("CHECK MT5 STATUS");
        connectButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("MT5 Connection")
                    .setMessage(
                            mt5.getStatus()
                            + "\n\nThis button does not connect to a real MT5 account."
                    )
                    .setPositiveButton("OK", null)
                    .show();
        });
        serverInput = new EditText(this);
serverInput.setHint("MT5 Server");

accountInput = new EditText(this);
accountInput.setHint("MT5 Account");

passwordInput = new EditText(this);
passwordInput.setHint("MT5 Password");
passwordInput.setInputType(
        android.text.InputType.TYPE_CLASS_TEXT
        | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
);
serverInput.setTextColor(android.graphics.Color.WHITE);
serverInput.setHintTextColor(android.graphics.Color.LTGRAY);

accountInput.setTextColor(android.graphics.Color.WHITE);
accountInput.setHintTextColor(android.graphics.Color.LTGRAY);

passwordInput.setTextColor(android.graphics.Color.WHITE);
passwordInput.setHintTextColor(android.graphics.Color.LTGRAY);
loginButton = new Button(this);
loginButton.setText("LOGIN MT5");
uploadH1Button = new Button(this);
uploadH1Button.setText("UPLOAD H1 SCREENSHOT");


uploadM5Button = new Button(this);
uploadM5Button.setText("UPLOAD M5 SCREENSHOT");


analyzeButton = new Button(this);
analyzeButton.setText("ANALYZE");
uploadH1Button.setOnClickListener(v -> {

    Intent intent = new Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    );

    startActivityForResult(intent, 100);

});        

loginButton.setOnClickListener(v -> {

    mt5.connect(
    serverInput.getText().toString(),
    accountInput.getText().toString(),
    passwordInput.getText().toString(),
    false
);
    showDashboard();

});

        dashboard = new TextView(this);
        dashboard.setTextSize(16);
        dashboard.setTextColor(
                android.graphics.Color.WHITE
        );

        ScrollView scroll = new ScrollView(this);
        scroll.addView(dashboard);

        layout.addView(title);
        layout.addView(balanceLabel);
        layout.addView(balanceRow);
        layout.addView(customRow);
        layout.addView(riskLabel);
        layout.addView(riskInput);
        layout.addView(applyRiskButton);
        layout.addView(riskWarning);
        layout.addView(refreshButton);
        layout.addView(connectButton);
        layout.addView(serverInput);
layout.addView(accountInput);
layout.addView(passwordInput);
layout.addView(loginButton);
layout.addView(uploadH1Button);
layout.addView(uploadM5Button);
layout.addView(analyzeButton);      

        layout.addView(scroll, new LinearLayout.LayoutParams(
                -1, 0, 1
        ));

        setContentView(layout);

        handler.post(updateTask);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTask);
    }
}
