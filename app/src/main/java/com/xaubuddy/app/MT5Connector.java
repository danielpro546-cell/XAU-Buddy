package com.xaubuddy.app;

public class MT5Connector {

    String status = "DISCONNECTED";


    public void connect(
            String server,
            String account,
            String password
    ){

        if(
            server.length() > 0 &&
            account.length() > 0 &&
            password.length() > 0
        ){
            status = "CONNECTED DEMO";
        }
        else{
            status = "LOGIN FAILED";
        }

    }


    public String getStatus(){

        return status;

    }

}
