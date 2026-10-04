package com.xaubuddy.app;

public class StrategyEngine {

    public String bos = "NO";
    public String choch = "NO";
    public String fvg = "NONE";
    public String liquidity = "NONE";

    public String signal = "WAITING";


    public void analyze(MarketData data){

        if(data.ema20 > data.ema50){
            bos = "YES";
        }
        else{
            bos = "NO";
        }


        if(data.rsi14 > 50){
            choch = "BULLISH";
        }
        else{
            choch = "BEARISH";
        }


        if(data.ema20 > data.ema50){
            fvg = "FOUND";
        }
        else{
            fvg = "NONE";
        }


        if(data.rsi14 > 55){
            liquidity = "BUY SIDE";
        }
        else if(data.rsi14 < 45){
            liquidity = "SELL SIDE";
        }
        else{
            liquidity = "NONE";
        }


        // Signal Test
        if(data.rsi14 > 50){
            signal = "BUY";
        }
        else{
            signal = "SELL";
        }

    }
}
