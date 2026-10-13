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

        // Only chart area
        int startX = width / 10;
        int endX = width * 9 / 10;

        int startY = height / 8;
        int endY = height * 3 / 5;

        int previousCenter = -1;

        int bullish = 0;
        int bearish = 0;

        for(int x = startX; x < endX; x += 8){

            int top = height;
            int bottom = 0;

            boolean candleFound = false;

            for(int y = startY; y < endY; y++){

                int pixel = bitmap.getPixel(x, y);

                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = Color.blue(pixel);

                if((g > r + 25 && g > b + 25) ||
                   (r > g + 25 && r > b + 25)){

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
                        bullish++;
                    }else if(center > previousCenter){
                        bearish++;
                    }

                }

                previousCenter = center;

            }

        }

        if(bullish > bearish + 2){

            marketStructure = "BULLISH";

        }else if(bearish > bullish + 2){

            marketStructure = "BEARISH";

        }else{

            if(greenCandles > redCandles){
                marketStructure = "BULLISH";
            }else if(redCandles > greenCandles){
                marketStructure = "BEARISH";
            }else{
                marketStructure = "SIDEWAYS";
            }

        }

    }

}
