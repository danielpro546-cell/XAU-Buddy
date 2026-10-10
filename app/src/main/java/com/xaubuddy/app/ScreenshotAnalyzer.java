package com.xaubuddy.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;

import java.io.InputStream;

public class ScreenshotAnalyzer {

    public String timeframe = "";
    public String trend = "WAIT";
    public String signal = "WAIT";
    public String bos = "WAIT";
    public String choch = "WAIT";
    public String fvg = "WAIT";
    public String liquidity = "WAIT";
    public int confidence = 0;

    public void analyze(Context context, Uri image, String tf){

        timeframe = tf;

        if(image == null){
            signal = "NO IMAGE";
            confidence = 0;
            return;
        }

        try{

            InputStream input =
                    context.getContentResolver().openInputStream(image);

            Bitmap bitmap =
                    BitmapFactory.decodeStream(input);

            if(bitmap == null){
                signal = "IMAGE ERROR";
                confidence = 0;
                return;
            }

            int brightness = getBrightness(bitmap);

            if(brightness >= 120){
                trend = "BULLISH";
                signal = "BUY";
                bos = "DETECTED";
                choch = "DETECTED";
                fvg = "FOUND";
                liquidity = "BUY SIDE";
                confidence = 70;
            }else{
                trend = "BEARISH";
                signal = "SELL";
                bos = "DETECTED";
                choch = "DETECTED";
                fvg = "FOUND";
                liquidity = "SELL SIDE";
                confidence = 70;
            }

        }catch(Exception e){

            trend = "ERROR";
            signal = "ERROR";
            confidence = 0;

        }

    }

    private int getBrightness(Bitmap bitmap){

        long total = 0;
        int count = 0;

        for(int y = 0; y < bitmap.getHeight(); y += 10){

            for(int x = 0; x < bitmap.getWidth(); x += 10){

                int c = bitmap.getPixel(x, y);

                total += (Color.red(c)
                        + Color.green(c)
                        + Color.blue(c)) / 3;

                count++;

            }

        }

        if(count == 0){
            return 0;
        }

        return (int)(total / count);

    }

}
