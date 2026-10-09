package com.xaubuddy.app;

public class RiskManager {

    public double entry = 0;
    public double sl = 0;
    public double tp1 = 0;
    public double tp2 = 0;

    public double balance = 10.0;
    public double riskPercent = 0.5;
    public double riskMoney = 0.05;
    public double lotSize = 0.01;
    public double riskDistance = 15.0;
    public double actualRiskMoney = 0;
    public boolean minimumLotWarning = false;

    public String rr = "1:2";

    // D Prime / Doo Prime XAUUSD assumptions
    public double contractSize = 100.0;
    public double minLot = 0.01;
    public double lotStep = 0.01;

    public void calculate(double price, String signal) {

        riskMoney = round(
                balance * riskPercent / 100.0
        );

        riskDistance = calculateRiskDistance(price);

        calculateLotSize();

        if ("BUY".equals(signal)) {

            entry = round(price);
            sl = round(entry - riskDistance);
            tp1 = round(entry + riskDistance);
            tp2 = round(entry + riskDistance * 2);
            rr = "1:2";

        } else if ("SELL".equals(signal)) {

            entry = round(price);
            sl = round(entry + riskDistance);
            tp1 = round(entry - riskDistance);
            tp2 = round(entry - riskDistance * 2);
            rr = "1:2";
        }
    }

    private double calculateRiskDistance(double price) {

        if (price >= 4000) {
            return 15.0;
        } else if (price >= 3000) {
            return 12.0;
        } else {
            return 10.0;
        }
    }

    private void calculateLotSize() {

        double rawLot = riskMoney /
                (riskDistance * contractSize);

        minimumLotWarning = rawLot < minLot;

        if (rawLot < minLot) {
            lotSize = minLot;
        } else {
            lotSize = Math.floor(
                    rawLot / lotStep
            ) * lotStep;
        }

        lotSize = round(lotSize);

        actualRiskMoney = round(
                lotSize * riskDistance * contractSize
        );
    }

    public void setBalance(double value) {

        if (value > 0) {
            balance = value;
        }
    }

    public void setRiskPercent(double value) {

        if (value > 0 && value <= 10) {
            riskPercent = value;
        }
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
