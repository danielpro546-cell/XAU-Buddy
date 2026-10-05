package com.xaubuddy.app;


public class RiskManager {


    public double entry = 0;

    public double sl = 0;

    public double tp1 = 0;

    public double tp2 = 0;


    public double balance = 10;


    public double riskPercent = 0.5;


    public double riskMoney = 0;


    public double lotSize = 0.01;


    public double riskDistance = 0;


    public String rr = "WAITING";




    public void calculate(
            double price,
            String signal
    ){


        entry = price;


        riskMoney =
                balance *
                (riskPercent / 100);



        riskDistance =
                calculateRiskDistance(price);




        calculateLotSize();




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


            sl = 0;

            tp1 = 0;

            tp2 = 0;

            rr = "WAITING";


        }



    }





    private double calculateRiskDistance(
            double price
    ){



        if(price >= 4000){


            return 15;


        }

        else if(price >= 3000){


            return 12;


        }

        else{


            return 10;


        }


    }





    private void calculateLotSize(){



        // Simple XAU lot calculation


        if(riskMoney <= 1){


            lotSize = 0.01;


        }

        else if(riskMoney <= 5){


            lotSize = 0.05;


        }

        else{


            lotSize = 0.10;


        }



    }




}
