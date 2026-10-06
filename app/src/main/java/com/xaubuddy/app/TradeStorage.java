package com.xaubuddy.app;


import android.content.Context;
import android.content.SharedPreferences;


public class TradeStorage {


    private SharedPreferences prefs;


    public TradeStorage(Context context){

        prefs = context.getSharedPreferences(
                "XAU_BUDDY_TRADES",
                Context.MODE_PRIVATE
        );

    }



    // =========================
    // JOURNAL STATISTICS
    // =========================

    public void saveStats(
            int total,
            int wins,
            int losses,
            double profit
    ){

        prefs.edit()
                .putInt("TOTAL", total)
                .putInt("WINS", wins)
                .putInt("LOSSES", losses)
                .putFloat("PROFIT",(float)profit)
                .apply();

    }



    public int getTotal(){

        return prefs.getInt("TOTAL",0);

    }



    public int getWins(){

        return prefs.getInt("WINS",0);

    }



    public int getLosses(){

        return prefs.getInt("LOSSES",0);

    }



    public double getProfit(){

        return prefs.getFloat("PROFIT",0);

    }





    // =========================
    // CURRENT TRADE SAVE
    // =========================


    public void saveTrade(

            String status,

            String type,

            String lastResult,

            double entry,

            double current,

            double profit

    ){

        prefs.edit()

        .putString("STATUS",status)

        .putString("TYPE",type)

        .putString("LAST_RESULT",lastResult)

        .putFloat("ENTRY",(float)entry)

        .putFloat("CURRENT",(float)current)

        .putFloat("TRADE_PROFIT",(float)profit)

        .apply();

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



    public String getLastResult(){

        return prefs.getString(
                "LAST_RESULT",
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





    // =========================
    // TRADE SAVED FLAG
    // =========================


    public void saveTradeSaved(boolean saved){

        prefs.edit()

        .putBoolean(
                "TRADE_SAVED",
                saved
        )

        .apply();

    }



    public boolean getTradeSaved(){

        return prefs.getBoolean(
                "TRADE_SAVED",
                false
        );

    }





    // =========================
    // LAST TRADE
    // =========================


    public void saveLastTrade(String trade){

        prefs.edit()

        .putString(
                "LAST_TRADE",
                trade
        )

        .apply();

    }



    public String getLastTrade(){

        return prefs.getString(
                "LAST_TRADE",
                "NO TRADE"
        );

    }





    // =========================
    // FULL JOURNAL HISTORY
    // =========================


    public void saveJournal(String history){

        prefs.edit()

        .putString(
                "TRADE_HISTORY",
                history
        )

        .apply();

    }





    public String getJournal(){

        return prefs.getString(
                "TRADE_HISTORY",
                ""
        );

    }





    // Clear data (Testing)

    public void clearAll(){

        prefs.edit()
        .clear()
        .apply();

    }


}
