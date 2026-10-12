package com.xaubuddy.app;

import android.graphics.Bitmap;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class PriceDetector {

    public interface PriceListener{
        void onPriceDetected(double price,String status);
    }

    public double detectedPrice = 0;
    public String priceText = "";
    public String detectedText = "";

    public void detect(Bitmap bitmap, PriceListener listener){

        detectedPrice = 0;
        priceText = "";
        detectedText = "";

        recognize(bitmap, listener);

    }

    private void recognize(Bitmap bitmap, PriceListener listener){

        try{

            InputImage image =
                    InputImage.fromBitmap(bitmap,0);

            TextRecognition
                    .getClient(
                            TextRecognizerOptions.DEFAULT_OPTIONS
                    )
                    .process(image)

                    .addOnSuccessListener(result -> {

                        detectedText = result.getText();

                        try{

                            java.util.regex.Matcher matcher =
                                    java.util.regex.Pattern
                                            .compile("\\d{4}\\.\\d{2}")
                                            .matcher(detectedText);

                            detectedPrice = 0;

                            while(matcher.find()){

                                double price =
                                        Double.parseDouble(
                                                matcher.group()
                                        );

                                if(price >= 4150 && price <= 4200){

                                    detectedPrice = price;

                                }

                            }

                            if(detectedPrice > 0){

                                priceText = "OCR SUCCESS";

                            }else{

                                priceText = "PRICE NOT FOUND";

                            }

                        }catch(Exception e){

                            detectedPrice = 0;
                            priceText = "OCR ERROR";

                        }

                        listener.onPriceDetected(
                                detectedPrice,
                                priceText
                        );

                    })

                    .addOnFailureListener(e -> {

                        detectedPrice = 0;
                        priceText = "OCR FAILED";

                        listener.onPriceDetected(
                                0,
                                priceText
                        );

                    });

        }catch(Exception e){

            detectedPrice = 0;
            priceText = "OCR ERROR";

            listener.onPriceDetected(
                    0,
                    priceText
            );

        }

    }

}
