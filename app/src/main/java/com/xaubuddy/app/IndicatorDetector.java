package com.xaubuddy.app;

import android.graphics.Bitmap;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndicatorDetector {

    public interface IndicatorListener{
        void onFinish();
    }

    public double ema20 = 0;
    public double ema50 = 0;
    public double rsi14 = 0;

    public String rawText = "";


    public void detect(
            Bitmap bitmap,
            IndicatorListener listener
    ){

        ema20 = 0;
        ema50 = 0;
        rsi14 = 0;
        rawText = "";


        try{

            InputImage image =
                    InputImage.fromBitmap(bitmap,0);


            TextRecognition
                    .getClient(
                            TextRecognizerOptions.DEFAULT_OPTIONS
                    )
                    .process(image)


                    .addOnSuccessListener(result -> {


                        rawText =
                                result.getText();


                        parseIndicators();


                        android.util.Log.d(
                                "INDICATOR_OCR",
                                rawText
                        );


                        listener.onFinish();


                    })


                    .addOnFailureListener(e -> {

                        listener.onFinish();

                    });


        }catch(Exception e){

            listener.onFinish();

        }

    }



    private void parseIndicators(){


        if(rawText == null)
            return;


        String text =
                rawText.toUpperCase();


        // ================= RSI =================

        Matcher rsiMatcher =
                Pattern.compile(
                "RSI\\s*\\(?14\\)?\\s*[^0-9]*(\\d+\\.\\d+)"
                )
                .matcher(text);


        if(rsiMatcher.find()){

            try{

                rsi14 =
                    Double.parseDouble(
                        rsiMatcher.group(1)
                    );

            }catch(Exception ignored){}

        }



        // ================= EMA =================

        Matcher emaMatcher =
                Pattern.compile(
                "(EMA|MA).*?(\\d{4}\\.\\d{2})"
                )
                .matcher(text);


        java.util.ArrayList<Double> emaValues =
                new java.util.ArrayList<>();


        while(emaMatcher.find()){

            try{

                emaValues.add(
                    Double.parseDouble(
                        emaMatcher.group(2)
                    )
                );


            }catch(Exception ignored){}

        }



        if(emaValues.size() >= 2){

            ema20 = emaValues.get(0);
            ema50 = emaValues.get(1);

        }



        // fallback
        // EMA label မဖတ်နိုင်တဲ့အခါ
        if(ema20 == 0 || ema50 == 0){

            Matcher priceMatcher =
                    Pattern.compile(
                    "\\d{4}\\.\\d{2}"
                    )
                    .matcher(text);


            java.util.ArrayList<Double> list =
                    new java.util.ArrayList<>();


            while(priceMatcher.find()){

                try{

                    list.add(
                    Double.parseDouble(
                    priceMatcher.group()
                    ));

                }catch(Exception ignored){}

            }


            if(list.size() >= 2){

                ema20 = list.get(0);
                ema50 = list.get(1);

            }

        }


    }

}
