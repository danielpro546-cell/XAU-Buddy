package com.xaubuddy.app;

import android.graphics.Bitmap;

public class EMACropper {

    public Bitmap crop(Bitmap source){

        int w = source.getWidth();
        int h = source.getHeight();

        // EMA / RSI indicator area (TradingView)
        int left = 0;
        int top = (int)(h * 0.08);

        int width = (int)(w * 0.70);
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
