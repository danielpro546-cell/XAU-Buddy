package com.xaubuddy.app;

import android.graphics.Bitmap;
import android.graphics.Color;

public class WickDetector {

    public int upperWicks = 0;
    public int lowerWicks = 0;

    public void detect(Bitmap bitmap){

        upperWicks = 0;
        lowerWicks = 0;

        int startX = bitmap.getWidth() / 10;
        int endX = bitmap.getWidth() * 9 / 10;

        int startY = bitmap.getHeight() / 5;
        int endY = bitmap.getHeight() * 4 / 5;

        for(int x = startX; x < endX; x += 5){

            boolean found = false;

            for(int y = startY; y < endY; y++){

                int c = bitmap.getPixel(x, y);

                int r = Color.red(c);
                int g = Color.green(c);
                int b = Color.blue(c);

                if((g > r + 30 && g > b + 30) ||
                   (r > g + 30 && r > b + 30)){

                    if(!found){
                        upperWicks++;
                        found = true;
                    }

                    break;
                }
            }

            found = false;

            for(int y = endY - 1; y >= startY; y--){

                int c = bitmap.getPixel(x, y);

                int r = Color.red(c);
                int g = Color.green(c);
                int b = Color.blue(c);

                if((g > r + 30 && g > b + 30) ||
                   (r > g + 30 && r > b + 30)){

                    if(!found){
                        lowerWicks++;
                        found = true;
                    }

                    break;
                }
            }

        }

    }

}
