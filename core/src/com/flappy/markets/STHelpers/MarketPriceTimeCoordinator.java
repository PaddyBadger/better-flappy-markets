package com.flappy.markets.STHelpers;

/**
 * Created by zanema on 4/18/15.
 */
public class MarketPriceTimeCoordinator implements ValueTimeCoordinator {

    private MarketDataProvider marketDataProvider;
    private long startTime;

    private long getCurrentTime() {
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

    @Override
    public double getCurrentValue() {
        long elapsedTime = currentTime - startTime;
        return marketDataProvider.get((int)(elapsedTime / timePerPrice));
    }

    @Override
    public UpcomingValue getUpcomingValue() {
        long elapsedTime = currentTime - startTime;
        return new UpcomingValue(timePerPrice - (elapsedTime % timePerPrice), marketDataProvider.get((int)(elapsedTime / timePerPrice) + 1));
    }
}
