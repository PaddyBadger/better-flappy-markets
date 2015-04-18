package com.flappy.markets.STHelpers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;

/**
 * Created by zanema on 4/18/15.
 */
public class MarketDataProvider {

    private static final String DATA_FILE_PATH = "historical_five_day_prices";
    float[] prices;

    public MarketDataProvider(int dataCount) {
        this.prices = new float[dataCount];

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(DATA_FILE_PATH).getFile());

        int totalDataPoints = 0, initialReadIndex = 0;

        // find the length of the file / number of lines in the file
        try {
            LineNumberReader lnr = new LineNumberReader(new FileReader(file));
            lnr.skip(Long.MAX_VALUE);
            totalDataPoints = lnr.getLineNumber() + 1; //Add 1 because line index starts at 0
            lnr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // randomize initial read point
        if (totalDataPoints > 0) {
            initialReadIndex = (int) Math.floor(Math.random() * (totalDataPoints - prices.length));
        }

        try (Scanner scanner = new Scanner(file)) {
            int lineNum = 0;

            while (lineNum < initialReadIndex) {
                scanner.nextLine();
            }

            int i = 0, len = prices.length;
            while (scanner.hasNextLine() && i < len) {
                String line = scanner.nextLine();
                prices[i++] = Float.parseFloat(line);
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float get(int index) {
        return prices[index];
    }
}
