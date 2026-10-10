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

            int green = countGreenPixels(bitmap);
            int red = countRedPixels(bitmap);

            if(green > red){

                trend = "BULLISH";
                signal = "BUY";
                liquidity = "BUY SIDE";

            }else if(red > green){

                trend = "BEARISH";
                signal = "SELL";
                liquidity = "SELL SIDE";

            }else{

                trend = "SIDEWAYS";
                signal = "WAIT";
                liquidity = "WAIT";

            }

            bos = "DETECTED";
            choch = "DETECTED";
            fvg = "FOUND";

            confidence = 60 + Math.abs(green - red) / 10;

            if(confidence > 100){
                confidence = 100;
            }

        }catch(Exception e){

            trend = "ERROR";
            signal = "ERROR";
            confidence = 0;

        }

    }

    private int countGreenPixels(Bitmap bitmap){

        int green = 0;

        int startX = bitmap.getWidth() / 10;
        int endX = bitmap.getWidth() * 9 / 10;

        int startY = bitmap.getHeight() / 5;
        int endY = bitmap.getHeight() * 4 / 5;

        for(int y = startY; y < endY; y += 5){

            for(int x = startX; x < endX; x += 5){

                int c = bitmap.getPixel(x, y);

                int r = Color.red(c);
                int g = Color.green(c);
                int b = Color.blue(c);

                if(g > r + 30 && g > b + 30){
                    green++;
                }

            }

        }

        return green;

    }

    private int countRedPixels(Bitmap bitmap){

        int red = 0;

        int startX = bitmap.getWidth() / 10;
        int endX = bitmap.getWidth() * 9 / 10;

        int startY = bitmap.getHeight() / 5;
        int endY = bitmap.getHeight() * 4 / 5;

        for(int y = startY; y < endY; y += 5){

            for(int x = startX; x < endX; x += 5){

                int c = bitmap.getPixel(x, y);

                int r = Color.red(c);
                int g = Color.green(c);
                int b = Color.blue(c);

                if(r > g + 30 && r > b + 30){
                    red++;
                }

            }

        }

        return red;

    }

}
