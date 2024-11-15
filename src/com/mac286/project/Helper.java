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
        double totalProfitLong = 0.0;
        double totalHoldingPeriodLong = 0.0;
        double totalProfitShort = 0.0;
        double totalHoldingPeriodShort = 0.0;
        int winningCountLong = 0;
        int winningCountShort = 0;
        int totalCountLong = 0;
        int totalCountShort = 0;

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

        Statistics stats = new Statistics();

        // Calc avg and win % for long trades
        stats.averageProfitLong = totalCountLong > 0 ? totalProfitLong / totalCountLong : 0.0;
        stats.averageHoldingPeriodLong = totalCountLong > 0 ? totalHoldingPeriodLong / totalCountLong : 0.0;
        stats.winningPercentLong = totalCountLong > 0 ? (winningCountLong / (double) totalCountLong) * 100 : 0.0;

        // Calc avg and win % for short trades
        stats.averageProfitShort = totalCountShort > 0 ? totalProfitShort / totalCountShort : 0.0;
        stats.averageHoldingPeriodShort = totalCountShort > 0 ? totalHoldingPeriodShort / totalCountShort : 0.0;
        stats.winningPercentShort = totalCountShort > 0 ? (winningCountShort / (double) totalCountShort) * 100 : 0.0;

        return stats;
    }

}