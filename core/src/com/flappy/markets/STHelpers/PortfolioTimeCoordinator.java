package com.flappy.markets.STHelpers;

/**
 * Created by zanema on 4/18/15.
 */
public class PortfolioTimeCoordinator {
    private long startTime;
    private long timePerPrice;
    private MarketPriceTimeCoordinator marketPriceTimeCoordinator;

    private double sharesHeld;
    private double purchasePrice;
    private double cashHeld;

    public PortfolioTimeCoordinator(double startingCash, long startTime, long timePerPrice, int totalPrices) {
        this.startTime = startTime;
        this.timePerPrice = timePerPrice;
        this.marketPriceTimeCoordinator = new MarketPriceTimeCoordinator(startTime, timePerPrice, totalPrices);

        this.sharesHeld = 0;
        this.cashHeld = startingCash;
    }

    public void buyShares(long currentTime) {
        if (cashHeld > 0) {
            purchasePrice = marketPriceTimeCoordinator.getPrice(currentTime);
            sharesHeld = cashHeld / purchasePrice;
            cashHeld = 0;
        }
    }

    public void sellShares(long currentTime) {
        if (sharesHeld > 0) {
            cashHeld = marketPriceTimeCoordinator.getPrice(currentTime) * sharesHeld;
            purchasePrice = 0;
            sharesHeld = 0;
        }
    }

    public double getCurrentPortfolioValue(long currentTime) {
        return cashHeld + marketPriceTimeCoordinator.getPrice(currentTime);
    }

    public UpcomingPrice getUpcomingPortfolioValue(long currentTime) {
        UpcomingPrice upcomingMarketPrice = marketPriceTimeCoordinator.getUpcomingPrice(currentTime);

        return new UpcomingPrice(upcomingMarketPrice.getTimeRemaining(),
                cashHeld + (upcomingMarketPrice.getPrice() * sharesHeld));
    }
}
