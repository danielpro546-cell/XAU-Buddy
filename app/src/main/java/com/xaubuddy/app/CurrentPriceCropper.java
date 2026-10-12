package com.xaubuddy.app;

import android.graphics.Bitmap;

public class CurrentPriceCropper {

    public Bitmap crop(Bitmap bitmap){

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Right side live price box
        int left = (int)(width * 0.86);
        int top = (int)(height * 0.39);

        int cropWidth = (int)(width * 0.14);
        int cropHeight = (int)(height * 0.05);

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
