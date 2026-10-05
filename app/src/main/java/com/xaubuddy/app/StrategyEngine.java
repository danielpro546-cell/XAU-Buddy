package com.xaubuddy.app;


public class StrategyEngine {


    public String bos = "NO";
    public String choch = "NO";
    public String fvg = "NONE";
    public String liquidity = "WAITING";


    public String signal = "WAITING";
    public int confidence = 0;



    public void analyze(MarketData data){


        int buyScore = 0;
        int sellScore = 0;



        // =========================
        // BOS V5
        // =========================

        if(data.h1Close > data.h1Open
                &&
           data.h1Close > data.h1Low){

            bos = "BULLISH BREAK";

        }
        else if(data.h1Close < data.h1Open
                &&
                data.h1Close < data.h1High){

            bos = "BEARISH BREAK";

        }
        else{

            bos = "NO";

        }





        // =========================
        // CHoCH V5
        // =========================

        if(data.m5Close > data.m5Open
                &&
           data.m5Close > data.h1Close){

            choch = "BULLISH";

        }
        else if(data.m5Close < data.m5Open
                &&
                data.m5Close < data.h1Close){

            choch = "BEARISH";

        }
        else{

            choch = "NO";

        }





        // =========================
        // FVG V5
        // =========================

        double body =
                Math.abs(
                data.m5Close-data.m5Open
                );


        double range =
                data.m5High-data.m5Low;



        if(
            data.m5Close > data.m5Open
            &&
            range > 0
            &&
            body > range*0.5
        ){

            fvg = "BUY FVG";

        }

        else if(
            data.m5Close < data.m5Open
            &&
            range > 0
            &&
            body > range*0.5
        ){

            fvg = "SELL FVG";

        }

        else{

            fvg = "NONE";

        }





        // =========================
        // Liquidity Sweep
        // =========================


        if(data.m5High >= data.h1High){

            liquidity = "BUY SIDE SWEEP";

        }

        else if(data.m5Low <= data.h1Low){

            liquidity = "SELL SIDE SWEEP";

        }

        else{

            liquidity = "WAITING";

        }





        // =========================
        // SCORE SYSTEM
        // =========================


        // Trend 25

        if(data.h1Bias.equals("BULLISH")){

            buyScore +=25;

        }
        else if(data.h1Bias.equals("BEARISH")){

            sellScore +=25;

        }



        // EMA 20

        if(data.ema20 > data.ema50){

            buyScore +=20;

        }
        else if(data.ema20 < data.ema50){

            sellScore +=20;

        }




        // RSI Momentum 15

        if(data.rsi14 >=55){

            buyScore +=15;

        }
        else if(data.rsi14 <=45){

            sellScore +=15;

        }





        // BOS 15

        if(bos.equals("BULLISH BREAK")){

            buyScore +=15;

        }
        else if(bos.equals("BEARISH BREAK")){

            sellScore +=15;

        }




        // CHoCH 10

        if(choch.equals("BULLISH")){

            buyScore +=10;

        }
        else if(choch.equals("BEARISH")){

            sellScore +=10;

        }




        // FVG 10

        if(fvg.equals("BUY FVG")){

            buyScore +=10;

        }
        else if(fvg.equals("SELL FVG")){

            sellScore +=10;

        }





        // Liquidity 5

        if(liquidity.contains("BUY")){

            buyScore +=5;

        }

        else if(liquidity.contains("SELL")){

            sellScore +=5;

        }






        // =========================
        // FINAL SIGNAL
        // =========================


        if(
            buyScore >=70
            &&
            buyScore > sellScore
        ){

            signal="BUY";

            confidence=buyScore;

        }


        else if(
            sellScore >=70
            &&
            sellScore > buyScore
        ){

            signal="SELL";

            confidence=sellScore;

        }


        else{

            signal="WAITING";

            confidence=
            Math.max(
            buyScore,
            sellScore
            );

        }



    }



}
