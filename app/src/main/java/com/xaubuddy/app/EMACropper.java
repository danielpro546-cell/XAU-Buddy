package com.xaubuddy.app;

import android.graphics.Bitmap;

public class EMACropper {

    public Bitmap crop(Bitmap source){

        int w = source.getWidth();
        int h = source.getHeight();

        // EMA + RSI Area
        int left = (int)(w * 0.60);
        int top = (int)(h * 0.55);

        int width = (int)(w * 0.38);
        int height = (int)(h * 0.42);

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
