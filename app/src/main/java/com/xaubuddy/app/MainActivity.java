package com.xaubuddy.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {

    TextView dashboard;

    Button refreshButton;
    Button connectButton;

    MarketData marketData = new MarketData();
    StrategyEngine strategy = new StrategyEngine();
    RiskManager riskManager = new RiskManager();
    MT5Connector mt5 = new MT5Connector();
    TradeSimulator tradeSimulator;
DemoPriceSimulator demoPrice = new DemoPriceSimulator();
TradeJournal tradeJournal;
TradeStorage tradeStorage;
Handler handler = new Handler();
    
boolean tradeSaved; 
    Runnable updateTask = new Runnable() {

        @Override
        public void run() {

            loadData();

            handler.postDelayed(this,10000);

        }

    };

    private void loadData(){

        marketData.updateLiveData();
        
if(!demoPrice.running){

    demoPrice.start(
            marketData.price
    );

}
        new Handler().postDelayed(() -> {

            if(marketData.dataReady){

                strategy.analyze(marketData);
                
                riskManager.calculate(
                        marketData.price,
                        strategy.signal
                );
                if(
        strategy.signal.equals("BUY")
        ||
        strategy.signal.equals("SELL")
){

    if(
tradeSimulator.status.equals("NO TRADE")

){
        tradeSaved = false;

        tradeStorage.saveTradeSaved(false);

        tradeSimulator.openTrade(

            strategy.signal,

            marketData.price,

            riskManager.sl,

            riskManager.tp1

        );

    }

}

                double simulatedPrice =
        demoPrice.movePrice(
                strategy.signal
        );
                tradeSimulator.update(
        simulatedPrice
);
                if(
(
tradeSimulator.status.equals("TP HIT")
||
tradeSimulator.status.equals("SL HIT")
)
&&
!tradeSaved
){

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
                    tradeSimulator.resetTrade();

}

                showDashboard();

            }else{
                
                dashboard.setText(

                        "===== XAU BUDDY V5.8 =====\n\n"

                        +"STATUS: "
                        +marketData.getStatus()

                        +"\n\nWaiting Data..."

                        +"\n\nPRICE: "
                        +marketData.price

                );

            }

        },3000);

    }

    private void showDashboard(){
        
if(strategy.signal.equals("BUY")){

    dashboard.setTextColor(
            android.graphics.Color.GREEN
    );

}
else if(strategy.signal.equals("SELL")){

    dashboard.setTextColor(
            android.graphics.Color.RED
    );

}
else{

    dashboard.setTextColor(
            android.graphics.Color.WHITE
    );

}
        String time =
                java.text.DateFormat
                        .getTimeInstance()
                        .format(new java.util.Date());

        dashboard.setText(

                "===== XAU BUDDY V5.8 =====\n\n"

                +"PRICE: "
                +marketData.price

                +"\nEMA20: "
                +marketData.ema20

                +"\nEMA50: "
                +marketData.ema50

                +"\nRSI14: "
                +marketData.rsi14

                +"\n\nH1: "
                +marketData.h1Bias

                +"\nM5: "
                +marketData.m5Signal

                +"\n\nBOS: "
                +strategy.bos

                +"\nCHoCH: "
                +strategy.choch

                +"\nFVG: "
                +strategy.fvg

                +"\nLiquidity: "
                +strategy.liquidity

                +"\n\nSIGNAL: "
                +strategy.signal

                +"\nCONFIDENCE: "
                +strategy.confidence
                +"%"

                +"\n\nENTRY: "
                +riskManager.entry

                +"\nSL: "
                +riskManager.sl

                +"\nTP1: "
                +riskManager.tp1

                +"\nTP2: "
                +riskManager.tp2

                +"\nRR: "
                +riskManager.rr
            
+"\n\nTRADE STATUS: "
+tradeSimulator.status

+"\nTYPE: "
+tradeSimulator.type

+"\nTRADE ENTRY: "
+tradeSimulator.entry

+"\nCURRENT PRICE: "
+tradeSimulator.current

+"\nPROFIT: "
+tradeSimulator.profit

+"\nWIN RATE: "
+tradeSimulator.winRate
+"%"
            +"\nTOTAL TRADES: "
+tradeSimulator.totalTrades

+"\nWINS: "
+tradeSimulator.winTrades

+"\nLOSSES: "
+tradeSimulator.lossTrades

+"\nLAST RESULT: "
+tradeSimulator.lastResult
            +"\n\n===== TRADE JOURNAL ====="

+"\nTOTAL JOURNAL: "
+tradeJournal.totalTrades

+"\nJOURNAL WIN: "
+tradeJournal.wins

+"\nJOURNAL LOSS: "
+tradeJournal.losses

+"\nJOURNAL PROFIT: "
+tradeJournal.totalProfit

+"\n\nLAST TRADE:\n"
+tradeStorage.getLastTrade()
                +"\n\nBALANCE: $"
                +riskManager.balance

                +"\nRISK: "
                +riskManager.riskPercent
                +"%"

                +"\nRISK MONEY: $"
                +riskManager.riskMoney

                +"\nLOT SIZE: "
                +riskManager.lotSize
                            +"\n\n========================"

                +"\nDATA STATUS: "
                +marketData.getStatus()

                +"\nLAST UPDATE: "
                +time

                +"\nMODE: DEMO"

                +"\nVERSION: V5.8"

                +"\nMT5: "
                +mt5.getStatus()

        );

    }


    @Override
    protected void onCreate(Bundle savedInstanceState){

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
        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                30,40,30,30
        );

        TextView title =
                new TextView(this);

        title.setText(
                "XAU Buddy V5"
        );

        title.setTextSize(30);

        title.setGravity(
                Gravity.CENTER
        );

        dashboard =
                new TextView(this);

        dashboard.setTextSize(18);

        refreshButton =
                new Button(this);

        refreshButton.setText(
                "REFRESH DATA"
        );

        connectButton =
                new Button(this);

        connectButton.setText(
                "CONNECT MT5"
        );

        ScrollView scroll =
                new ScrollView(this);

        scroll.addView(dashboard);

        refreshButton.setOnClickListener(v -> {

            loadData();

        });

        connectButton.setOnClickListener(v -> {

            dashboard.setText(
                    "MT5 STATUS: "
                    + mt5.getStatus()
            );

        });

        layout.addView(title);
        layout.addView(refreshButton);
        layout.addView(connectButton);
        layout.addView(scroll);

      // DARK MODE
layout.setBackgroundColor(
        android.graphics.Color.BLACK
);

title.setTextColor(
        android.graphics.Color.WHITE
);

dashboard.setTextColor(
        android.graphics.Color.WHITE
);

refreshButton.setTextColor(
        android.graphics.Color.WHITE
);

connectButton.setTextColor(
        android.graphics.Color.WHITE
);

refreshButton.setBackgroundColor(
        android.graphics.Color.DKGRAY
);

connectButton.setBackgroundColor(
        android.graphics.Color.DKGRAY
);  
        setContentView(layout);

        handler.post(updateTask);

    }


    @Override
    protected void onDestroy(){

        super.onDestroy();

        handler.removeCallbacks(updateTask);

    }

}
