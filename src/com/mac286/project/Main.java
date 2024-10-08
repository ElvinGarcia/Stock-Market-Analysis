package com.mac286.project;

import java.io.File;

/*
In this class you desing you main to test All stocks and
ETFs
1- create a Tester with the appropriate path and file name and risk factor
2- Call run
3- Get the trades
4- Call helper function that will compute all statistics from the vector trades
 */
public class Main {
    public static void main(String[] args) {
        //If you are testing a risk based on stoploss and target
        float[] riskFactor = {0.5f, 1f, 2f, 5f, 10f};
        //set path to appropriate path ("C:/ProfOmarMAC286/Spring2024/Data/)

        final File folder = new File("src/com/mac286/project/Data");

        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }



        //set file to appropriate file (stocks.txt)


        //loop through your risk array and do the following
        // Create a Tester with with path file and riskFactor[i]
        //Call run method on the tester
        // get the trades vector getTrades()
        //call the helper method computerStates with the trade vector as input
        //display the results using the toString of the Statistics method

        //Change the filename to ETFs.txt and do the same.
    }
}