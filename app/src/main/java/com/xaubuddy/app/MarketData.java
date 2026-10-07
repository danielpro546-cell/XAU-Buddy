package com.xaubuddy.app;

public class MarketData {

    public double price = 4167.00;

    public double h1Open = 4160;
    public double h1High = 4170;
    public double h1Low = 4155;
    public double h1Close = 4167;

    public double m5Open = 4165;
    public double m5High = 4168;
    public double m5Low = 4162;
    public double m5Close = 4167;

    public double ema20 = 4165;
    public double ema50 = 4160;
    public double rsi14 = 60;

    public String h1Bias = "BULLISH";
    public String m5Signal = "BUY SETUP";

    public boolean dataReady = false;

    private String status = "WAITING";

    public void updateLiveData() {

        double move = (Math.random() * 8) - 4;
        price += move;

        h1Close = price;
        m5Close = price;

        if(price > h1High) h1High = price;
        if(price < h1Low) h1Low = price;

        ema20 = ema20 + (price - ema20) * 0.20;
        ema50 = ema50 + (price - ema50) * 0.08;

        rsi14 = 50 + (price - ema50);

        if(rsi14 > 70) rsi14 = 70;
        if(rsi14 < 30) rsi14 = 30;

        if(ema20 > ema50){
            h1Bias = "BULLISH";
        }else{
            h1Bias = "BEARISH";
        }

        if(h1Bias.equals("BULLISH") && rsi14 > 55){
            m5Signal = "BUY SETUP";
        }
        else if(h1Bias.equals("BEARISH") && rsi14 < 45){
            m5Signal = "SELL SETUP";
        }
        else{
            m5Signal = "WAIT";
        }

        dataReady = true;
        status = "DEMO LIVE DATA";
    }

    public String getStatus() {
        return status;
    }

}
