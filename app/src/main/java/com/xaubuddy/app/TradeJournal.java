package com.xaubuddy.app;


import java.util.ArrayList;


public class TradeJournal {


    private TradeStorage storage;


    public ArrayList<String> history =
            new ArrayList<>();


    public int totalTrades = 0;

    public int wins = 0;

    public int losses = 0;


    public double totalProfit = 0;



    public TradeJournal(
            TradeStorage storage
    ){

        this.storage = storage;


        String lastTrade =
                storage.getLastTrade();


        if(!lastTrade.equals("NO TRADE")){

            history.add(lastTrade);

        }

    }




    public void addTrade(
            String type,
            double entry,
            double exit,
            double profit,
            String result
    ){


        if(result.equals("NONE")){

            return;

        }



        totalTrades++;



        if(result.equals("WIN")){

            wins++;

        }

        else if(result.equals("LOSS")){

            losses++;

        }



        totalProfit += profit;



        String record =

                "TRADE #"
                + totalTrades

                + "\nTYPE: "
                + type

                + "\nENTRY: "
                + entry

                + "\nEXIT: "
                + exit

                + "\nRESULT: "
                + result

                + "\nPROFIT: "
                + profit;



        history.add(record);



        // SAVE LAST TRADE

        storage.saveLastTrade(record);


    }





    public double getWinRate(){


        if(totalTrades == 0){

            return 0;

        }


        return
        ((double)wins / totalTrades) * 100;


    }





    public String getLastTrade(){


        if(history.size() == 0){

            return "NO TRADE";

        }


        return history.get(
                history.size()-1
        );


    }





    public void loadStats(
            int total,
            int win,
            int loss,
            double profit
    ){


        totalTrades = total;

        wins = win;

        losses = loss;

        totalProfit = profit;


    }


}
