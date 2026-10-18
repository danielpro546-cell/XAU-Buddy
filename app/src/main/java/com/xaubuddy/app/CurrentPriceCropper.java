package com.xaubuddy.app;

import android.graphics.Bitmap;

public class CurrentPriceCropper {

    public Bitmap crop(Bitmap source){

        int w = source.getWidth();
        int h = source.getHeight();

        // MT5 Android Current Price Box
        int left = (int)(w * 0.83);
        int top = (int)(h * 0.28);

        int width = (int)(w * 0.17);
        int height = (int)(h * 0.26);

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
