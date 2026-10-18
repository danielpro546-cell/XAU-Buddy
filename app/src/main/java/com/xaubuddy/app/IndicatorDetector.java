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

        InputImage image = InputImage.fromBitmap(bitmap,0);

        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                .process(image)

                .addOnSuccessListener(result -> {

                    rawText = result.getText();

                    parse(rawText);

                    android.util.Log.d("OCR", rawText);

                    listener.onFinish();

                })

                .addOnFailureListener(e -> listener.onFinish());

    }

    private void parse(String text){

        ArrayList<Double> values = new ArrayList<>();

        String[] lines = text.split("\\n");

        for(String line : lines){

            String upper = line.toUpperCase();

            Matcher m = Pattern.compile("\\d+\\.\\d+").matcher(line);

            while(m.find()){

                double v = Double.parseDouble(m.group());

                if(upper.contains("RSI")){

                    if(v>=5 && v<=95){
                        rsi14 = v;
                    }

                }else{

                    if(v>=4100 && v<=4300){
                        values.add(v);
                    }

                }

            }

        }

        if(values.size()>=2){

            ema20 = values.get(0);
            ema50 = values.get(1);

        }

    }

}
