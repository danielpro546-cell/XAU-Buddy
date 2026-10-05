package com.xaubuddy.app;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONObject;

import java.io.IOException;

public class MarketData {


    public double price = 0.0;

    public double ema20 = 0.0;
    public double ema50 = 0.0;
    public double rsi14 = 0.0;


    public String h1Bias = "WAITING";
    public String m5Signal = "WAITING";


    // API KEY ထည့်ရန်
    private final String API_KEY = "4b21ab3f9f75467fb9346fc62966d58e";


    private OkHttpClient client = new OkHttpClient();



    public void updateLiveData(){


        String url =
        "https://api.twelvedata.com/price?symbol=XAU/USD&apikey="
        + API_KEY;



        Request request =
                new Request.Builder()
                .url(url)
                .build();



        client.newCall(request)
        .enqueue(new Callback(){



            @Override
            public void onFailure(
                    Call call,
                    IOException e
            ){

                h1Bias = "API ERROR";
                m5Signal = "WAITING";

            }




            @Override
            public void onResponse(
                    Call call,
                    Response response
            ) throws IOException {



                try {


                    String result =
                            response.body().string();



                    JSONObject json =
                            new JSONObject(result);



                    if(!json.has("price")){

                        h1Bias = "DATA ERROR";
                        m5Signal = "WAITING";
                        return;

                    }



                    price =
                    Double.parseDouble(
                    json.getString("price")
                    );



                    // Temporary indicator calculation

                    ema20 = price - 2;

                    ema50 = price - 5;


                    rsi14 = 55;



                    // H1 Trend

                    if(ema20 > ema50){

                        h1Bias = "BULLISH";

                    }
                    else{

                        h1Bias = "BEARISH";

                    }



                    // M5 Signal

                    if(rsi14 > 50){

                        m5Signal = "BUY SETUP";

                    }
                    else{

                        m5Signal = "SELL SETUP";

                    }



                }
                catch(Exception e){


                    h1Bias = "DATA ERROR";
                    m5Signal = "WAITING";


                }

            }


        });


    }


}
