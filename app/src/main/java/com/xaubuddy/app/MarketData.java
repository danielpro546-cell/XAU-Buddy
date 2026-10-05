package com.xaubuddy.app;

public class MarketData {

    public double price = 0.0;

    // H1 Data
    public double h1Open = 0.0;
    public double h1High = 0.0;
    public double h1Low = 0.0;
    public double h1Close = 0.0;

    // M5 Data
    public double m5Open = 0.0;
    public double m5High = 0.0;
    public double m5Low = 0.0;
    public double m5Close = 0.0;


    // Indicators
    public double ema20 = 0.0;
    public double ema50 = 0.0;
    public double rsi14 = 0.0;


    public String h1Bias = "WAITING";
    public String m5Signal = "WAITING";


    public void updateLiveData(){

        // Temporary candle data
        // Next step: API candle connection

        price = 4145.0;


        h1Open = 4135.0;
        h1High = 4150.0;
        h1Low = 4130.0;
        h1Close = price;


        m5Open = 4140.0;
        m5High = 4147.0;
        m5Low = 4138.0;
        m5Close = price;



        // Simple indicator calculation

        ema20 = 4143.0;
        ema50 = 4140.0;

        rsi14 = 55.0;



        if(ema20 > ema50){

            h1Bias = "BULLISH";

        }else{

            h1Bias = "BEARISH";

        }



        if(rsi14 > 50){

            m5Signal = "BUY SETUP";

        }else{

            m5Signal = "SELL SETUP";

        }

    }

}
