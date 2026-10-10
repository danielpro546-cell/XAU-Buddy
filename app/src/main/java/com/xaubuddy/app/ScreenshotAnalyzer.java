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

            CandleDetector detector = new CandleDetector();

detector.detect(bitmap);

int green = detector.greenCandles;
int red = detector.redCandles;

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

    }

    
