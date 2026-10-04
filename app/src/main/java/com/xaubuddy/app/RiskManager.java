package com.xaubuddy.app;

public class RiskManager {

    public double entry = 0;
    public double sl = 0;
    public double tp1 = 0;
    public double tp2 = 0;

    public String rr = "1:2";


    public void calculate(double price, String signal){

        entry = price;


        if(signal.equals("BUY")){

            sl = entry - 10;
            tp1 = entry + 10;
            tp2 = entry + 20;

        }


        else if(signal.equals("SELL")){

            sl = entry + 10;
            tp1 = entry - 10;
            tp2 = entry - 20;

        }


        else{

            sl = 0;
            tp1 = 0;
            tp2 = 0;

        }

    }

}
