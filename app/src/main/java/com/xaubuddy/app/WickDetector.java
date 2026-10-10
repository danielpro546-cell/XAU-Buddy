package com.xaubuddy.app;

import android.graphics.Bitmap;
import android.graphics.Color;

public class WickDetector {

    public int upperWicks = 0;
    public int lowerWicks = 0;

    // ScreenshotAnalyzer အတွက် အသစ်ထည့်ထားတဲ့ Variables
    public int bullishWicks = 0;
    public int bearishWicks = 0;

    public void detect(Bitmap bitmap){

        upperWicks = 0;
        lowerWicks = 0;
        bullishWicks = 0;
        bearishWicks = 0;

        int startX = bitmap.getWidth() / 10;
        int endX = bitmap.getWidth() * 9 / 10;

        int startY = bitmap.getHeight() / 5;
        int endY = bitmap.getHeight() * 4 / 5;

        for(int x = startX; x < endX; x += 5){

            boolean found = false;

            // Upper wick
            for(int y = startY; y < endY; y++){

                int c = bitmap.getPixel(x, y);

                int r = Color.red(c);
                int g = Color.green(c);
                int b = Color.blue(c);

                if((g > r + 30 && g > b + 30) ||
                   (r > g + 30 && r > b + 30)){

                    if(!found){
                        upperWicks++;
                        bearishWicks++;   // Upper wick = Sell pressure
                        found = true;
                    }

                    break;
                }
            }

            found = false;

            // Lower wick
            for(int y = endY - 1; y >= startY; y--){

                int c = bitmap.getPixel(x, y);

                int r = Color.red(c);
                int g = Color.green(c);
                int b = Color.blue(c);

                if((g > r + 30 && g > b + 30) ||
                   (r > g + 30 && r > b + 30)){

                    if(!found){
                        lowerWicks++;
                        bullishWicks++;   // Lower wick = Buy pressure
                        found = true;
                    }

                    break;
                }
            }

        }

    }

}
