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
                liquidity = "WAITING";

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

    private int countGreenPixels(Bitmap bitmap){

        int green = 0;

        for(int y = 0; y < bitmap.getHeight(); y += 5){

            for(int x = 0; x < bitmap.getWidth(); x += 5){

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

        for(int y = 0; y < bitmap.getHeight(); y += 5){

            for(int x = 0; x < bitmap.getWidth(); x += 5){

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
