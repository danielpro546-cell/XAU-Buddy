package com.xaubuddy.app;

public class MT5Connector {

    public boolean connected = false;

    public String account = "";
    public String server = "";


    public void connect(
            String serverName,
            String accountNumber
    ){

        server = serverName;
        account = accountNumber;

        // Later replace with real API connection

        connected = true;

    }


    public String getStatus(){

        if(connected){
            return "MT5 CONNECTED";
        }
        else{
            return "MT5 DISCONNECTED";
        }

    }


}
