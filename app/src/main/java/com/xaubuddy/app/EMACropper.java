package com.xaubuddy.app;

import android.graphics.Bitmap;

public class EMACropper {

    public Bitmap crop(Bitmap source){

        int w = source.getWidth();
        int h = source.getHeight();

        int left = (int)(w * 0.45);
        int top = (int)(h * 0.18);

        int width = (int)(w * 0.55);
        int height = (int)(h * 0.28);

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
