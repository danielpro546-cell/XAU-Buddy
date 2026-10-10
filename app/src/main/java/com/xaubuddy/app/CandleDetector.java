package com.xaubuddy.app;

import android.graphics.Bitmap;
import android.graphics.Color;

public class CandleDetector {

    public int greenCandles = 0;
    public int redCandles = 0;

    public void detect(Bitmap bitmap){

        greenCandles = 0;
        redCandles = 0;

        int startX = bitmap.getWidth() / 10;
        int endX = bitmap.getWidth() * 9 / 10;

        int startY = bitmap.getHeight() / 5;
        int endY = bitmap.getHeight() * 4 / 5;

        for(int y = startY; y < endY; y += 5){

            for(int x = startX; x < endX; x += 5){

                int c = bitmap.getPixel(x, y);

                int r = Color.red(c);
                int g = Color.green(c);
                int b = Color.blue(c);

                if(g > r + 30 && g > b + 30){
                    greenCandles++;
                }

                if(r > g + 30 && r > b + 30){
                    redCandles++;
                }

            }

        }

    }

}
