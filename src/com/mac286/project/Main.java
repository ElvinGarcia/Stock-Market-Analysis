package com.mac286.project;

import java.util.Vector;

/*
In this class you'll design a main to test all stocks and ETFs
1- create a Tester with the appropriate path, file name, and risk factor
2- Call run
3- Get the trades
4- Call helper function that will compute all statistics from the vector trades
 */
public class Main {
    public static void main(String[] args) {
        // if you are testing a risk based on stop-loss and target
        float[] riskFactor = {0.5f, 1f, 2f, 5f, 10f};
        //set path to appropriate path
        String path = "src/com/mac286/project/Data/";
        //set file to appropriate file
        String fileStocks = "Stocks.txt", fileETFs = "ETFs.txt";

        //loop through the risk array
        for (float v : riskFactor) {
            System.out.printf("\n************* Current Risk: %.1f *************\n\n", v);

            // Calculate the stats for Stocks:
            // create a Tester with arguments path, file, and riskFactor v
            Tester testerStock = new Tester(path, fileStocks, v);
            // call run method on the tester
            testerStock.run();
            // get the trades vector getTrades()
            Vector<Trade> tradesStock = testerStock.getTrades();
            // call the helper method computeStats with the trade vector as input
            Statistics statsStock = Helper.computeStats(tradesStock);

            // display the results using the toString of the Statistics method
            System.out.println("************* Stock Statistics *************");
            System.out.println(statsStock);

            // Calculate the stats for ETFs:
            // create a Tester with arguments path, file, and riskFactor v
            Tester testerETF = new Tester(path, fileETFs, v);
            // call run method on the tester
            testerETF.run();
            // get the trades vector getTrades()
            Vector<Trade> tradesETF = testerETF.getTrades();
            // call the helper method computeStats with the trade vector as input
            Statistics statsETF = Helper.computeStats(tradesETF);

            // display the results using the toString of the Statistics method
            System.out.println("\n************* ETF Statistics *************");
            System.out.println(statsETF);

            // Calculate the Combined Stats:
            tradesStock.addAll(tradesETF);
            Statistics all = Helper.computeStats(tradesStock);

            // display the results using the toString of the Statistics method
            System.out.println("\n************* Combined Statistics *************");
            System.out.println(all);
        }
    }
}