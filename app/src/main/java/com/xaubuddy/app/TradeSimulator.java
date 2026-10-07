package com.xaubuddy.app;


public class TradeSimulator {


    private TradeStorage storage;


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




    public TradeSimulator(
            TradeStorage storage
    ){

        this.storage = storage;


        status = storage.getTradeStatus();

        type = storage.getTradeType();

        lastResult = storage.getLastResult();


        entry = storage.getTradeEntry();

        current = storage.getTradeCurrent();

        profit = storage.getTradeProfit();


        totalTrades = storage.getTotal();

        winTrades = storage.getWins();

        lossTrades = storage.getLosses();


        calculateWinRate();

    }





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

            current = price;

            profit = 0;


            sl = stopLoss;

            tp = takeProfit;


            status = "OPEN";

            lastResult = "NONE";


            saveTrade();

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


            profit = current - entry;



            if(current >= tp){

                closeTrade(true);

            }

            else if(current <= sl){

                closeTrade(false);

            }


        }



        else if(type.equals("SELL")){


            profit = entry - current;



            if(current <= tp){

                closeTrade(true);

            }

            else if(current >= sl){

                closeTrade(false);

            }


        }



        saveTrade();

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


        saveTrade();

    }





    public void calculateWinRate(){


        if(totalTrades > 0){


            winRate =
            ((double)winTrades / totalTrades) * 100;


        }

        else{


            winRate = 0;


        }


    }





    private void saveTrade(){


        storage.saveTrade(

                status,

                type,

                lastResult,

                entry,

                current,

                profit

        );


    }





    public void resetTrade(){


        status = "NO TRADE";

        type = "NONE";

        lastResult = "NONE";


        entry = 0;

        current = 0;

        profit = 0;


        tp = 0;

        sl = 0;


        saveTrade();

    }


}
