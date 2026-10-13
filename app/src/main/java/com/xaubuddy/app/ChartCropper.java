package com.xaubuddy.app;

import android.graphics.Bitmap;

public class ChartCropper {

    public Bitmap crop(Bitmap bitmap){

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // MT5 chart area
        int left = (int)(width * 0.05);
        int top = (int)(height * 0.08);

        int cropWidth = (int)(width * 0.85);
        int cropHeight = (int)(height * 0.65);

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
