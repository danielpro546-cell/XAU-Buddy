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



    public void analyze(
            Context context,
            Uri image,
            String tf
    ){

        timeframe = tf;


        // reset data
        trend = "WAIT";
        signal = "WAIT";

        detectedPrice = 0;
        ema20 = 0;
        ema50 = 0;
        rsi14 = 0;

        confidence = 0;



        if(image == null){

            signal = "NO IMAGE";
            return;

        }



        try{


            InputStream input =
                    context
                    .getContentResolver()
                    .openInputStream(image);



            Bitmap original =
                    BitmapFactory.decodeStream(input);



            if(original == null){

                signal="IMAGE ERROR";
                return;

            }



            // ========= PRICE OCR =========


            CurrentPriceCropper priceCropper =
                    new CurrentPriceCropper();


            Bitmap priceBitmap =
                    priceCropper.crop(original);



            PriceDetector priceDetector =
                    new PriceDetector();



            priceDetector.detect(
                    priceBitmap,
                    (price,status)->{


                        detectedPrice = price;

                        ocrStatus = status;

                        ocrText =
                        priceDetector.detectedText;


                    });



            // ========= EMA / RSI OCR =========


            EMACropper emaCropper =
                    new EMACropper();


            Bitmap emaBitmap =
                    emaCropper.crop(original);



            IndicatorDetector indicator =
                    new IndicatorDetector();



            indicator.detect(
                    emaBitmap,
                    ()->{


                        ema20 =
                        indicator.ema20;


                        ema50 =
                        indicator.ema50;


                        rsi14 =
                        indicator.rsi14;


                    });
                        // ========= CHART ANALYSIS =========


            ChartCropper chartCropper =
                    new ChartCropper();


            Bitmap chartBitmap =
                    chartCropper.crop(original);



            CandleDetector candle =
                    new CandleDetector();


            candle.detect(chartBitmap);



            String structure =
                    candle.marketStructure;



            WickDetector wick =
                    new WickDetector();


            wick.detect(chartBitmap);



            int bullish =
                    wick.bullishWicks;


            int bearish =
                    wick.bearishWicks;



            // ========= TREND =========


            if(structure.equals("BULLISH")){


                trend = "BULLISH";


                signal = "BUY";


                liquidity = "BUY SIDE";


            }
            else if(structure.equals("BEARISH")){


                trend = "BEARISH";


                signal = "SELL";


                liquidity = "SELL SIDE";


            }
            else{


                trend = "SIDEWAYS";


                signal = "WAIT";


                liquidity = "WAIT";


            }



            // ========= WICK CONFIRMATION =========


            if(
                bullish > bearish &&
                trend.equals("BEARISH")
            ){

                trend="BULLISH";
                signal="BUY";

            }



            if(
                bearish > bullish &&
                trend.equals("BULLISH")
            ){

                trend="BEARISH";
                signal="SELL";

            }



            // ========= STRUCTURE =========


            bos = trend;

            choch = trend;



            if(trend.equals("BULLISH")){

                fvg="BUY";

            }
            else if(trend.equals("BEARISH")){

                fvg="SELL";

            }
            else{

                fvg="WAIT";

            }



            // ========= CONFIDENCE =========


            confidence = 50;



            if(!trend.equals("SIDEWAYS")){

                confidence += 20;

            }


            if(bullish > bearish ||
               bearish > bullish){

                confidence += 15;

            }


            if(detectedPrice > 0){

                confidence += 10;

            }



            if(ema20 > 0 && ema50 > 0){

                confidence += 5;

            }



            if(confidence > 100){

                confidence = 100;

            }



        }
        catch(Exception e){


            trend="ERROR";

            signal="ERROR";

            confidence=0;


        }


    }


}
