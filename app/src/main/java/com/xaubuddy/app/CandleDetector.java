package com.xaubuddy.app;

import android.graphics.Bitmap;
import android.graphics.Color;

public class CandleDetector {

    public int bullishCandles = 0;
    public int bearishCandles = 0;

    public int bullishStrength = 0;
    public int bearishStrength = 0;

    public double lastHigh = 0;
    public double lastLow = 0;

    public String marketStructure = "WAIT";

    public void detect(Bitmap bitmap){

        bullishCandles = 0;
        bearishCandles = 0;

        bullishStrength = 0;
        bearishStrength = 0;

        lastHigh = 0;
        lastLow = 999999;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int startX = (int)(width * 0.60);
        int endX = (int)(width * 0.96);

        int startY = (int)(height * 0.08);
        int endY = (int)(height * 0.78);

        for(int x = startX; x < endX; x += 4){

            int top = height;
            int bottom = 0;

            boolean found = false;

            int greenPixels = 0;
            int redPixels = 0;
                        for(int y = startY; y < endY; y++){

                int pixel = bitmap.getPixel(x, y);

                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = Color.blue(pixel);

                if(g > r + 25 && g > b + 25){

                    greenPixels++;
                    found = true;

                    if(y < top){
                        top = y;
                    }

                    if(y > bottom){
                        bottom = y;
                    }

                }

                if(r > g + 25 && r > b + 25){

                    redPixels++;
                    found = true;

                    if(y < top){
                        top = y;
                    }

                    if(y > bottom){
                        bottom = y;
                    }

                }

            }

            if(found){

                if(top > lastHigh){
                    lastHigh = top;
                }

                if(bottom < lastLow){
                    lastLow = bottom;
                }

                if(greenPixels > redPixels){

                    bullishCandles++;
                    bullishStrength += greenPixels;

                }else if(redPixels > greenPixels){

                    bearishCandles++;
                    bearishStrength += redPixels;

                }

            }

        }

            if(bullishCandles > bearishCandles &&
                bullishStrength > bearishStrength){

            marketStructure = "BULLISH";

        }
        else if(bearishCandles > bullishCandles &&
                bearishStrength > bullishStrength){

            marketStructure = "BEARISH";

        }
        else{

            marketStructure = "SIDEWAYS";

        }

    }

}
