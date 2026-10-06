package com.xaubuddy.app;


public class TradeSimulator {


    public String status = "NO TRADE";

    public String type = "NONE";

    public String lastResult = "NONE";


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


                closeTrade(true);


            }

            else if(current <= sl){


                closeTrade(false);


            }


        }




        else if(type.equals("SELL")){


            profit =
                    entry - current;



            if(current <= tp){


                closeTrade(true);


            }

            else if(current >= sl){


                closeTrade(false);


            }


        }



    }





    private void closeTrade(
            boolean win
    ){


        if(win){


            status = "TP HIT";

            lastResult = "WIN";


            winTrades++;


        }

        else{


            status = "SL HIT";

            lastResult = "LOSS";


            lossTrades++;


        }



        calculateWinRate();


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
