package com.flappy.markets.STHelpers;

/**
 * Created by zanema on 4/18/15.
 */
public class UpcomingPrice {
    private long millisecondsRemaining;
    private double price;

    public UpcomingPrice(long millisecondsRemaining, double price) {
        this.millisecondsRemaining = millisecondsRemaining;
        this.price = price;
    }

    public long getMillisecondsRemaining() {
        return millisecondsRemaining;
    }

    public double getPrice() {
        return price;
    }
}
