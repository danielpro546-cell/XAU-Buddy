package com.xaubuddy.app;

import android.graphics.Bitmap;

public class ChartCropper {

    public Bitmap crop(Bitmap bitmap){

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Chart area only (adjust later if needed)
        int left = width / 12;
        int top = height / 6;

        int cropWidth = width * 5 / 6;
        int cropHeight = height / 2;

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
