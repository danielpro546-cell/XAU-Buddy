package com.xaubuddy.app;

public class DemoPriceSimulator {

    public double demoPrice = 4167.0;

    public boolean running = false;

    public void start(double startPrice) {

        demoPrice = startPrice;

        running = true;

    }

    public double movePrice(String signal) {

        if (!running) {
            return demoPrice;
        }

        double move = (Math.random() * 4.0) - 2.0;

        if (signal.equals("BUY")) {
            move += 0.5;
        } else if (signal.equals("SELL")) {
            move -= 0.5;
        }

        demoPrice += move;

        return Math.round(demoPrice * 100.0) / 100.0;

    }

    public void reset(double price) {

        demoPrice = price;

        running = false;

    }

}
