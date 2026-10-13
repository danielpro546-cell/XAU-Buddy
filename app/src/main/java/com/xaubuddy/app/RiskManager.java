package com.xaubuddy.app;

public class RiskManager {

    public double entry = 0;
    public double sl = 0;
    public double tp1 = 0;
    public double tp2 = 0;

    public double balance = 10.0;
    public double riskPercent = 0.5;

    public double riskMoney = 0;
    public double lotSize = 0.01;
    public double actualRiskMoney = 0;

    public double contractSize = 1;

    public double minLot = 0.01;
    public double lotStep = 0.01;

    public double riskDistance = 15;

    public boolean canTrade = false;

    public String warning =
            "Waiting for calculation";

    public String rr = "1:2";


    public void calculate(
        double livePrice,
        double detectedPrice,
        String signal
){

        riskMoney =
                round(balance * riskPercent / 100);


        riskDistance = 15;


        double rawLot =
                riskMoney /
                (riskDistance * contractSize);


        if(rawLot < minLot){

            lotSize = minLot;

            actualRiskMoney = riskMoney;

            canTrade = actualRiskMoney <= riskMoney;

warning =
"⚠ Minimum lot 0.01 used (Actual risk is higher)";
        }
        else{

            lotSize =
            Math.floor(
            rawLot / lotStep
            ) * lotStep;


            actualRiskMoney = riskMoney;


            warning = "Risk check passed";
canTrade = actualRiskMoney <= riskMoney;
            
        }

        if(signal.equals("BUY")){

            if(detectedPrice > 0){
    entry = round(detectedPrice);
}else{
    entry = round(livePrice);
}
            sl = round(entry - riskDistance);

            tp1 = round(entry + riskDistance);
            tp2 = round(entry + riskDistance * 2);

        }


        else if(signal.equals("SELL")){

            if(detectedPrice > 0){
    entry = round(detectedPrice);
}else{
    entry = round(livePrice);
}
            sl = round(entry + riskDistance);

            tp1 = round(entry - riskDistance);
            tp2 = round(entry - riskDistance * 2);

        }

    }



    public void setBalance(double value){

        if(value > 0){

            balance = value;

        }

    }



    public void setRiskPercent(double value){

        if(value > 0 && value <=10){

            riskPercent = value;

        }

    }

        public void stopTrade(){

    entry = 0;
    sl = 0;
    tp1 = 0;
    tp2 = 0;

    lotSize = 0;

    riskMoney = 0;
    actualRiskMoney = 0;

    canTrade = false;

warning = "Trade Blocked (AI FINAL = WAIT)";


}



    private double round(double value){

    return Math.round(value * 10000.0)
            /10000.0;

}

}
