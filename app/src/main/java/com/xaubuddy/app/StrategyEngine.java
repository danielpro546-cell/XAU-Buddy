package com.xaubuddy.app;

public class StrategyEngine {


    public String bos = "NO";
    public String choch = "NO";
    public String fvg = "NONE";
    public String liquidity = "NONE";

    public String signal = "WAITING";
    public int confidence = 0;



    public void analyze(MarketData data){



        // BOS

        if(data.h1Close > data.h1Open){

            bos = "BULLISH BREAK";

        }
        else if(data.h1Close < data.h1Open){

            bos = "BEARISH BREAK";

        }
        else{

            bos = "NO";

        }





        // CHoCH

        if(data.m5Close > data.m5Open){

            choch = "BULLISH";

        }
        else{

            choch = "BEARISH";

        }





        // FVG

        double gap =
                data.m5Close - data.m5Open;



        if(gap > 2){

            fvg = "BUY FVG";

        }
        else if(gap < -2){

            fvg = "SELL FVG";

        }
        else{

            fvg = "NONE";

        }





        // Liquidity

        if(data.m5High >= data.h1High){

            liquidity = "BUY SIDE LIQUIDITY";

        }
        else if(data.m5Low <= data.h1Low){

            liquidity = "SELL SIDE LIQUIDITY";

        }
        else{

            liquidity = "WAITING";

        }





        // Confidence

        confidence = 0;



        // Trend

        if(data.ema20 > data.ema50){

            confidence += 30;

        }





        // RSI

        if(data.rsi14 > 50){

            confidence += 20;

        }





        // CHoCH

        if(choch.equals("BULLISH")){

            confidence += 20;

        }





        // BOS

        if(bos.equals("BULLISH BREAK")){

            confidence += 20;

        }





        // FVG

        if(fvg.equals("BUY FVG")){

            confidence += 10;

        }





        // Signal


        if(confidence >= 75
                &&
                data.h1Bias.equals("BULLISH")){


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
