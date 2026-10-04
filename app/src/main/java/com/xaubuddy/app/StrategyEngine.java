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
        if(data.ema20 > data.ema50){
            bos = "YES";
        }
        else{
            bos = "NO";
        }


        // CHoCH
        if(data.rsi14 > 50){
            choch = "BULLISH";
        }
        else{
            choch = "BEARISH";
        }


        // FVG
        if(data.ema20 > data.ema50){
            fvg = "FOUND";
        }
        else{
            fvg = "NONE";
        }


        // Liquidity
        if(data.rsi14 > 55){
            liquidity = "BUY SIDE";
        }
        else if(data.rsi14 < 45){
            liquidity = "SELL SIDE";
        }
        else{
            liquidity = "NONE";
        }


        confidence = 0;


        // BUY SCORE
        if(data.ema20 > data.ema50){
            confidence += 25;
        }

        if(data.rsi14 > 50){
            confidence += 25;
        }

        if(bos.equals("YES")){
            confidence += 25;
        }

        if(fvg.equals("FOUND")){
            confidence += 25;
        }


        if(confidence >= 75){
            signal = "BUY";
        }

        else if(
            data.ema20 < data.ema50 &&
            data.rsi14 < 50
        ){
            signal = "SELL";
        }

        else{
            signal = "WAITING";
        }

    }
}
