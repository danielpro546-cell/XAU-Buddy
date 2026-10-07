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



        public double movePrice(String signal){

    if(!running){
        return demoPrice;
    }

    double move = (Math.random() * 6) - 3;
    demoPrice += move;

    return demoPrice;
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
