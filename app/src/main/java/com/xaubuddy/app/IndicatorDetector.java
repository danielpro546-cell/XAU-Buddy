package com.xaubuddy.app;

import android.graphics.Bitmap;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.ArrayList;
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

    public void detect(Bitmap bitmap, IndicatorListener listener){

        ema20 = 0;
        ema50 = 0;
        rsi14 = 0;
        rawText = "";

        try{

            InputImage image =
                    InputImage.fromBitmap(bitmap,0);

            TextRecognition
                    .getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    .process(image)

                    .addOnSuccessListener(result -> {

                        rawText = result.getText();

                        parseIndicators(rawText);

                        android.util.Log.d(
                                "OCR_INDICATOR",
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

    private void parseIndicators(String text){

        if(text == null)
            return;

        String[] lines = text.split("\\n");

        ArrayList<Double> prices =
                new ArrayList<>();

        ema20 = 0;
        ema50 = 0;
        rsi14 = 0;

        for(String line : lines){

            String upper =
                    line.toUpperCase().trim();

            Matcher m =
                    Pattern.compile("\\d+\\.\\d+")
                    .matcher(line);

            while(m.find()){

                try{

                    double value =
                            Double.parseDouble(
                                    m.group()
                            );

                    if(upper.contains("RSI")
                            && value >= 5
                            && value <= 95){

                        rsi14 = value;
                        continue;

                    }

                    if(value >= 4100 && value <= 4300){

    prices.add(value);

}

                }catch(Exception ignored){}

            }

        }

            if(prices.size() >= 3){

    ema20 = prices.get(prices.size() - 2);
    ema50 = prices.get(prices.size() - 1);

}

            if(Math.abs(p2 - price)
                    < Math.abs(p3 - price)){

                ema20 = p2;
                ema50 = p3;

            }else{

                ema20 = p3;
                ema50 = p2;

            }

        }else if(prices.size() == 2){

            ema20 = prices.get(0);
            ema50 = prices.get(1);

        }else if(prices.size() == 1){

            ema20 = prices.get(0);

        }

        // EMA မတွေ့ရင် fallback
        if(ema20 == 0 && prices.size() >= 1){
            ema20 = prices.get(0);
        }

        if(ema50 == 0 && prices.size() >= 2){
            ema50 = prices.get(1);
        }

    }

}
