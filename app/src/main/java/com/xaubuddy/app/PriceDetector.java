package com.xaubuddy.app;

import android.graphics.Bitmap;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceDetector {

    public interface PriceListener{
        void onPriceDetected(double price, String status);
    }


    public double detectedPrice = 0;
    public String detectedText = "";
    public String priceText = "";


    public void detect(
            Bitmap bitmap,
            PriceListener listener
    ){

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


                        listener.onPriceDetected(
                                0,
                                "OCR FAILED"
                        );


                    });


        }catch(Exception e){


            listener.onPriceDetected(
                    0,
                    "OCR ERROR"
            );

        }

    }



    private void parsePrice(){


        ArrayList<Double> prices =
                new ArrayList<>();


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



                if(value >= 4000 &&
                   value <= 4500){


                    prices.add(value);


                }


            }catch(Exception ignored){}

        }



        if(prices.size() > 0){


            // current price က ပုံမှန်အားဖြင့်
            // OCR list ထဲမှာ အောက်ပိုင်း/နောက်ဆုံးနားမှာရှိတတ်

            detectedPrice =
                    prices.get(prices.size()-1);



            priceText =
                    "OCR SUCCESS";


        }else{


            priceText =
                    "PRICE NOT FOUND";


        }


    }

}
