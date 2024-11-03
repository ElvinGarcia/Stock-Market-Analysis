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
    public static Statistics computeStats(Vector<Trade> trades){
        //Create a Statistics object

        //go through Vector trades one by one and compute all the Stats

        //return your object.

        return null;
    }
}