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

    public double riskDistance = 15.0;

    public String rr = "1:2";

    public void calculate(double price, String signal) {

        riskMoney = round(balance * (riskPercent / 100.0));

        riskDistance = calculateRiskDistance(price);

        calculateLotSize();

        if (signal.equals("BUY")) {

            entry = round(price);
            sl = round(entry - riskDistance);
            tp1 = round(entry + riskDistance);
            tp2 = round(entry + (riskDistance * 2));
            rr = "1:2";

        } else if (signal.equals("SELL")) {

            entry = round(price);
            sl = round(entry + riskDistance);
            tp1 = round(entry - riskDistance);
            tp2 = round(entry - (riskDistance * 2));
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

        if (riskMoney <= 1.0) {
            lotSize = 0.01;
        } else if (riskMoney <= 5.0) {
            lotSize = 0.05;
        } else {
            lotSize = 0.10;
        }
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
