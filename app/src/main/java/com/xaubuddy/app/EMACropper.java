package com.xaubuddy.app;

import android.graphics.Bitmap;

public class EMACropper {

    public Bitmap crop(Bitmap source){

        int w = source.getWidth();
        int h = source.getHeight();

        // Indicator Area (EMA + RSI)
        int left = (int)(w * 0.58);
        int top = (int)(h * 0.62);

        int width = (int)(w * 0.40);
        int height = (int)(h * 0.34);

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
