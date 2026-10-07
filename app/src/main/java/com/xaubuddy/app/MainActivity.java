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

    DemoPriceSimulator demoPrice =
            new DemoPriceSimulator();


    TradeJournal tradeJournal;

    TradeStorage tradeStorage;


    Handler handler = new Handler();



    boolean tradeSaved = false;


    boolean waitingNewSetup = false;


    long lastTradeCloseTime = 0;


    long tradeCooldown = 30000;



    String lastTradeSignal = "";



    Runnable updateTask =
            new Runnable() {

        @Override
        public void run() {

            loadData();

            handler.postDelayed(
                    this,
                    10000
            );

        }

    };




    private void loadData(){


        marketData.updateLiveData();



        if(!demoPrice.running){

            demoPrice.start(
                    marketData.price
            );

        }



        new Handler().postDelayed(
                () -> {


            if(marketData.dataReady){



                strategy.analyze(
                        marketData
                );



                riskManager.calculate(

                        marketData.price,

                        strategy.signal

                );



                // =====================
                // NEW SETUP DETECTION
                // =====================

                if(waitingNewSetup){


                    if(
                    marketData.m5Signal.equals("BUY SETUP")
                    ||
                    marketData.m5Signal.equals("SELL SETUP")
                    ){

                        waitingNewSetup = false;

                    }

                }



                checkTradeEntry();



                double simulatedPrice =
                        demoPrice.movePrice(
                                strategy.signal
                        );



                tradeSimulator.update(
                        simulatedPrice
                );



                checkTradeClose();



                showDashboard();



            }
            else{


                dashboard.setText(

                        "===== XAU BUDDY V5.9 =====\n\n"

                        +"STATUS: "
                        +marketData.getStatus()

                        +"\n\nWaiting Data..."

                        +"\nPRICE: "
                        +marketData.price

                );


            }



        },3000);


    }
    private void checkTradeEntry(){


        if(
            strategy.signal.equals("BUY")
            ||
            strategy.signal.equals("SELL")
        ){


            if(
                strategy.confidence >= 70
                &&
                !waitingNewSetup
                &&
                tradeSimulator.status.equals("NO TRADE")
                &&
                System.currentTimeMillis()
                -
                lastTradeCloseTime
                >=
                tradeCooldown
            ){


                if(
                    (strategy.signal.equals("BUY")
                    &&
                    marketData.h1Bias.equals("BULLISH")
                    &&
                    marketData.m5Signal.equals("BUY SETUP"))

                    ||

                    (strategy.signal.equals("SELL")
                    &&
                    marketData.h1Bias.equals("BEARISH")
                    &&
                    marketData.m5Signal.equals("SELL SETUP"))
                ){


                    tradeSaved = false;


                    tradeStorage.saveTradeSaved(false);



                    lastTradeSignal =
                            strategy.signal;



                    tradeSimulator.openTrade(

                            strategy.signal,

                            marketData.price,

                            riskManager.sl,

                            riskManager.tp1

                    );


                }


            }


        }


    }





    private void checkTradeClose(){



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



            tradeStorage.saveTradeSaved(
                    true
            );



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



            lastTradeCloseTime =
                    System.currentTimeMillis();



        }


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
                .format(
                    new java.util.Date()
                );



        dashboard.setText(


        "===== XAU BUDDY V5.9 =====\n\n"


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


        +"\nENTRY PRICE: "
        +tradeSimulator.entry


        +"\nCURRENT: "
        +tradeSimulator.current


        +"\nPROFIT: "
        +tradeSimulator.profit



        +"\n\nWIN RATE: "
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



        +"\n\n===== JOURNAL ====="


        +"\nTOTAL: "
        +tradeJournal.totalTrades


        +"\nWIN: "
        +tradeJournal.wins


        +"\nLOSS: "
        +tradeJournal.losses


        +"\nPROFIT: "
        +tradeJournal.totalProfit



        +"\n\nLAST TRADE:\n"
        +tradeStorage.getLastTrade()



        +"\n\nBALANCE: $"
        +riskManager.balance


        +"\nRISK: "
        +riskManager.riskPercent
        +"%"


        +"\nLOT: "
        +riskManager.lotSize



        +"\n\nDATA: "
        +marketData.getStatus()


        +"\nMODE: DEMO"


        +"\nVERSION: V5.9"


        +"\nMT5: "
        +mt5.getStatus()


        +"\nWAITING NEW SETUP: "
        +waitingNewSetup


        +"\n\nLAST UPDATE: "
        +time


        );


    }
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);


        tradeStorage =
                new TradeStorage(this);


        tradeJournal =
                new TradeJournal(
                        tradeStorage
                );


        tradeSaved =
                tradeStorage.getTradeSaved();


        tradeSimulator =
                new TradeSimulator(
                        tradeStorage
                );



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
                30,
                40,
                30,
                30
        );



        TextView title =
                new TextView(this);


        title.setText(
                "XAU Buddy V5.9"
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


        scroll.addView(
                dashboard
        );



        refreshButton.setOnClickListener(v -> {

            loadData();

        });



        connectButton.setOnClickListener(v -> {


            dashboard.setText(

                    "MT5 STATUS: "
                    +
                    mt5.getStatus()

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


        handler.removeCallbacks(
                updateTask
        );

    }


}
