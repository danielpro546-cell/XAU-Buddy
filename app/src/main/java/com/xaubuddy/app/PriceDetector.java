package com.xaubuddy.app;

import android.graphics.Bitmap;
import android.graphics.Color;

public class PriceDetector {

    public double detectedPrice = 0;
    public boolean found = false;

    public void detect(Bitmap bitmap){

        detectedPrice = 0;
        found = false;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int whitePixels = 0;

        for(int y = height / 20; y < height / 4; y++){

            for(int x = width * 3 / 5; x < width; x++){

                int c = bitmap.getPixel(x, y);

                int r = Color.red(c);
                int g = Color.green(c);
                int b = Color.blue(c);

                if(r > 220 && g > 220 && b > 220){
                    whitePixels++;
                }

            }

        }

        if(whitePixels > 100){
            found = true;
        }

    }

}
