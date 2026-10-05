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

    private final String API_KEY = "YOUR_API_KEY";

    private OkHttpClient client = new OkHttpClient();


    public void updateLiveData(){

        String url =
        "https://api.twelvedata.com/price?symbol=XAU/USD&apikey="
        + API_KEY;


        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e){

                h1Bias = "API ERROR";

            }


            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {


                try {

                    String data = response.body().string();

                    JSONObject json =
                    new JSONObject(data);


                    price =
                    json.getDouble("price");


                    // Temporary indicator logic
                    ema20 = price;
                    ema50 = price;


                    rsi14 = 50;


                    if(ema20 > ema50){
                        h1Bias = "BULLISH";
                    }
                    else{
                        h1Bias = "BEARISH";
                    }


                    if(rsi14 > 50){
                        m5Signal = "BUY SETUP";
                    }
                    else{
                        m5Signal = "SELL SETUP";
                    }


                }catch(Exception e){

                    h1Bias = "DATA ERROR";

                }

            }

        });

    }
}
