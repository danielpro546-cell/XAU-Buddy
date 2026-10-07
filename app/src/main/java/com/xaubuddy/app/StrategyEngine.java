package com.xaubuddy.app;

public class StrategyEngine {

    public String bos = "NO";
    public String choch = "NO";
    public String fvg = "NONE";
    public String liquidity = "WAITING";

    public String signal = "WAIT";
    public int confidence = 0;

    public void analyze(MarketData data){

        int buyScore = 0;
        int sellScore = 0;

        // BOS
        if(data.h1Close > data.h1Open){
            bos = "BULLISH BREAK";
            buyScore += 20;
        }else if(data.h1Close < data.h1Open){
            bos = "BEARISH BREAK";
            sellScore += 20;
        }else{
            bos = "NO";
        }

        // CHoCH
        if(data.m5Close > data.m5Open){
            choch = "BULLISH";
            buyScore += 15;
        }else if(data.m5Close < data.m5Open){
            choch = "BEARISH";
            sellScore += 15;
        }else{
            choch = "NO";
        }

        // EMA Trend
        if(data.ema20 > data.ema50){
            buyScore += 25;
        }else{
            sellScore += 25;
        }

        // RSI
        if(data.rsi14 >= 55){
            buyScore += 20;
        }else if(data.rsi14 <= 45){
            sellScore += 20;
        }

        // FVG
        if(data.m5Close > data.m5Open){
            fvg = "BUY FVG";
            buyScore += 10;
        }else if(data.m5Close < data.m5Open){
            fvg = "SELL FVG";
            sellScore += 10;
        }else{
            fvg = "NONE";
        }

        // Liquidity
        if(data.price >= data.h1High){
            liquidity = "BUY SIDE";
            buyScore += 10;
        }else if(data.price <= data.h1Low){
            liquidity = "SELL SIDE";
            sellScore += 10;
        }else{
            liquidity = "WAITING";
        }

        if(buyScore > sellScore && buyScore >= 70){
            signal = "BUY";
            confidence = buyScore;
        }else if(sellScore > buyScore && sellScore >= 70){
            signal = "SELL";
            confidence = sellScore;
        }else{
            signal = "WAIT";
            confidence = Math.max(buyScore, sellScore);
        }
    }
}
