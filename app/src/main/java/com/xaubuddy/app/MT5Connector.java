package com.xaubuddy.app;

public class MT5Connector {

    private String status = "DISCONNECTED";

    private String server;
    private String account;


    public void connect(
            String server,
            String account,
            String password
    ){

        this.server = server;
        this.account = account;


        if(server == null || account == null || password == null){

            status = "LOGIN FAILED";

            return;

        }


        if(
            server.trim().length() > 0 &&
            account.trim().length() > 0 &&
            password.trim().length() > 0
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


    public String getServer(){

        return server;

    }


    public String getAccount(){

        return account;

    }

}
