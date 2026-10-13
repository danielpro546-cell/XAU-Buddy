package com.xaubuddy.app;

import android.graphics.Bitmap;
import android.graphics.Color;

public class CandleDetector {

    public int greenCandles = 0;
    public int redCandles = 0;

    public String marketStructure = "WAIT";

    public void detect(Bitmap bitmap){

        greenCandles = 0;
        redCandles = 0;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int startX = width / 8;
        int endX = width * 7 / 8;

        int startY = height / 10;
        int endY = height * 7 / 10;

        for(int x = startX; x < endX; x += 6){

            int green = 0;
            int red = 0;

            for(int y = startY; y < endY; y++){

                int c = bitmap.getPixel(x, y);

                int r = Color.red(c);
                int g = Color.green(c);
                int b = Color.blue(c);

                if(g > r + 35 && g > b + 20){
                    green++;
                }

                if(r > g + 35 && r > b + 20){
                    red++;
                }

            }

            if(green > red && green > 8){
                greenCandles++;
            }

            if(red > green && red > 8){
                redCandles++;
            }

        }

        if(greenCandles >= redCandles + 2){
            marketStructure = "BULLISH";
        }
        else if(redCandles >= greenCandles + 2){
            marketStructure = "BEARISH";
        }
        else{
            marketStructure = "SIDEWAYS";
        }

    }

}
