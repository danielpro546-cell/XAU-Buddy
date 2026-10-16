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
    public String detectedText = "";
    public String priceText = "";

    public void detect(Bitmap bitmap, PriceListener listener){

        detectedPrice = 0;
        detectedText = "";
        priceText = "";

        try{

            InputImage image =
                    InputImage.fromBitmap(bitmap,0);

            TextRecognition
                    .getClient(
                            TextRecognizerOptions.DEFAULT_OPTIONS
                    )
                    .process(image)

                    .addOnSuccessListener(result -> {

                        detectedText =
                                result.getText();

                        parsePrice();

                        listener.onPriceDetected(
                                detectedPrice,
                                priceText
                        );

                    })

                    .addOnFailureListener(e -> {

                        priceText = "OCR FAILED";

                        listener.onPriceDetected(
                                0,
                                priceText
                        );

                    });

        }catch(Exception e){

            priceText = "OCR ERROR";

            listener.onPriceDetected(
                    0,
                    priceText
            );

        }

    }


    private void parsePrice(){

        Matcher matcher =
                Pattern.compile(
                        "\\d{4}[\\.,]\\d{2}"
                )
                .matcher(detectedText);


        while(matcher.find()){

            try{

                String clean =
                        matcher.group()
                                .replace(",",".");

                double value =
                        Double.parseDouble(clean);


                // XAUUSD Range Filter
                if(value >= 4000 &&
                   value <= 4500){

                    detectedPrice = value;

                    break;
                }


            }catch(Exception ignored){}

        }


        if(detectedPrice > 0){

            priceText =
                    "OCR SUCCESS";

        }else{

            priceText =
                    "PRICE NOT FOUND";

        }

    }

}
