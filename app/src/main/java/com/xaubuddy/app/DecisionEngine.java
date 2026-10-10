package com.xaubuddy.app;

public class DecisionEngine {

    public String signal = "WAIT";
    public String reason = "";

    public int confidence = 0;


    public void decide(

            String h1Trend,
            String m5Trend,
            double ema20,
            double ema50,
            double rsi,
            String bos,
            String choch,
            String fvg,
            String liquidity

    ){

        int buyScore = 0;
        int sellScore = 0;


        // H1 Trend
        if(h1Trend.equals("BULLISH")){
            buyScore += 20;
        }else if(h1Trend.equals("BEARISH")){
            sellScore += 20;
        }


        // M5 Trend
        if(m5Trend.equals("BULLISH")){
            buyScore += 20;
        }else if(m5Trend.equals("BEARISH")){
            sellScore += 20;
        }


        // EMA
        if(ema20 > ema50){
            buyScore += 15;
        }else{
            sellScore += 15;
        }


        // RSI
        if(rsi >= 55){
            buyScore += 15;
        }else if(rsi <=45){
            sellScore += 15;
        }


        // BOS
        if(bos.contains("BULLISH")){
            buyScore += 10;
        }else if(bos.contains("BEARISH")){
            sellScore += 10;
        }


        // CHoCH
        if(choch.contains("BULLISH")){
            buyScore += 10;
        }else if(choch.contains("BEARISH")){
            sellScore += 10;
        }


        // FVG
        if(fvg.contains("BUY")){
            buyScore += 5;
        }else if(fvg.contains("SELL")){
            sellScore += 5;
        }


        // Liquidity
        if(liquidity.equals("BUY SIDE")){
            buyScore += 5;
        }else if(liquidity.equals("SELL SIDE")){
            sellScore += 5;
        }



        if(buyScore > sellScore && buyScore >=60){

            signal = "BUY";
            confidence = buyScore;
            reason = "BUY Score Confirmed";

        }
        else if(sellScore > buyScore && sellScore >=60){

            signal = "SELL";
            confidence = sellScore;
            reason = "SELL Score Confirmed";

        }
        else{

            signal = "WAIT";
            confidence = Math.max(buyScore,sellScore);
            reason = "Not Enough Confirmation";

        }

    }

}
