package com.xaubuddy.app;

public class StrategyEngine {

    public String bos = "NO";
    public String choch = "NO";
    public String fvg = "NONE";
    public String liquidity = "NONE";

    public String signal = "WAITING";


    public void analyze(MarketData data){

        // BOS detection
        if(data.ema20 > data.ema50){
            bos = "YES";
        }
        else{
            bos = "NO";
        }


        // CHoCH detection
        if(data.rsi14 > 50){
            choch = "BULLISH";
        }
        else{
            choch = "BEARISH";
        }


        // FVG placeholder
        if(data.ema20 > data.ema50){
            fvg = "FOUND";
        }
        else{
            fvg = "NONE";
        }


        // Liquidity Sweep placeholder
        if(data.rsi14 > 55){
            liquidity = "BUY SIDE";
        }
        else if(data.rsi14 < 45){
            liquidity = "SELL SIDE";
        }
        else{
            liquidity = "NONE";
        }


        // Confluence
        if(
            data.h1Bias.equals("BULLISH") &&
            bos.equals("YES") &&
            fvg.equals("FOUND")
        ){
            signal = "BUY DEMO";
        }

        else if(
            data.h1Bias.equals("BEARISH") &&
            bos.equals("YES")
        ){
            signal = "SELL DEMO";
        }

        else{
            signal = "WAITING";
        }

    }
}
