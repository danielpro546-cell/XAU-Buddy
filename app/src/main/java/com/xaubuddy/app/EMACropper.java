package com.xaubuddy.app;

import android.graphics.Bitmap;

public class EMACropper {

    public Bitmap crop(Bitmap source){

        int w = source.getWidth();
        int h = source.getHeight();


        // MT5 Indicator panel area
        // EMA20 EMA50 + RSI14

        int left = (int)(w * 0.35);
        int top = (int)(h * 0.72);

        int width = (int)(w * 0.60);
        int height = (int)(h * 0.22);



        if(left + width > w){
            width = w - left;
        }


        if(top + height > h){
            height = h - top;
        }



        return Bitmap.createBitmap(
                source,
                left,
                top,
                width,
                height
        );

    }

}
