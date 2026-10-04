package com.xaubuddy.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {

    TextView dashboard;
    EditText riskInput;

    double balance = 10;

    MarketData marketData = new MarketData();
    StrategyEngine strategy = new StrategyEngine();

    Button calculateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30,40,30,30);


        TextView title = new TextView(this);
        title.setText("XAU Buddy V2");
        title.setTextSize(30);
        title.setGravity(Gravity.CENTER);


        TextView mode = new TextView(this);
        mode.setText("MODE: DEMO\nLIVE: LOCKED");
        mode.setTextSize(20);


        TextView balanceText = new TextView(this);
        balanceText.setText("Balance: $10");
        balanceText.setTextSize(20);



        Button b10 = new Button(this);
        b10.setText("$10");

        Button b50 = new Button(this);
        b50.setText("$50");

        Button b100 = new Button(this);
        b100.setText("$100");



        LinearLayout balanceButtons = new LinearLayout(this);
        balanceButtons.addView(b10);
        balanceButtons.addView(b50);
        balanceButtons.addView(b100);



        riskInput = new EditText(this);
        riskInput.setHint("Risk %");
        riskInput.setText("0.5");
        riskInput.setInputType(2);



        dashboard = new TextView(this);
        dashboard.setTextSize(18);



        calculateButton = new Button(this);
        calculateButton.setText("CALCULATE");



        calculateButton.setOnClickListener(v -> {


            marketData.updateDemoData();

            strategy.analyze(marketData);



            double risk = 0.5;


            try{

                risk = Double.parseDouble(
                riskInput.getText().toString()
                );

            }catch(Exception e){}



            double riskAmount =
                    balance * risk / 100;



            double lot =
                    riskAmount / 5;



            if(lot < 0.01){

                lot = 0.01;

            }



            dashboard.setText(

            "===== XAU BUDDY V2 =====\n\n" +

            "PRICE: " + marketData.price +

            "\nEMA20: " + marketData.ema20 +

            "\nEMA50: " + marketData.ema50 +

            "\nRSI14: " + marketData.rsi14 +

            "\n\nH1: " + marketData.h1Bias +

            "\nM5: " + marketData.m5Signal +

            "\n\nBOS: " + strategy.bos +

            "\nCHoCH: " + strategy.choch +

            "\nFVG: " + strategy.fvg +

            "\nLiquidity: " + strategy.liquidity +

            "\n\nSIGNAL: " + strategy.signal +

            "\nCONFIDENCE: " + strategy.confidence + "%" +

            "\n\nBalance: $" + balance +

            "\nRisk: " + risk + "%" +

            "\nRisk Amount: $" +
            String.format("%.2f",riskAmount) +

            "\nLot Size: " +
            String.format("%.2f",lot) +

            "\n\nENTRY: " + marketData.price +

            "\nSL: Waiting..." +

            "\nTP: Waiting..."

            );


        });



        b10.setOnClickListener(v -> {

            balance = 10;
            balanceText.setText("Balance: $10");

        });



        b50.setOnClickListener(v -> {

            balance = 50;
            balanceText.setText("Balance: $50");

        });



        b100.setOnClickListener(v -> {

            balance = 100;
            balanceText.setText("Balance: $100");

        });



        layout.addView(title);

        layout.addView(mode);

        layout.addView(balanceText);

        layout.addView(balanceButtons);

        layout.addView(riskInput);

        layout.addView(dashboard);

        layout.addView(calculateButton);



        setContentView(layout);



    }

}
