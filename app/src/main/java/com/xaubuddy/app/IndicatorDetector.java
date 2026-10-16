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

                        android.util.Log.d("OCR_INDICATOR", rawText);

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

                Matcher matcher =
                Pattern.compile("\\d+\\.\\d+")
                .matcher(text);
        String[] lines = text.split("\\n");

java.util.ArrayList<Double> prices =
        new java.util.ArrayList<>();

for (String line : lines) {

    line = line.trim();

    Matcher m = Pattern
            .compile("\\d+\\.\\d+")
            .matcher(line);

    while (m.find()) {

        try {

            double value =
                    Double.parseDouble(m.group());

            if (value >= 4000) {

                prices.add(value);

            } else if (value >= 10 && value <= 90) {

                rsi14 = value;

            }

        } catch (Exception ignored) {
        }

    }
}

        java.util.ArrayList<Double> prices =
                new java.util.ArrayList<>();

        while(matcher.find()){

            try{

                double v =
                        Double.parseDouble(matcher.group());

                if(v > 4000){

                    prices.add(v);

                }else if(v >= 10 && v <= 90){

                    rsi14 = v;

                }

            }catch(Exception ignored){}

        }

        if(prices.size() >= 3){

            // Price, EMA20, EMA50
            double price = prices.get(0);
            double p2 = prices.get(1);
            double p3 = prices.get(2);

            if(Math.abs(p2 - price) <
               Math.abs(p3 - price)){

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

    }

}
