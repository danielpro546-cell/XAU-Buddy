package com.xaubuddy.app;


public class TradeSimulator {


    public String status = "NO TRADE";

    public String type = "NONE";


    public double entry = 0;

    public double current = 0;


    public double profit = 0;


    public int totalTrades = 0;

    public int winTrades = 0;



    public double winRate = 0;



    private double tp = 0;

    private double sl = 0;



    public void openTrade(
            String signal,
            double price,
            double stopLoss,
            double takeProfit
    ){


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


        if(status.equals("OPEN")){


            current = price;



            if(type.equals("BUY")){


                profit =
                        current - entry;



                if(current >= tp){


                    winTrades++;

                    status = "TP HIT";


                    calculateWinRate();


                }


                else if(current <= sl){


                    status = "SL HIT";


                    calculateWinRate();


                }



            }





            else if(type.equals("SELL")){


                profit =
                        entry - current;



                if(current <= tp){


                    winTrades++;

                    status = "TP HIT";


                    calculateWinRate();


                }


                else if(current >= sl){


                    status = "SL HIT";


                    calculateWinRate();


                }



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
