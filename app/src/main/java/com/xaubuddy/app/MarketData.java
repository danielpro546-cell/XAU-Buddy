package com.xaubuddy.app;

import java.util.ArrayList;


public class MarketData {


    public double price = 4167.00;


    public double h1Open = 4160;
    public double h1High = 4170;
    public double h1Low = 4155;
    public double h1Close = 4167;


    public double m5Open = 4165;
    public double m5High = 4168;
    public double m5Low = 4162;
    public double m5Close = 4167;


    public double ema20 = 4165;
    public double ema50 = 4160;
    public double rsi14 = 60;


    public String h1Bias = "BULLISH";

    public String m5Signal = "BUY SETUP";


    public boolean dataReady = false;


    private String status = "WAITING";



    public void updateLiveData(){


        // Demo Feed
        // MT5 Feed later replace here


        price = 4167.00;


        h1Close = price;


        m5Close = price;



        ema20 = 4165;

        ema50 = 4160;


        rsi14 = 60;




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



        dataReady = true;


        status = "DEMO DATA READY";


    }




    public String getStatus(){


        return status;


    }


}
