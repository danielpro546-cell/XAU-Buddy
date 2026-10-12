package com.xaubuddy.app;

import android.graphics.Bitmap;

public class CurrentPriceCropper {

    public Bitmap crop(Bitmap bitmap){

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Live price area (MT5 Android)
        int left = (int)(width * 0.80);
int top = (int)(height * 0.33);

int cropWidth = (int)(width * 0.20);
int cropHeight = (int)(height * 0.12);
        if(left < 0) left = 0;
        if(top < 0) top = 0;

        if(left + cropWidth > width){
            cropWidth = width - left;
        }

        if(top + cropHeight > height){
            cropHeight = height - top;
        }

        return Bitmap.createBitmap(
                bitmap,
                left,
                top,
                cropWidth,
                cropHeight
        );
    }
}
