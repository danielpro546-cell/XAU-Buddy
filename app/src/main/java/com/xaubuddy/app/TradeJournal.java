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


        loadJournal();

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



        storage.saveLastTrade(record);


        saveJournal();

    }





    private void saveJournal(){


        String data = "";


        for(String trade : history){

            data += trade;

            data += "\n\n";

        }


        storage.saveJournal(data);


    }





    private void loadJournal(){


        String data =
                storage.getJournal();



        if(!data.equals("")){


            String[] trades =
                    data.split("\n\n");


            for(String t : trades){

                if(!t.trim().equals("")){

                    history.add(t);

                }

            }


        }


    }





    public double getWinRate(){


        if(totalTrades == 0){

            return 0;

        }


        return
        ((double)wins / totalTrades) * 100;


    }





    public String getLastTrade(){


        if(history.size()==0){

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
