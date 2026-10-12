package com.xaubuddy.app;

import android.graphics.Bitmap;

public class CurrentPriceCropper {

    public Bitmap crop(Bitmap bitmap){

    int width = bitmap.getWidth();
    int height = bitmap.getHeight();

    int left = width;
    int top = height;
    int right = 0;
    int bottom = 0;

    for(int y=0;y<height;y++){

        for(int x=0;x<width;x++){

            int c = bitmap.getPixel(x,y);

            int r = android.graphics.Color.red(c);
            int g = android.graphics.Color.green(c);
            int b = android.graphics.Color.blue(c);

            boolean green =
                    (g > 150 && r < 120 && b < 180);

            boolean red =
                    (r > 150 && g < 120 && b < 120);

            if(green || red){

                if(x < left) left = x;
                if(x > right) right = x;
                if(y < top) top = y;
                if(y > bottom) bottom = y;

            }

        }

    }

    if(right <= left || bottom <= top){

        return bitmap;

    }

    return Bitmap.createBitmap(
            bitmap,
            left,
            top,
            right-left,
            bottom-top
    );

}

}
