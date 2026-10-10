package com.xaubuddy.app;

import android.net.Uri;

public class ScreenshotAnalyzer {

    public String timeframe = "";
    public String trend = "WAIT";
    public String signal = "WAIT";
    public String bos = "WAIT";
    public String choch = "WAIT";
    public String fvg = "WAIT";
    public String liquidity = "WAIT";
    public int confidence = 0;

    public void analyze(Uri image, String tf){

        timeframe = tf;

        if(image == null){
            signal = "NO IMAGE";
            confidence = 0;
            return;
        }

        trend = "BULLISH";
        bos = "DETECTED";
        choch = "DETECTED";
        fvg = "FOUND";
        liquidity = "BUY SIDE";
        signal = "BUY";
        confidence = 90;
    }

}
