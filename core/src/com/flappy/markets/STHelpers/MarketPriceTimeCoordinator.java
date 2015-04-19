package com.flappy.markets.STHelpers;

/**
 * Created by zanema on 4/18/15.
 */
public class MarketPriceTimeCoordinator {

    private MarketDataProvider marketDataProvider;
    private long startTime;
    private long timePerPrice;
    private int totalPrices;

    public MarketPriceTimeCoordinator(long startTime, long timePerPrice, int totalPrices) {
        marketDataProvider = new MarketDataProvider(totalPrices);
        this.startTime = startTime;
        this.timePerPrice = timePerPrice;
        this.totalPrices = totalPrices;
    }

    public UpcomingPrice getUpcomingPrice(long currentTime) {
        long elapsedTime = currentTime - startTime;

        return new UpcomingPrice(elapsedTime % timePerPrice, marketDataProvider.get((int)(elapsedTime / timePerPrice) + 1));
    }

    public double getPrice(long currentTime) {
        long elapsedTime = currentTime - startTime;
        return marketDataProvider.get((int)(elapsedTime / timePerPrice));
    }
}