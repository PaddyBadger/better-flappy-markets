package com.flappy.markets.STHelpers;

/**
 * Created by zanema on 4/18/15.
 */
public class UpcomingValue {
    private long timeRemaining;
    private double value;

    public UpcomingValue(long timeRemaining, double value) {
        this.timeRemaining = timeRemaining;
        this.value = value;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public double getValue() {
        return value;
    }
}
