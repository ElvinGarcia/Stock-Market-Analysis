package com.mac286.project;

import java.util.Vector;
import java.io.FileReader;
import java.io.*;

public class Helper {
    //Write a static method that accepts a path and a file name
    //it opens the file (file symbols) and reads it and creates a
    //Vector of strings of all files.
    // make sure to pass in: "src/com/mac286/project/Data/" as the path when using loadSymbols()
    public static Vector<String> loadSymbols(String path, String file){
        //TODO: Create a Vector symbols
        Vector<String> symbols = new Vector<String>();
        //Open the input file and read line by line, trim the string
        //and add it to the Vector symbols
        File currFile = new File(path+file);
        try {
            BufferedReader br = new BufferedReader(new FileReader(currFile));
            // read file line by line
            String st;
            while ((st = br.readLine()) != null) {
                symbols.add(st);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //return the vector
        return symbols;
    }

    //Write a Static method that accepts a Vector of Trades and goes

    //through it to compute all statistics, return the statistics as
    //an object.
    public static Statistics computeStats(Vector<Trade> trades) {
        // Initialize variables for long trades
        double totalProfitLong = 0.0;
        double totalHoldingPeriodLong = 0.0;
        int winningCountLong = 0;
        int totalCountLong = 0;

        // Initialize variables for short trades
        double totalProfitShort = 0.0;
        double totalHoldingPeriodShort = 0.0;
        int winningCountShort = 0;
        int totalCountShort = 0;

        // Loop through each trade to calculate totals
        for (Trade trade : trades) {
            if (trade.getDir() == Direction.LONG) {
                totalCountLong++;
                double profit = trade.percentPL();
                totalProfitLong += profit;
                totalHoldingPeriodLong += trade.getHoldingPeriod();
                if (profit > 0) {
                    winningCountLong++;
                }
            } else if (trade.getDir() == Direction.SHORT) {
                totalCountShort++;
                double profit = trade.percentPL();
                totalProfitShort += profit;
                totalHoldingPeriodShort += trade.getHoldingPeriod();
                if (profit > 0) {
                    winningCountShort++;
                }
            }
        }

        // Create a Statistics object to hold the results
        Statistics stats = new Statistics();

        // Calculate averages and winning percentages for long trades
        if (totalCountLong > 0) {
            // avg profit for long trades
            stats.averageProfitLong = totalProfitLong / totalCountLong;
            // avg holding period for long trades
            stats.averageHoldingPeriodLong = totalHoldingPeriodLong / totalCountLong;
            // avg profit / day for long trades
            stats.averageProfitPerDayLong = stats.averageProfitLong / stats.averageHoldingPeriodLong;
            // win percentage for long trades
            stats.winningPercentLong = (winningCountLong / (double) totalCountLong) * 100;
        }

        // Calculate averages and winning percentages for short trades
        if (totalCountShort > 0) {
            // avg profit for short trades
            stats.averageProfitShort = totalProfitShort / totalCountShort;
            // avg holding period for short trades
            stats.averageHoldingPeriodShort = totalHoldingPeriodShort / totalCountShort;
            // avg profit / day for short trades
            stats.averageProfitPerDayShort = stats.averageProfitShort / stats.averageHoldingPeriodShort;
            // win percentage  short trades
            stats.winningPercentShort = (winningCountShort / (double) totalCountShort) * 100;
        }

        // Calculate overall statistics
        int totalCount = totalCountLong + totalCountShort;
        if (totalCount > 0) {
            //  avg profit
            stats.averageProfit = (totalProfitLong + totalProfitShort) / totalCount;
            // avg holding period
            stats.averageHoldingPeriod = (totalHoldingPeriodLong + totalHoldingPeriodShort) / totalCount;
            // avg profit/day
            stats.averageProfitPerDay = stats.averageProfit / stats.averageHoldingPeriod;
            // WTF is this not working
            //  winning percentage
            stats.winningPercent = ((winningCountLong + winningCountShort) / (double) totalCount) * 100;
        }

        return stats;
    }


}