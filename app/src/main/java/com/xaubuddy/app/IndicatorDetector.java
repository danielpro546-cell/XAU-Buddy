package com.xaubuddy.app;

import android.graphics.Bitmap;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class IndicatorDetector {

    public interface IndicatorListener{
        void onFinish();
    }

    public double ema20 = 0;
    public double ema50 = 0;
    public double rsi14 = 0;

    public String rawText = "";

    public void detect(Bitmap bitmap, IndicatorListener listener){

        try{

            InputImage image = InputImage.fromBitmap(bitmap,0);

            TextRecognition
                    .getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    .process(image)

                    .addOnSuccessListener(result -> {

                        rawText = result.getText();

                        java.util.regex.Matcher matcher =
                                java.util.regex.Pattern
                                        .compile("\\d{2,4}\\.\\d{2}")
                                        .matcher(rawText);

                        java.util.ArrayList<Double> values =
                                new java.util.ArrayList<>();

                        while(matcher.find()){

                            try{
                                values.add(
                                        Double.parseDouble(matcher.group())
                                );
                            }catch(Exception ignored){}
                        }

                        ema20 = 0;
                        ema50 = 0;
                        rsi14 = 0;

                        for(double v : values){

                            if(v > 4000){

                                if(ema20 == 0){

                                    ema20 = v;

                                }else if(ema50 == 0){

                                    ema50 = v;

                                }

                            }else if(v >= 0 && v <= 100){

                                rsi14 = v;

                            }

                        }

                        listener.onFinish();

                    })

                    .addOnFailureListener(e -> listener.onFinish());

        }catch(Exception e){

            listener.onFinish();

        }

    }

}
