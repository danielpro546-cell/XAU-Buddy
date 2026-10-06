package com.xaubuddy.app;


public class TradeSimulator {


    public String status = "NO TRADE";

    public String type = "NONE";


    public double entry = 0;

    public double current = 0;


    public double profit = 0;


    public int totalTrades = 0;

    public int winTrades = 0;

    public int lossTrades = 0;


    public double winRate = 0;


    private double tp = 0;

    private double sl = 0;



    public void openTrade(
            String signal,
            double price,
            double stopLoss,
            double takeProfit
    ){


        // Prevent duplicate trade

        if(status.equals("OPEN")){

            return;

        }



        if(
                signal.equals("BUY")
                ||
                signal.equals("SELL")
        ){


            type = signal;

            entry = price;

            sl = stopLoss;

            tp = takeProfit;


            status = "OPEN";


            totalTrades++;


        }


    }





    public void update(
            double price
    ){


        if(!status.equals("OPEN")){

            return;

        }



        current = price;



        if(type.equals("BUY")){


            profit =
                    current - entry;



            if(current >= tp){


                status = "TP1 HIT";

                winTrades++;


                calculateWinRate();


            }


            else if(current <= sl){


                status = "STOP LOSS HIT";


                lossTrades++;


                calculateWinRate();


            }



        }




        else if(type.equals("SELL")){


            profit =
                    entry - current;



            if(current <= tp){


                status = "TP1 HIT";


                winTrades++;


                calculateWinRate();


            }


            else if(current >= sl){


                status = "STOP LOSS HIT";


                lossTrades++;


                calculateWinRate();


            }



        }



    }





    private void calculateWinRate(){


        if(totalTrades > 0){


            winRate =

                    ((double)winTrades
                    /
                    totalTrades)
                    *100;


        }


    }



}
