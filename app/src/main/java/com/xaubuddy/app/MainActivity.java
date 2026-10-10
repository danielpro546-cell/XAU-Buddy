package com.xaubuddy.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    TextView title;
    TextView marketView;
    TextView analysisView;
    TextView tradeView;
    TextView journalView;
    TextView systemView;
    TextView riskWarning;

    EditText riskInput;
    EditText serverInput;
    EditText accountInput;
    EditText passwordInput;

    Button balance10;
    Button balance50;
    Button balance100;
    Button customBalance;

    Button applyRiskButton;
    Button loginButton;
    Button uploadH1Button;
    Button uploadM5Button;
    Button analyzeButton;

    Uri h1Image;
    Uri m5Image;

    MarketData marketData = new MarketData();
    StrategyEngine strategy = new StrategyEngine();
    RiskManager riskManager = new RiskManager();
    MT5Connector mt5 = new MT5Connector();
    ScreenshotAnalyzer h1Analyzer = new ScreenshotAnalyzer();
ScreenshotAnalyzer m5Analyzer = new ScreenshotAnalyzer();
    DecisionEngine decision = new DecisionEngine();

    TradeStorage tradeStorage;
    TradeJournal tradeJournal;
    TradeSimulator tradeSimulator;

    Handler handler = new Handler();
@Override
protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    tradeStorage = new TradeStorage(this);
    tradeJournal = new TradeJournal(tradeStorage);
    tradeSimulator = new TradeSimulator(tradeStorage);

    LinearLayout root = new LinearLayout(this);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setBackgroundColor(Color.BLACK);

    ScrollView scroll = new ScrollView(this);
    scroll.setFillViewport(true);

    LinearLayout layout = new LinearLayout(this);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(24,24,24,24);

    scroll.addView(layout);

    title = new TextView(this);
    title.setText("XAU Buddy V7");
    title.setTextColor(Color.WHITE);
    title.setTextSize(28);
    title.setGravity(Gravity.CENTER);

    layout.addView(title);

    marketView = new TextView(this);
    marketView.setTextColor(Color.CYAN);
    marketView.setTextSize(16);

    analysisView = new TextView(this);
    analysisView.setTextColor(Color.GREEN);
    analysisView.setTextSize(16);

    tradeView = new TextView(this);
    tradeView.setTextColor(Color.YELLOW);
    tradeView.setTextSize(16);

    journalView = new TextView(this);
    journalView.setTextColor(Color.WHITE);
    journalView.setTextSize(16);

    systemView = new TextView(this);
    systemView.setTextColor(Color.LTGRAY);
    systemView.setTextSize(14);

    riskWarning = new TextView(this);
    riskWarning.setTextColor(Color.RED);
// ================= BALANCE =================

TextView balanceTitle = new TextView(this);
balanceTitle.setText("Select Demo Balance (USD)");
balanceTitle.setTextColor(Color.WHITE);
layout.addView(balanceTitle);

LinearLayout balanceRow = new LinearLayout(this);
balanceRow.setOrientation(LinearLayout.HORIZONTAL);

balance10 = new Button(this);
balance10.setText("$10");

balance50 = new Button(this);
balance50.setText("$50");

balance100 = new Button(this);
balance100.setText("$100");

balanceRow.addView(balance10,
        new LinearLayout.LayoutParams(0,-2,1));

balanceRow.addView(balance50,
        new LinearLayout.LayoutParams(0,-2,1));

balanceRow.addView(balance100,
        new LinearLayout.LayoutParams(0,-2,1));

layout.addView(balanceRow);

customBalance = new Button(this);
customBalance.setText("CUSTOM");
layout.addView(customBalance);

// ================= RISK =================

TextView riskTitle = new TextView(this);
riskTitle.setText("Risk Percentage (0.01%-10%)");
riskTitle.setTextColor(Color.WHITE);
layout.addView(riskTitle);

riskInput = new EditText(this);
riskInput.setHint("0.50");
riskInput.setText("0.50");
riskInput.setTextColor(Color.WHITE);
riskInput.setHintTextColor(Color.GRAY);
layout.addView(riskInput);

applyRiskButton = new Button(this);
applyRiskButton.setText("APPLY RISK %");
layout.addView(applyRiskButton);

layout.addView(riskWarning);
// ================= MT5 LOGIN =================

TextView mt5Title = new TextView(this);
mt5Title.setText("MT5 LOGIN");
mt5Title.setTextColor(Color.YELLOW);
mt5Title.setTextSize(18);
layout.addView(mt5Title);

serverInput = new EditText(this);
serverInput.setHint("MT5 Server");
serverInput.setTextColor(Color.WHITE);
serverInput.setHintTextColor(Color.LTGRAY);
layout.addView(serverInput);

accountInput = new EditText(this);
accountInput.setHint("MT5 Account");
accountInput.setTextColor(Color.WHITE);
accountInput.setHintTextColor(Color.LTGRAY);
layout.addView(accountInput);

passwordInput = new EditText(this);
passwordInput.setHint("MT5 Password");
passwordInput.setTextColor(Color.WHITE);
passwordInput.setHintTextColor(Color.LTGRAY);
layout.addView(passwordInput);

loginButton = new Button(this);
loginButton.setText("LOGIN MT5");
layout.addView(loginButton);

// ================= SCREENSHOT =================

TextView screenTitle = new TextView(this);
screenTitle.setText("SCREENSHOT ANALYSIS");
screenTitle.setTextColor(Color.YELLOW);
screenTitle.setTextSize(18);
layout.addView(screenTitle);

uploadH1Button = new Button(this);
uploadH1Button.setText("UPLOAD H1");
layout.addView(uploadH1Button);

uploadM5Button = new Button(this);
uploadM5Button.setText("UPLOAD M5");
layout.addView(uploadM5Button);

analyzeButton = new Button(this);
analyzeButton.setText("ANALYZE");
layout.addView(analyzeButton);
// ================= DASHBOARD =================

marketView.setText(
        "===== MARKET DATA =====\n\n"
      + "PRICE : WAITING...\n"
      + "EMA20 : WAITING...\n"
      + "EMA50 : WAITING...\n"
      + "RSI14 : WAITING...\n"
      + "H1 : WAITING...\n"
      + "M5 : WAITING..."
);

analysisView.setText(
        "===== AI ANALYSIS =====\n\n"
      + "BOS : WAITING...\n"
      + "CHoCH : WAITING...\n"
      + "FVG : WAITING...\n"
      + "Liquidity : WAITING...\n"
      + "Signal : WAITING...\n"
      + "Confidence : 0%"
);

tradeView.setText(
        "===== TRADE =====\n\n"
      + "Entry : -\n"
      + "SL : -\n"
      + "TP1 : -\n"
      + "TP2 : -\n"
      + "Lot : -"
);

journalView.setText(
        "===== JOURNAL =====\n\n"
      + "Trades : 0\n"
      + "Wins : 0\n"
      + "Losses : 0\n"
      + "Profit : 0"
);

systemView.setText(
        "===== SYSTEM =====\n\n"
      + "Mode : DEMO\n"
      + "MT5 : DISCONNECTED"
);

layout.addView(marketView);
layout.addView(analysisView);
layout.addView(tradeView);
layout.addView(journalView);
layout.addView(systemView);

root.addView(scroll);
setContentView(root);
 refreshDashboard();   
// ================= BUTTON EVENTS =================

balance10.setOnClickListener(v -> {
    riskManager.setBalance(10);
    refreshDashboard();
});

balance50.setOnClickListener(v -> {
    riskManager.setBalance(50);
    refreshDashboard();
});

balance100.setOnClickListener(v -> {
    riskManager.setBalance(100);
    refreshDashboard();
});

customBalance.setOnClickListener(v -> {

    EditText input = new EditText(this);
    input.setHint("Balance");

    new AlertDialog.Builder(this)
            .setTitle("Custom Balance")
            .setView(input)
            .setPositiveButton("OK", (d, w) -> {

                try {

                    double b = Double.parseDouble(
                            input.getText().toString()
                    );

                    riskManager.setBalance(b);
                    refreshDashboard();

                } catch (Exception e) {

                    Toast.makeText(
                            this,
                            "Invalid Balance",
                            Toast.LENGTH_SHORT
                    ).show();

                }

            })
            .setNegativeButton("Cancel", null)
            .show();

});

applyRiskButton.setOnClickListener(v -> {

    try {

        double r = Double.parseDouble(
                riskInput.getText().toString()
        );

        riskManager.setRiskPercent(r);
        refreshDashboard();

    } catch (Exception e) {

        Toast.makeText(
                this,
                "Invalid Risk",
                Toast.LENGTH_SHORT
        ).show();

    }

});

loginButton.setOnClickListener(v -> {

    mt5.connect(

            serverInput.getText().toString(),
            accountInput.getText().toString(),
            passwordInput.getText().toString(),
            false

    );

    refreshDashboard();

});

uploadH1Button.setOnClickListener(v -> {

    Intent intent = new Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    );

    startActivityForResult(intent, 100);

});

uploadM5Button.setOnClickListener(v -> {

    Intent intent = new Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    );

    startActivityForResult(intent, 200);

});

analyzeButton.setOnClickListener(v -> {

    h1Analyzer.analyze(this, h1Image, "H1");
m5Analyzer.analyze(this, m5Image, "M5");

    refreshDashboard();

});

}

private void refreshDashboard() {

    marketData.updateLiveData();

    strategy.analyze(marketData);
    decision.decide(

        h1Analyzer.trend,

        m5Analyzer.trend,

        marketData.ema20,

        marketData.ema50,

        marketData.rsi14,

        m5Analyzer.bos,

        m5Analyzer.choch,

        m5Analyzer.fvg,

        m5Analyzer.liquidity

);
    riskManager.calculate(
        marketData.price,
        decision.signal
);

    marketView.setText(
        "===== MARKET DATA =====\n\n"
        + "PRICE : " + marketData.price
        + "\nEMA20 : " + marketData.ema20
        + "\nEMA50 : " + marketData.ema50
        + "\nRSI14 : " + marketData.rsi14
        + "\nH1 : " + marketData.h1Bias
        + "\nM5 : " + marketData.m5Signal
        + "\nBalance : $" + riskManager.balance
        + "\nRisk % : " + riskManager.riskPercent
);       

    analysisView.setText(
        "===== AI ANALYSIS =====\n\n"
        + "\nH1 Trend : " + h1Analyzer.trend
+ "\nM5 Trend : " + m5Analyzer.trend
+ "\nBOS : " + m5Analyzer.bos
+ "\nCHoCH : " + m5Analyzer.choch
+ "\nFVG : " + m5Analyzer.fvg
+ "\nLiquidity : " + m5Analyzer.liquidity
+ "\nSignal : " + m5Analyzer.signal
+ "\nConfidence : " + m5Analyzer.confidence + "%"
        + "\nAI FINAL : " + decision.signal
+ "\nReason : " + decision.reason
);
    tradeView.setText(
        "===== TRADE =====\n\n"
        + "Entry : " + (decision.signal.equals("WAIT") ? "-" : riskManager.entry)
+ "\nSL : " + (decision.signal.equals("WAIT") ? "-" : riskManager.sl)
+ "\nTP1 : " + (decision.signal.equals("WAIT") ? "-" : riskManager.tp1)
+ "\nTP2 : " + (decision.signal.equals("WAIT") ? "-" : riskManager.tp2)
        + "\nLot : " + riskManager.lotSize
        + "\nPlanned Risk : $" + riskManager.riskMoney
        + "\nActual Risk : $" + riskManager.actualRiskMoney
        + "\nRR : " + riskManager.rr
);
    journalView.setText(
            "===== JOURNAL =====\n\n"
            + "Trades : " + tradeJournal.totalTrades
            + "\nWins : " + tradeJournal.wins
            + "\nLosses : " + tradeJournal.losses
            + "\nProfit : " + tradeJournal.totalProfit
    );

    systemView.setText(
            "===== SYSTEM =====\n\n"
            + "MT5 : " + mt5.getStatus()
            + "\nMode : DEMO"
    );

    if (riskManager.canTrade) {
        riskWarning.setText("Risk Check : PASSED");
        riskWarning.setTextColor(Color.GREEN);
    } else {
        riskWarning.setText(riskManager.warning);
        riskWarning.setTextColor(Color.RED);
    }

}

@Override
protected void onActivityResult(
        int requestCode,
        int resultCode,
        Intent data
) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK && data != null) {

        if (requestCode == 100) {
            h1Image = data.getData();
            Toast.makeText(
                    this,
                    "H1 Screenshot Selected",
                    Toast.LENGTH_SHORT
            ).show();
        }

        if (requestCode == 200) {
            m5Image = data.getData();
            Toast.makeText(
                    this,
                    "M5 Screenshot Selected",
                    Toast.LENGTH_SHORT
            ).show();
        }

    }
}
}
