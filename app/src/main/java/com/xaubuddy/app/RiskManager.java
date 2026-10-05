package com.xaubuddy.app;

public class RiskManager {

    public double entry = 0;
    public double sl = 0;
    public double tp1 = 0;
    public double tp2 = 0;

    public String rr = "1:2";


    public void calculate(
            double price,
            String signal
    ){

        entry = price;


        double riskDistance = 10;


        if(signal.equals("BUY")){


            sl = entry - riskDistance;

            tp1 = entry + riskDistance;

            tp2 = entry + (riskDistance * 2);


            rr = "1:2";


        }


        else if(signal.equals("SELL")){


            sl = entry + riskDistance;

            tp1 = entry - riskDistance;

            tp2 = entry - (riskDistance * 2);


            rr = "1:2";


        }


        else{


            entry = price;

            sl = 0;

            tp1 = 0;

            tp2 = 0;

            rr = "WAITING";


        }

    }

}
