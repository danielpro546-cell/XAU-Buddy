package com.xaubuddy.app;

public class DecisionEngine {

    public String signal = "WAIT";
    public String reason = "";

    public void decide(

            String h1Trend,
            String m5Trend,
            double ema20,
            double ema50,
            double rsi

    ){

        signal = "WAIT";
        reason = "";

        // BUY
        if(h1Trend.equals("BULLISH")
                && m5Trend.equals("BULLISH")
                && ema20 > ema50
                && rsi >= 55){

            signal = "BUY";
            reason = "Trend + EMA + RSI Confirm";

        }

        // SELL
        else if(h1Trend.equals("BEARISH")
                && m5Trend.equals("BEARISH")
                && ema20 < ema50
                && rsi <= 45){

            signal = "SELL";
            reason = "Trend + EMA + RSI Confirm";

        }

        // WAIT
        else{

            signal = "WAIT";
            reason = "Market Conflict";

        }

    }

}
