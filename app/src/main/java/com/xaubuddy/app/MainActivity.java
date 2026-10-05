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



    Handler handler = new Handler();



    Runnable updateTask = new Runnable() {


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



        new Handler().postDelayed(() -> {



            if(marketData.dataReady){



                strategy.analyze(
                        marketData
                );



                riskManager.calculate(

                        marketData.price,

                        strategy.signal

                );



                showDashboard();



            }

            else{


                dashboard.setText(

                        "===== XAU BUDDY V3 =====\n\n"

                        +"STATUS: "
                        +marketData.getStatus()

                        +"\n\nWaiting API Data..."

                        +"\n\nPRICE: "
                        +marketData.price

                );


            }



        },5000);



    }





    private void showDashboard(){



        dashboard.setText(


                "===== XAU BUDDY V3 =====\n\n"


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



                +"\n\nDATA: "
                +marketData.getStatus()



                +"\nMT5: "
                +mt5.getStatus()


        );


    }





    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);



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
                "XAU Buddy V3"
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
                    +mt5.getStatus()

            );


        });





        layout.addView(title);


        layout.addView(refreshButton);


        layout.addView(connectButton);


        layout.addView(scroll);



        setContentView(layout);



        handler.post(
                updateTask
        );


    }






    @Override
    protected void onDestroy(){


        super.onDestroy();


        handler.removeCallbacks(
                updateTask
        );


    }



}
