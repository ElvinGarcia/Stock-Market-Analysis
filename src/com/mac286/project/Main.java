package com.mac286.project;

import java.io.File;
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
        // If you are testing a risk based on stop-loss and target
        float[] riskFactor = {0.5f, 1f, 2f, 5f, 10f};
        //set path to appropriate path
        String path = "src/com/mac286/project/Data/";
        String file = "";

        //set file to appropriate file (stocks.txt)
        file = "Stocks.txt";

        //loop through the risk array
        for (float v : riskFactor) {
            // Create a Tester with arguments path, file, and riskFactor v
            Tester tester = new Tester(path, file, v);
            //Call run method on the tester
            tester.run();
            // get the trades vector getTrades()
            Vector<Trade> trades = tester.getTrades();
            //call the helper method computeStats with the trade vector as input
            Statistics stats = Helper.computeStats(trades);
            //display the results using the toString of the Statistics method
            System.out.println(stats);
        }

        //Change the filename to ETFs.txt and do the same.
        file = "ETFs.txt";
        for (float v : riskFactor) {
            Tester tester = new Tester(path, file, v);
            tester.run();
            Vector<Trade> trades = tester.getTrades();
            Statistics stats = Helper.computeStats(trades);
            System.out.println(stats);
        }
    }
}