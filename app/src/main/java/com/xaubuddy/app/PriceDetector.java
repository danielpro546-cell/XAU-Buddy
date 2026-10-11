package com.xaubuddy.app;

import android.graphics.Bitmap;
import android.graphics.Color;

public class PriceDetector {

    public double detectedPrice = 0;
    public String priceText = "";
    public boolean found = false;

    public String detectedText = "";
    public int digitCount = 0;

    public int left;
    public int top;
    public int right;
    public int bottom;

    public void detect(Bitmap bitmap){

        found = false;
        detectedPrice = 0;
        priceText = "";
        detectedText = "";
        digitCount = 0;

        left = bitmap.getWidth();
        top = bitmap.getHeight();
        right = 0;
        bottom = 0;

        for(int y = 0; y < bitmap.getHeight()/3; y++){

            for(int x = bitmap.getWidth()/2; x < bitmap.getWidth(); x++){

                int c = bitmap.getPixel(x,y);

                int r = Color.red(c);
                int g = Color.green(c);
                int b = Color.blue(c);

                if(r > 220 && g > 220 && b > 220){

                    if(x < left) left = x;
                    if(x > right) right = x;
                    if(y < top) top = y;
                    if(y > bottom) bottom = y;

                    found = true;

                }

            }

        }

        if(found){

            priceText = "OCR READY";
            detectedText = "PRICE AREA FOUND";
            digitCount = (right - left) / 12;

        }else{

            priceText = "NOT FOUND";
            detectedText = "PRICE AREA NOT FOUND";

        }

    }

}
