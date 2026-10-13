package com.xaubuddy.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public double detectedPrice = 0;
    public double ema20 = 0;
    public double ema50 = 0;
    public double rsi14 = 0;

    public String ocrStatus = "";
    public String ocrText = "";

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

            Bitmap originalBitmap = bitmap;

            CurrentPriceCropper priceCropper =
                    new CurrentPriceCropper();

            Bitmap priceBitmap =
                    priceCropper.crop(originalBitmap);

            ChartCropper cropper =
                    new ChartCropper();

            bitmap = cropper.crop(originalBitmap);

            // OCR
            PriceDetector detector =
                    new PriceDetector();

            detector.detect(priceBitmap,(price,status)->{

                detectedPrice = price;
                ocrStatus = status;
                ocrText = detector.detectedText;

            });

            // Indicator OCR
            IndicatorDetector indicator =
                    new IndicatorDetector();

            indicator.detect(bitmap,()->{

                ema20 = indicator.ema20;
                ema50 = indicator.ema50;
                rsi14 = indicator.rsi14;

            });

            // Candle
            CandleDetector candle =
                    new CandleDetector();

            candle.detect(bitmap);

            String structure =
                    candle.marketStructure;

            // Wick
            WickDetector wick =
                    new WickDetector();

            wick.detect(bitmap);

            int bullish = wick.bullishWicks;
            int bearish = wick.bearishWicks;

            if(structure.equals("BULLISH")){

                trend = "BULLISH";
                signal = "BUY";
                liquidity = "BUY SIDE";

            }else if(structure.equals("BEARISH")){

                trend = "BEARISH";
                signal = "SELL";
                liquidity = "SELL SIDE";

            }else{

                trend = "SIDEWAYS";
                signal = "WAIT";
                liquidity = "WAIT";

            }

            // Wick confirmation
            if(bullish > bearish && trend.equals("BEARISH")){

                trend = "BULLISH";
                signal = "BUY";
                liquidity = "BUY SIDE";

            }

            if(bearish > bullish && trend.equals("BULLISH")){

                trend = "BEARISH";
                signal = "SELL";
                liquidity = "SELL SIDE";

            }

            bos = trend;
            choch = trend;

            if(trend.equals("BULLISH")){

                fvg = "BUY";

            }else if(trend.equals("BEARISH")){

                fvg = "SELL";

            }else{

                fvg = "WAIT";

            }

            confidence = 60;

            if(structure.equals("BULLISH") ||
               structure.equals("BEARISH")){

                confidence += 20;

            }

            if(bullish > bearish ||
               bearish > bullish){

                confidence += 10;

            }

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
