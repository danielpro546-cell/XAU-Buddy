package com.xaubuddy.app;


public class StrategyEngine {


    public String bos = "NO";
    public String choch = "NO";
    public String fvg = "NONE";
    public String liquidity = "NONE";


    public String signal = "WAITING";
    public int confidence = 0;



    public void analyze(MarketData data){



        // =========================
        // BOS Detection
        // =========================

        if(data.h1Close > data.h1Open){

            bos = "BULLISH BREAK";

        }
        else if(data.h1Close < data.h1Open){

            bos = "BEARISH BREAK";

        }
        else{

            bos = "NO";

        }





        // =========================
        // CHoCH Detection
        // =========================

        if(data.m5Close > data.m5Open){

            choch = "BULLISH";

        }
        else if(data.m5Close < data.m5Open){

            choch = "BEARISH";

        }
        else{

            choch = "NO";

        }





        // =========================
        // FVG Detection
        // =========================

        double candleSize =
                data.m5High - data.m5Low;



        if(
                data.m5Close > data.m5Open
                &&
                candleSize > 5
        ){

            fvg = "BUY FVG";

        }

        else if(
                data.m5Close < data.m5Open
                &&
                candleSize > 5
        ){

            fvg = "SELL FVG";

        }

        else{

            fvg = "NONE";

        }





        // =========================
        // Liquidity
        // =========================

        if(data.m5High >= data.h1High){

            liquidity = "BUY SIDE";

        }

        else if(data.m5Low <= data.h1Low){

            liquidity = "SELL SIDE";

        }

        else{

            liquidity = "WAITING";

        }





        // =========================
        // Confidence Score
        // =========================

        int buyScore = 0;
        int sellScore = 0;



        // Trend

        if(data.h1Bias.equals("BULLISH")){

            buyScore += 20;

        }

        if(data.h1Bias.equals("BEARISH")){

            sellScore += 20;

        }





        // EMA

        if(data.ema20 > data.ema50){

            buyScore += 20;

        }

        else if(data.ema20 < data.ema50){

            sellScore += 20;

        }





        // RSI

        if(data.rsi14 >= 55){

            buyScore += 15;

        }

        else if(data.rsi14 <= 45){

            sellScore += 15;

        }





        // BOS

        if(bos.equals("BULLISH BREAK")){

            buyScore += 15;

        }

        else if(bos.equals("BEARISH BREAK")){

            sellScore += 15;

        }





        // CHoCH

        if(choch.equals("BULLISH")){

            buyScore += 15;

        }

        else if(choch.equals("BEARISH")){

            sellScore += 15;

        }





        // FVG

        if(fvg.equals("BUY FVG")){

            buyScore += 10;

        }

        else if(fvg.equals("SELL FVG")){

            sellScore += 10;

        }





        // Liquidity

        if(liquidity.equals("BUY SIDE")){

            buyScore += 5;

        }

        else if(liquidity.equals("SELL SIDE")){

            sellScore += 5;

        }





        // =========================
        // Final Decision
        // =========================


        if(buyScore >= 75
                &&
           buyScore > sellScore){


            signal = "BUY";

            confidence = buyScore;


        }


        else if(sellScore >= 75
                &&
                sellScore > buyScore){


            signal = "SELL";

            confidence = sellScore;


        }


        else{


            signal = "WAITING";

            confidence =
                    Math.max(
                    buyScore,
                    sellScore
                    );


        }



    }



}
