package com.xaubuddy.app;

public class MarketData {

    public double price = 0.0;
    public double ema20 = 0.0;
    public double ema50 = 0.0;
    public double rsi14 = 0.0;

    public String h1Bias = "WAITING";
    public String m5Signal = "WAITING";


    public void updateDemoData(){

        // Placeholder data
        // Later replace with real MT5/API feed

        price = 5000.00;

        ema20 = 4998.00;
        ema50 = 4995.00;

        rsi14 = 55.0;


        if(ema20 > ema50){
            h1Bias = "BULLISH";
        }
        else{
            h1Bias = "BEARISH";
        }


        if(rsi14 > 50){
            m5Signal = "BUY SETUP";
        }
        else{
            m5Signal = "SELL SETUP";
        }

    }
}
