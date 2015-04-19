package com.flappy.markets.STHelpers;

/**
* Created by zanema on 4/18/15.
*/
public class PortfolioTimeCoordinator implements ValueTimeCoordinator{
    private long startTime;
    private long currentTime;
    private long timePerPrice;
    private long timePerBuySellCheck;
    private MarketPriceTimeCoordinator marketPriceTimeCoordinator;

    private double sharesHeld;
    private double purchasePrice;
    private double cashHeld;

    public PortfolioTimeCoordinator(double startingCash, long startTime, long timePerPrice, long timePerBuySellCheck, int totalPrices) {
        this.startTime = startTime;
        this.currentTime = this.startTime;
        this.timePerPrice = timePerPrice;
        this.timePerBuySellCheck = timePerBuySellCheck;

        this.marketPriceTimeCoordinator = new MarketPriceTimeCoordinator(startTime, timePerPrice, totalPrices);

        this.sharesHeld = 0;
        this.cashHeld = startingCash;
    }

    public void buyShares() {
        if (cashHeld > 0) {
            purchasePrice = marketPriceTimeCoordinator.getCurrentValue();
            sharesHeld = cashHeld / purchasePrice;
            cashHeld = 0;
        }
    }

    public void sellShares() {
        if (sharesHeld > 0) {
            cashHeld = marketPriceTimeCoordinator.getCurrentValue() * sharesHeld;
            purchasePrice = 0;
            sharesHeld = 0;
        }
    }

    public void incrementTime(long delta, boolean shouldBuy) {

        this.currentTime += delta;
        this.marketPriceTimeCoordinator.incrementTime(delta);

        if (shouldUpdateDueToDelta(delta)) {
            //buy or sell based on global button state
            if (shouldBuy && !hasShares()) buyShares();
            else if (!shouldBuy && hasShares()) sellShares();
        }
    }

    @Override
    public double getCurrentValue() {
        return cashHeld + marketPriceTimeCoordinator.getCurrentValue() * sharesHeld;
    }

    @Override
    public UpcomingValue getUpcomingValue() {
        UpcomingValue upcomingMarketPrice = marketPriceTimeCoordinator.getUpcomingValue();

        return new UpcomingValue(upcomingMarketPrice.getTimeRemaining(),
                cashHeld + (upcomingMarketPrice.getPrice() * sharesHeld));
    }

    private boolean shouldUpdateDueToDelta(long delta) {
        //check if it will cross a threshold time
        long elapsedTime = this.currentTime - this.startTime;

        return elapsedTime % timePerBuySellCheck < delta;
    }

    private boolean hasShares() {
        return sharesHeld > 0;
    }


}
