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

        int startX = width / 10;
        int endX = width * 9 / 10;

        // Chart candle area only
        int startY = height / 8;
        int endY = height * 3 / 5;


        int previousCenter = -1;

        int higherHigh = 0;
        int higherLow = 0;

        int lowerHigh = 0;
        int lowerLow = 0;


        for(int x = startX; x < endX; x += 8){

            int top = height;
            int bottom = 0;

            boolean candleFound = false;


            for(int y = startY; y < endY; y++){

                int pixel = bitmap.getPixel(x,y);

                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = Color.blue(pixel);


                // Candle color detection
                if(
                    (g > r + 25 && g > b + 25) ||
                    (r > g + 25 && r > b + 25)
                ){

                    candleFound = true;


                    if(g > r + 25){
                        greenCandles++;
                    }

                    if(r > g + 25){
                        redCandles++;
                    }


                    if(y < top){
                        top = y;
                    }

                    if(y > bottom){
                        bottom = y;
                    }

                }

            }


            if(candleFound){

                int center = (top + bottom) / 2;


                if(previousCenter != -1){

                    if(center < previousCenter){

                        higherHigh++;
                        higherLow++;

                    }else{

                        lowerHigh++;
                        lowerLow++;

                    }

                }


                previousCenter = center;

            }

        }


        // Market Structure

        if(higherHigh > lowerHigh &&
           higherLow > lowerLow){

            marketStructure = "BULLISH";


        }else if(lowerHigh > higherHigh &&
                 lowerLow > higherLow){

            marketStructure = "BEARISH";


        }else{

            marketStructure = "SIDEWAYS";

        }

    }

}
