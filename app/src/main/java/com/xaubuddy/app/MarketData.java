package com.xaubuddy.app;

public class MarketData {

    public double price = 0;

    public double h1Open = 0;
    public double h1High = 0;
    public double h1Low = 0;
    public double h1Close = 0;

    public double m5Open = 0;
    public double m5High = 0;
    public double m5Low = 0;
    public double m5Close = 0;

    public double ema20 = 0;
    public double ema50 = 0;
    public double rsi14 = 0;

    public String h1Bias = "WAIT";
    public String m5Signal = "WAIT";

    public boolean dataReady = false;

    private String status = "WAIT";

    public void updateFromAnalysis(
            double detectedPrice,
            String trend,
            String signal
    ){

        price = detectedPrice;

        h1Close = detectedPrice;
        m5Close = detectedPrice;

        h1High = detectedPrice;
        h1Low = detectedPrice;

        m5High = detectedPrice;
        m5Low = detectedPrice;

        // -------- EMA --------

        if(trend.equals("BULLISH")){

            ema50 = detectedPrice - 15;
            ema20 = detectedPrice - 5;

        }else if(trend.equals("BEARISH")){

            ema20 = detectedPrice + 5;
            ema50 = detectedPrice + 15;

        }else{

            ema20 = detectedPrice;
            ema50 = detectedPrice;

        }

        // -------- RSI --------

        if(signal.equals("BUY")){

            rsi14 = 60;

        }else if(signal.equals("SELL")){

            rsi14 = 40;

        }else{

            rsi14 = 50;

        }

        // -------- H1 --------

        h1Bias = trend;

        // -------- M5 --------

        if(signal.equals("BUY")){

            m5Signal = "BUY SETUP";

        }else if(signal.equals("SELL")){

            m5Signal = "SELL SETUP";

        }else{

            m5Signal = "WAIT";

        }

        dataReady = true;
        status = "SCREENSHOT ANALYSIS";
    }

    public String getStatus(){

        return status;

    }

}
