package com.xaubuddy.app;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MarketData {


    public double price = 0.0;


    public double h1Open;
    public double h1High;
    public double h1Low;
    public double h1Close;


    public double m5Open;
    public double m5High;
    public double m5Low;
    public double m5Close;



    public double ema20;
    public double ema50;
    public double rsi14;



    public String h1Bias = "WAITING";
    public String m5Signal = "WAITING";



    private final String API_KEY =
            "4b21ab3f9f75467fb9346fc62966d58e";



    OkHttpClient client = new OkHttpClient();



    public void updateLiveData(){


        getCandleData("1h");

        getCandleData("5min");


    }




    private void getCandleData(String interval){


        String url =
        "https://api.twelvedata.com/time_series?symbol=XAU/USD&interval="
        + interval
        + "&outputsize=50&apikey="
        + API_KEY;



        Request request =
                new Request.Builder()
                .url(url)
                .build();



        client.newCall(request).enqueue(new Callback(){


            @Override
            public void onFailure(Call call, IOException e){

                h1Bias = "API ERROR";

            }



            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {


                try{


                    String result =
                            response.body().string();



                    JSONObject json =
                            new JSONObject(result);



                    JSONArray values =
                            json.getJSONArray("values");



                    ArrayList<Double> closes =
                            new ArrayList<>();


                    JSONObject first =
                            values.getJSONObject(0);



                    price =
                            Double.parseDouble(
                            first.getString("close")
                            );



                    for(int i=0;i<values.length();i++){


                        JSONObject candle =
                                values.getJSONObject(i);


                        closes.add(
                                Double.parseDouble(
                                candle.getString("close")
                                )
                        );


                    }



                    ema20 =
                            calculateEMA(closes,20);



                    ema50 =
                            calculateEMA(closes,50);



                    rsi14 =
                            calculateRSI(closes,14);



                    updateSignal();



                }
                catch(Exception e){


                    h1Bias="DATA ERROR";


                }


            }



        });



    }





    private void updateSignal(){


        if(ema20 > ema50){

            h1Bias="BULLISH";

        }
        else{

            h1Bias="BEARISH";

        }



        if(rsi14 > 50){

            m5Signal="BUY SETUP";

        }
        else{

            m5Signal="SELL SETUP";

        }


    }





    private double calculateEMA(
            ArrayList<Double> data,
            int period){


        if(data.size()<period)
            return 0;


        double multiplier =
                2.0/(period+1);



        double ema=data.get(
                data.size()-1
        );



        for(int i=data.size()-2;i>=0;i--){


            ema =
            (data.get(i)-ema)
            *multiplier
            +ema;


        }


        return ema;


    }





    private double calculateRSI(
            ArrayList<Double> data,
            int period){


        if(data.size()<=period)
            return 50;



        double gain=0;
        double loss=0;



        for(int i=1;i<=period;i++){


            double diff =
                    data.get(i-1)
                    -
                    data.get(i);



            if(diff>0)
                gain+=diff;
            else
                loss-=diff;


        }



        if(loss==0)
            return 100;



        double rs =
                gain/loss;



        return
        100-(100/(1+rs));


    }



}
