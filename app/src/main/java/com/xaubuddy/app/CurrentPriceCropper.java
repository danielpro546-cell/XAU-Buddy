package com.xaubuddy.app;

import android.graphics.Bitmap;

public class CurrentPriceCropper {

    public Bitmap crop(Bitmap bitmap){

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int left = (int)(width * 0.84);
int top = (int)(height * 0.32);

int cropWidth = (int)(width * 0.13);
int cropHeight = (int)(height * 0.18);
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
