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


    public double price = 0;

    public double h1Open = 0;
    public double h1High = 0;
    public double h1Low = 0;
    public double h1Close = 0;


    public double m5Open = 0;
    public double m5High = 0;
    public double m5Low = 0;
    public double m5Close = 0;


    public double ema20 = 0;
    public double ema50 = 0;
    public double rsi14 = 0;


    public String h1Bias = "WAITING";
    public String m5Signal = "WAITING";


    public boolean dataReady = false;


    private int apiFinished = 0;


    private String apiError = "";



    private final String API_KEY =
            "4b21ab3f9f75467fb9346fc62966d58e";



    OkHttpClient client =
            new OkHttpClient();




    public synchronized void updateLiveData(){


        dataReady = false;

        apiFinished = 0;

        apiError = "";



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
            public void onFailure(
                    Call call,
                    IOException e
            ){

                apiError =
                e.getMessage();

                finish();

            }





            @Override
            public void onResponse(
                    Call call,
                    Response response
            ) throws IOException {


                try{


                    String body =
                    response.body().string();



                    JSONObject json =
                    new JSONObject(body);




                    if(!json.has("values")){


                        apiError =
                        json.toString();

                        finish();

                        return;

                    }



                    JSONArray values =
                    json.getJSONArray("values");



                    JSONObject candle =
                    values.getJSONObject(0);



                    ArrayList<Double> closes =
                    new ArrayList<>();



                    for(int i=0;i<values.length();i++){


                        JSONObject c =
                        values.getJSONObject(i);


                        closes.add(
                        Double.parseDouble(
                        c.getString("close")
                        ));


                    }



                    if(interval.equals("1h")){


                        h1Open =
                        Double.parseDouble(
                        candle.getString("open"));


                        h1High =
                        Double.parseDouble(
                        candle.getString("high"));


                        h1Low =
                        Double.parseDouble(
                        candle.getString("low"));


                        h1Close =
                        Double.parseDouble(
                        candle.getString("close"));

                    }



                    if(interval.equals("5min")){


                        m5Open =
                        Double.parseDouble(
                        candle.getString("open"));


                        m5High =
                        Double.parseDouble(
                        candle.getString("high"));


                        m5Low =
                        Double.parseDouble(
                        candle.getString("low"));


                        m5Close =
                        Double.parseDouble(
                        candle.getString("close"));


                        price = m5Close;


                    }


                    if(closes.size()>=20)
                    {
                        ema20 =
                        calculateEMA(closes,20);
                    }


                    if(closes.size()>=50)
                    {
                        ema50 =
                        calculateEMA(closes,50);
                    }


                    rsi14 =
                    calculateRSI(closes,14);



                    finish();



                }
                catch(Exception e){


                    apiError =
                    e.toString();


                    finish();


                }



            }


        });



    }
        private synchronized void finish(){


        apiFinished++;



        if(apiFinished >= 2){



            if(ema20 > 0 && ema50 > 0){


                if(ema20 > ema50){

                    h1Bias = "BULLISH";

                }
                else{

                    h1Bias = "BEARISH";

                }


            }
            else{


                h1Bias = "WAITING";


            }





            if(rsi14 > 50){


                m5Signal = "BUY SETUP";


            }
            else if(rsi14 < 50){


                m5Signal = "SELL SETUP";


            }
            else{


                m5Signal = "WAITING";


            }






            if(
                    price > 0
                    &&
                    h1Close > 0
                    &&
                    m5Close > 0
            ){


                dataReady = true;


            }
            else{


                dataReady = false;


            }


        }



    }






    private double calculateEMA(
            ArrayList<Double> data,
            int period
    ){


        if(data.size() < period){

            return 0;

        }



        double multiplier =
                2.0 / (period + 1);



        double ema =
                data.get(data.size()-1);




        for(
                int i=data.size()-2;
                i>=0;
                i--
        ){


            ema =
            ((data.get(i)-ema)
            *multiplier)
            +ema;


        }



        return ema;


    }






    private double calculateRSI(
            ArrayList<Double> data,
            int period
    ){



        if(data.size() <= period){

            return 50;

        }




        double gain = 0;

        double loss = 0;





        for(
                int i=1;
                i<=period;
                i++
        ){


            double change =
                    data.get(i-1)
                    -
                    data.get(i);



            if(change > 0){

                gain += change;

            }
            else{

                loss -= change;

            }


        }





        if(loss == 0){

            return 100;

        }




        double rs =
                gain / loss;




        return
        100 -
        (100/(1+rs));



    }






    public String getStatus(){


        if(apiError != null
                &&
           apiError.length() > 0){


            return apiError;


        }



        if(dataReady){


            return "DATA READY";


        }



        return "WAITING API";


    }



}
