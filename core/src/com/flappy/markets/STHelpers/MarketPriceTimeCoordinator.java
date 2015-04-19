package com.flappy.markets.STHelpers;

/**
 * Created by zanema on 4/18/15.
 */
public class MarketPriceTimeCoordinator {

    private MarketDataProvider marketDataProvider;
    private long startTime;

    public long getCurrentTime() {
        return currentTime;
    }

    private long currentTime;
    private long timePerPrice;

    public MarketPriceTimeCoordinator(long startTime, long timePerPrice, int totalPrices) {
        marketDataProvider = new MarketDataProvider(totalPrices);
        this.startTime = startTime;
        this.timePerPrice = timePerPrice;

        this.currentTime = this.startTime;
    }

    public void incrementTime(long delta) {
        this.currentTime += delta;
    }

    public UpcomingPrice getUpcomingPrice() {
        long elapsedTime = currentTime - startTime;

        return new UpcomingPrice(timePerPrice - (elapsedTime % timePerPrice), marketDataProvider.get((int)(elapsedTime / timePerPrice) + 1));
    }

    public double getCurrentPrice() {
        long elapsedTime = currentTime - startTime;
        return marketDataProvider.get((int)(elapsedTime / timePerPrice));
    }
}