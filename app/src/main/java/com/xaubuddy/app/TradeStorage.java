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


        editor.putInt(
                "TOTAL",
                total
        );


        editor.putInt(
                "WINS",
                wins
        );


        editor.putInt(
                "LOSSES",
                losses
        );


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



}
