package com.xaubuddy.app;

public class MT5Connector {

    private boolean connected = false;
    private boolean liveMode = false;

    private String status = "DISCONNECTED";

    private String server = "";
    private String account = "";

    public void connect(
            String server,
            String account,
            String password,
            boolean live
    ){

        this.server = server;
        this.account = account;
        this.liveMode = live;

        if(
                server == null ||
                account == null ||
                password == null
        ){
            connected = false;
            status = "LOGIN FAILED";
            return;
        }

        if(
                server.trim().isEmpty() ||
                account.trim().isEmpty() ||
                password.trim().isEmpty()
        ){
            connected = false;
            status = "LOGIN FAILED";
            return;
        }

        connected = true;

        if(liveMode){
            status = "CONNECTED LIVE";
        }else{
            status = "CONNECTED DEMO";
        }
    }

    public void disconnect(){

        connected = false;
        liveMode = false;

        status = "DISCONNECTED";

        server = "";
        account = "";
    }

    public boolean isConnected(){
        return connected;
    }

    public boolean isLive(){
        return liveMode;
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
