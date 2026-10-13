package com.xaubuddy.app;

import android.graphics.Bitmap;

public class ChartCropper {

    public Bitmap crop(Bitmap bitmap){

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // MT5 chart candles only
        int left = (int)(width * 0.08);
        int top = (int)(height * 0.10);

        int cropWidth = (int)(width * 0.72);
        int cropHeight = (int)(height * 0.52);

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
