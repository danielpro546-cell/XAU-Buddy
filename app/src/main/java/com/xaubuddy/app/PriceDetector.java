package com.xaubuddy.app;

import android.graphics.Bitmap;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class PriceDetector {

    public double detectedPrice = 0;
    public String priceText = "";
    public String detectedText = "";

    public void detect(Bitmap bitmap){

        detectedPrice = 0;
        priceText = "";
        detectedText = "";

        recognize(bitmap);

    }

    private void recognize(Bitmap bitmap){

        try{

            InputImage image =
                    InputImage.fromBitmap(bitmap,0);

            TextRecognition
                    .getClient(
                            TextRecognizerOptions.DEFAULT_OPTIONS
                    )
                    .process(image)

                    .addOnSuccessListener(result -> {

                        String text = result.getText();

                        detectedText = text;

                        try{

                            String[] lines = text.split("\\n");

                            detectedPrice = 0;

                            for(String line : lines){

                                java.util.regex.Matcher m =
                                        java.util.regex.Pattern
                                                .compile("(\\d{4}\\.\\d{2})")
                                                .matcher(line);

                                if(m.find()){

                                    detectedPrice =
                                            Double.parseDouble(
                                                    m.group(1)
                                            );

                                    break;
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

                    })

                    .addOnFailureListener(e -> {

                        detectedPrice = 0;
                        priceText = "OCR FAILED";

                    });

        }catch(Exception e){

            detectedPrice = 0;
            priceText = "OCR ERROR";

        }

    }

}
