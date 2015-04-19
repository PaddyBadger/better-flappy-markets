package com.flappy.markets.STHelpers;

/**
 * Created by zanema on 4/18/15.
 */
public class UpcomingValue {
    private long timeRemaining;
    private double price;

    public UpcomingValue(long timeRemaining, double price) {
        this.timeRemaining = timeRemaining;
        this.price = price;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public double getPrice() {
        return price;
    }
}
