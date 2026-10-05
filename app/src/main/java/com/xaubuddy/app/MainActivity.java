package com.xaubuddy.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.*;


public class MainActivity extends Activity {


    TextView dashboard;

    EditText riskInput;
    EditText serverInput;
    EditText accountInput;
    EditText passwordInput;


    Button calculateButton;
    Button connectButton;


    double balance = 10;


    MarketData marketData = new MarketData();

    StrategyEngine strategy = new StrategyEngine();

    RiskManager riskManager = new RiskManager();

    MT5Connector mt5 = new MT5Connector();



    Handler autoHandler = new Handler();



    Runnable autoUpdate = new Runnable() {


        @Override
        public void run() {


            marketData.updateLiveData();



            new Handler().postDelayed(() -> {


                if(marketData.dataReady){



                    strategy.analyze(marketData);



                    if(
                            strategy.signal.equals("BUY")
                            ||
                            strategy.signal.equals("SELL")
                    ){


                        riskManager.calculate(

                                marketData.price,

                                strategy.signal

                        );


                    }



                    updateDashboard();



                }
                else{


                    dashboard.setText(

                            "===== XAU BUDDY =====\n\n"
                            +"DATA LOADING..."

                    );


                }



            },5000);



            autoHandler.postDelayed(this,10000);



        }


    };





    private void updateDashboard(){



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



                +"\n\nMT5: "
                +mt5.getStatus()


        );

    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);



        LinearLayout mainLayout = new LinearLayout(this);

        mainLayout.setOrientation(
                LinearLayout.VERTICAL
        );

        mainLayout.setPadding(
                30,40,30,30
        );



        TextView title = new TextView(this);

        title.setText(
                "XAU Buddy V3"
        );

        title.setTextSize(30);

        title.setGravity(
                Gravity.CENTER
        );



        TextView mode = new TextView(this);

        mode.setText(
                "MODE: DEMO\nLIVE: LOCKED"
        );

        mode.setTextSize(20);




        TextView balanceText = new TextView(this);

        balanceText.setText(
                "Balance: $10"
        );

        balanceText.setTextSize(20);




        Button b10 = new Button(this);
        b10.setText("$10");


        Button b50 = new Button(this);
        b50.setText("$50");


        Button b100 = new Button(this);
        b100.setText("$100");



        LinearLayout balanceLayout =
                new LinearLayout(this);


        balanceLayout.addView(b10);

        balanceLayout.addView(b50);

        balanceLayout.addView(b100);




        riskInput = new EditText(this);

        riskInput.setHint(
                "Risk %"
        );

        riskInput.setText(
                "0.5"
        );

        riskInput.setInputType(2);




        serverInput = new EditText(this);

        serverInput.setHint(
                "MT5 Server"
        );



        accountInput = new EditText(this);

        accountInput.setHint(
                "MT5 Account"
        );



        passwordInput = new EditText(this);

        passwordInput.setHint(
                "MT5 Password"
        );

        passwordInput.setInputType(129);




        connectButton = new Button(this);

        connectButton.setText(
                "CONNECT MT5"
        );



        calculateButton = new Button(this);

        calculateButton.setText(
                "CALCULATE"
        );



        dashboard = new TextView(this);

        dashboard.setTextSize(18);



        ScrollView scroll =
                new ScrollView(this);

        scroll.addView(dashboard);





        connectButton.setOnClickListener(v -> {


            mt5.connect(

                    serverInput.getText().toString(),

                    accountInput.getText().toString(),

                    passwordInput.getText().toString()

            );



            dashboard.setText(

                    "MT5 STATUS: "
                    +mt5.getStatus()

            );


        });






        calculateButton.setOnClickListener(v -> {


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



                    updateDashboard();


                }
                else{


                    dashboard.setText(

                            "DATA LOADING..."

                    );


                }



            },5000);



        });






        b10.setOnClickListener(v -> {

            balance = 10;

            balanceText.setText(
                    "Balance: $10"
            );

        });



        b50.setOnClickListener(v -> {

            balance = 50;

            balanceText.setText(
                    "Balance: $50"
            );

        });



        b100.setOnClickListener(v -> {

            balance = 100;

            balanceText.setText(
                    "Balance: $100"
            );

        });







        mainLayout.addView(title);

        mainLayout.addView(mode);

        mainLayout.addView(balanceText);

        mainLayout.addView(balanceLayout);

        mainLayout.addView(riskInput);

        mainLayout.addView(serverInput);

        mainLayout.addView(accountInput);

        mainLayout.addView(passwordInput);

        mainLayout.addView(connectButton);

        mainLayout.addView(calculateButton);

        mainLayout.addView(scroll);



        setContentView(mainLayout);



        autoHandler.post(
                autoUpdate
        );


    }






    @Override
    protected void onDestroy(){


        super.onDestroy();


        autoHandler.removeCallbacks(
                autoUpdate
        );


    }



}
