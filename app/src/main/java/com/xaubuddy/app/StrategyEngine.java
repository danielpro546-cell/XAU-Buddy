package com.xaubuddy.app;

public class StrategyEngine {

    public String bos = "NO";
    public String choch = "NO";
    public String fvg = "NONE";
    public String liquidity = "NONE";

    public String signal = "WAITING";
    public int confidence = 0;


    public void analyze(MarketData data){


        // =====================
        // BOS Detection
        // =====================

        if(data.h1Close > data.h1High - 5){

            bos = "BULLISH BREAK";

        }
        else if(data.h1Close < data.h1Low + 5){

            bos = "BEARISH BREAK";

        }
        else{

            bos = "NO";

        }



        // =====================
        // CHoCH Detection
        // =====================

        if(data.m5Close > data.m5Open){

            choch = "BULLISH";

        }
        else{

            choch = "BEARISH";

        }



        // =====================
        // FVG Detection
        // =====================

        if(data.m5High > data.h1High){

            fvg = "BUY FVG";

        }
        else if(data.m5Low < data.h1Low){

            fvg = "SELL FVG";

        }
        else{

            fvg = "NONE";

        }



        // =====================
        // Liquidity
        // =====================

        if(data.m5Close > data.m5Open){

            liquidity = "BUY SIDE";

        }
        else{

            liquidity = "SELL SIDE";

        }



        // =====================
        // Confidence Score
        // =====================

        confidence = 0;


        if(data.ema20 > data.ema50){

            confidence += 25;

        }


        if(data.rsi14 > 50){

            confidence += 25;

        }


        if(choch.equals("BULLISH")){

            confidence += 25;

        }


        if(fvg.equals("BUY FVG")){

            confidence += 25;

        }



        // =====================
        // Signal
        // =====================

        if(confidence >= 75){

            signal = "BUY";

        }

        else if(
                data.ema20 < data.ema50
                &&
                data.rsi14 < 50
        ){

            signal = "SELL";

        }

        else{

            signal = "WAITING";

        }

    }
}
