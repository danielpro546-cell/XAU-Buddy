package com.xaubuddy.app;

import android.graphics.Bitmap;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceDetector {

    public interface PriceListener{
        void onPriceDetected(double price, String status);
    }

    public double detectedPrice = 0;
    public String priceText = "";
    public String detectedText = "";

    public void detect(Bitmap bitmap, PriceListener listener){

        detectedPrice = 0;
        priceText = "";
        detectedText = "";

        try{

            InputImage image = InputImage.fromBitmap(bitmap,0);

            TextRecognition
                    .getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    .process(image)

                    .addOnSuccessListener(result -> {

                        detectedText = result.getText();

                        parsePrice();

                        listener.onPriceDetected(
                                detectedPrice,
                                priceText
                        );

                    })

                    .addOnFailureListener(e -> {

                        detectedPrice = 0;
                        priceText = "OCR FAILED";

                        listener.onPriceDetected(
                                detectedPrice,
                                priceText
                        );

                    });

        }catch(Exception e){

            detectedPrice = 0;
            priceText = "OCR ERROR";

            listener.onPriceDetected(
                    detectedPrice,
                    priceText
            );

        }

    }

    private void parsePrice(){

        try{

            Matcher matcher =
                    Pattern.compile("\\d+\\.\\d+")
                            .matcher(detectedText);

            double bestPrice = 0;

            while(matcher.find()){

                try{

                    double value =
                            Double.parseDouble(
                                    matcher.group()
                            );

                    if(value >= 1000 && value <= 10000){

                        if(value > bestPrice){

                            bestPrice = value;

                        }

                    }

                }catch(Exception ignored){}

            }

            detectedPrice = bestPrice;

            if(bestPrice > 0){

                priceText = "OCR SUCCESS";

            }else{

                priceText = "PRICE NOT FOUND";

            }

        }catch(Exception e){

            detectedPrice = 0;
            priceText = "OCR ERROR";

        }

    }

}
