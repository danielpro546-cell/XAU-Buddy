package com.xaubuddy.app;

import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.Color;

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

    private int getBrightness(Bitmap bitmap){

    long total = 0;
    int count = 0;

    for(int y = 0; y < bitmap.getHeight(); y += 10){

        for(int x = 0; x < bitmap.getWidth(); x += 10){

            int c = bitmap.getPixel(x, y);

            total +=
                    (Color.red(c)
                    + Color.green(c)
                    + Color.blue(c)) / 3;

            count++;

        }

    }

    return (int)(total / count);

}

}
