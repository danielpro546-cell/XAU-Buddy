package com.xaubuddy.app;


import android.content.Context;
import android.content.SharedPreferences;


public class TradeStorage {


    private SharedPreferences prefs;



    public TradeStorage(Context context){

        prefs =
                context.getSharedPreferences(
                        "XAU_BUDDY_TRADES",
                        Context.MODE_PRIVATE
                );

    }



    public void saveStats(
            int total,
            int wins,
            int losses,
            double profit
    ){

        SharedPreferences.Editor editor =
                prefs.edit();


        editor.putInt("TOTAL", total);

        editor.putInt("WINS", wins);

        editor.putInt("LOSSES", losses);

        editor.putFloat(
                "PROFIT",
                (float)profit
        );


        editor.apply();

    }





    public int getTotal(){

        return prefs.getInt(
                "TOTAL",
                0
        );

    }



    public int getWins(){

        return prefs.getInt(
                "WINS",
                0
        );

    }



    public int getLosses(){

        return prefs.getInt(
                "LOSSES",
                0
        );

    }



    public double getProfit(){

        return prefs.getFloat(
                "PROFIT",
                0
        );

    }





    // SAVE TRADE STATE

    public void saveTrade(

            String status,

            String type,

            double entry,

            double current,

            double profit

    ){

        SharedPreferences.Editor editor =
                prefs.edit();



        editor.putString(
                "STATUS",
                status
        );


        editor.putString(
                "TYPE",
                type
        );


        editor.putFloat(
                "ENTRY",
                (float)entry
        );


        editor.putFloat(
                "CURRENT",
                (float)current
        );


        editor.putFloat(
                "TRADE_PROFIT",
                (float)profit
        );


        editor.apply();

    }





    public String getTradeStatus(){

        return prefs.getString(
                "STATUS",
                "NO TRADE"
        );

    }



    public String getTradeType(){

        return prefs.getString(
                "TYPE",
                "NONE"
        );

    }



    public double getTradeEntry(){

        return prefs.getFloat(
                "ENTRY",
                0
        );

    }



    public double getTradeCurrent(){

        return prefs.getFloat(
                "CURRENT",
                0
        );

    }



    public double getTradeProfit(){

        return prefs.getFloat(
                "TRADE_PROFIT",
                0
        );

    }





    // PREVENT DUPLICATE JOURNAL AFTER RESTART


    public void saveTradeClosed(
            boolean closed
    ){

        SharedPreferences.Editor editor =
                prefs.edit();


        editor.putBoolean(
                "TRADE_CLOSED",
                closed
        );


        editor.apply();

    }




    public boolean isTradeClosed(){

        return prefs.getBoolean(
                "TRADE_CLOSED",
                false
        );

    }





    // SAVE LAST RESULT


    public void saveLastResult(
            String result
    ){

        SharedPreferences.Editor editor =
                prefs.edit();


        editor.putString(
                "LAST_RESULT",
                result
        );


        editor.apply();

    }




    public String getLastResult(){

        return prefs.getString(
                "LAST_RESULT",
                "NONE"
        );

    }



}
