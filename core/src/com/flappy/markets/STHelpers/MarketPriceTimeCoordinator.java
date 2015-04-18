package com.flappy.markets.STHelpers;

/**
 * Created by zanema on 4/18/15.
 */
public class MarketPriceTimeCoordinator {

    MarketDataProvider marketDataProvider;
    long epochStartTime;
    long millisecondsPerPrice;
    int totalPrices;

    public MarketPriceTimeCoordinator(long epochStartTime, long millisecondsPerPrice, int totalPrices) {
        marketDataProvider = new MarketDataProvider(totalPrices);
        this.epochStartTime = epochStartTime;
        this.millisecondsPerPrice = millisecondsPerPrice;
        this.totalPrices = totalPrices;
    }

    public UpcomingPrice getUpcomingPrice(long epochTime) {
        long elapsedTime = epochTime - epochStartTime;

        return new UpcomingPrice(elapsedTime % millisecondsPerPrice, marketDataProvider.get((int)(elapsedTime / millisecondsPerPrice) + 1));
    }
}