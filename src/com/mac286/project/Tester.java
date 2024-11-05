package com.mac286.project;

import java.util.Vector;

/*
Tester will receive a file and path as well as a riskFactor or
how many days until exit
1- In the constructor create a Vector of all symbols (the Vector should be
a member variable of the class)
2- In the constructor, create a Vector of trades, to collect all trades
Have a method run() that will do the follwing:
1- If the Vector of symbols or Trades is empty create again or exit
2- Go through the Vector of symbols one by one and test the symbol using
a SymbolTester class, that you would have modified.
3- collect the trades from the SymbolTester each time a symbol
is tested.
4- hve a method reset() that resets the Vector of symbols and trades
if needed.
 */
public class Tester {
    private Vector<String> vSymbols;
    private Vector<Trade> mTrades;
    private String mPath, mFile;
    public Tester(String path, String file, float risk){
        //set path and file
        mPath = path;
        mFile = file;
        //create a vector of Trades
        mTrades = new Vector<Trade>();
        //set risk to risk
        this.risk = risk;
        //open the file and create a Vector of symbols using Helpers
        vSymbols = Helper.loadSymbols(path, file);
    }
    public void setPath(String p){
        mPath = p;
    }
    public void setFile(String f){
        mFile = f;
    }
    public boolean run() {
        if (vSymbols == null || vSymbols.isEmpty()) {
            vSymbols = Helper.loadSymbols(mPath, mFile); // create a new one part

            //            if (vSymbols.isEmpty()) {
            // create a symbol Tester with appropriate parameters
            //    System.out.println("No symbols found. Exiting.");
            //    return false;
        }
        //if mTrades null create a new one.
        if (mTrades == null) {
            mTrades = new Vector<Trade>(); //create a new one part
        }
        //go through the vSymbols, for each symbol,
        for (String symbol : vSymbols) {
            // create a symbol Tester with appropriate parameters
            SymbolTester symbolTester = new SymbolTester(symbol, mPath, risk);
            symbolTester.test();//Test the symbol (calling .test() method)
            //collect the trades by calling getTrades() method of the SymbolTester
            Vector<Trade> trades = symbolTester.getTrades();
            for (Trade trade : trades) {
                mTrades.add(trade);
            }
        }
        return true;
    }

    public Vector<Trade> getTrades(){
        return mTrades;
    }
    public void reset(){
        vSymbols = null;
        mTrades = null;
        vSymbols = Helper.loadSymbols(mPath, mFile); //reload
    }
}