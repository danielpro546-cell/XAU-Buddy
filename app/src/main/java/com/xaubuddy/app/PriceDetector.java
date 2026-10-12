package com.xaubuddy.app;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class PriceDetector {

    public double detectedPrice = 0;
    public String priceText = "";
    public boolean found = false;

    public String detectedText = "";
    public int digitCount = 0;

    public int left;
    public int top;
    public int right;
    public int bottom;

    public void detect(Bitmap bitmap){

        found = false;
        detectedPrice = 0;
        priceText = "";
        detectedText = "";
        digitCount = 0;

        left = bitmap.getWidth();
        top = bitmap.getHeight();
        right = 0;
        bottom = 0;

        for(int y=0; y<bitmap.getHeight()/3; y++){

            for(int x=bitmap.getWidth()/2; x<bitmap.getWidth(); x++){

                int c = bitmap.getPixel(x,y);

                int r = Color.red(c);
                int g = Color.green(c);
                int b = Color.blue(c);

                if(r>220 && g>220 && b>220){

                    if(x<left) left=x;
                    if(x>right) right=x;
                    if(y<top) top=y;
                    if(y>bottom) bottom=y;

                    found=true;
                }

            }

        }

        if(found){

            priceText="OCR READY";
            detectedText="PRICE AREA FOUND";
            digitCount=(right-left)/12;

            recognize(bitmap);

        }else{

            priceText="NOT FOUND";
            detectedText="PRICE AREA NOT FOUND";

        }

    }

    private void recognize(Bitmap bitmap){

    try{

        int cropLeft = left;
        int cropTop = top;
        int cropWidth = right - left;
        int cropHeight = bottom - top;


        if(cropWidth <= 0 || cropHeight <= 0){

            detectedPrice = 0;
            priceText = "NO PRICE AREA";
            return;

        }


        Bitmap priceBitmap = Bitmap.createBitmap(
                bitmap,
                cropLeft,
                cropTop,
                cropWidth,
                cropHeight
        );


        InputImage image =
                InputImage.fromBitmap(priceBitmap,0);


        TextRecognition
                .getClient(
                        TextRecognizerOptions.DEFAULT_OPTIONS
                )
                .process(image)


                .addOnSuccessListener(result -> {


                    String text = result.getText();


                    detectedText = text;
                    priceText = "OCR TEXT: " + text;


                    try{

    String[] lines = text.split("\\n");

    detectedPrice = 0;

    for(String line : lines){

        line = line.trim();

        if(line.matches("\\d{4}\\.\\d{2}")){

            detectedPrice = Double.parseDouble(line);

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
        priceText = "CROP ERROR";


    }


    }

}
