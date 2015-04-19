package com;

import com.flappy.markets.STHelpers.MarketDataProvider;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.net.URL;

/**
 * Created by zanema on 4/18/15.
 */
public class ResourceLoaderTest {

    @Test
    public void runMyTest() throws Exception {
//        MarketDataProvider mdp = new MarketDataProvider(5);

        URL resource = this.getClass().getResource("/historical_five_day_prices");

        Assert.assertTrue(new File(resource.toURI()).canRead());
    }

}
