package com.xaubuddy.app;


public class DemoPriceSimulator {


    public double demoPrice = 4167.0;


    public boolean running = false;



    public void start(
            double startPrice
    ){

        demoPrice = startPrice;

        running = true;

    }





    public double movePrice(
            String signal
    ){


        if(!running){

            return demoPrice;

        }



        if(signal.equals("BUY")){


            demoPrice += 2;


        }

        else if(signal.equals("SELL")){


            demoPrice -= 2;


        }



        return demoPrice;


    }




    public void reset(
            double price
    ){

        demoPrice = price;

        running = false;

    }


}
