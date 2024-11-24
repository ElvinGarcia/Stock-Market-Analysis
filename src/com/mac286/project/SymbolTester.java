package com.mac286.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class SymbolTester {
    private float riskFactor; //TODO: Change this to suit your need
    private String mSymbol; // e.g. AAPL
    private String dataPath; //= "C:\Users\oaith\Courses\MAC286\Fall2023\Data\";

    private Vector<Bar> mData; // this is the collection of bars
    private Vector<Trade> mTrades;
    private boolean loaded = false;

    public SymbolTester(String s, String p, float risk) {
        riskFactor = risk;
        mSymbol = s;
        dataPath = p;
        mData = new Vector<Bar>(3000, 100);
        mTrades = new Vector<Trade>(200, 100);
        loaded = false;
    }

    public Vector<Trade> getTrades() {
        return mTrades;
    }
    public void loadData() {
        //create file name
        String fileName = dataPath + mSymbol + "_Daily.csv";
        //"C:\Users\oaith\Courses\MAC286\Fall2023\Data\AAPL_Daily.csv"
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while((line = br.readLine()) != null) {
                //create a bar using the constructor that accepts the data as a String
                Bar b = new Bar(line);
                //add the bar to the Vector
                mData.add(b);
            }
            loaded = true;
            br.close();
            fr.close();
        }catch(IOException e) {
            System.out.println("Something is wrong: " + e.getMessage());
            loaded = false;
            return;
        }
    }
    // checks if the bar at the current index is an x day low (in our case, a 10-day low)
    private boolean xDaysLow(int ind, int days) {
        for (int i = ind-1; i > ind-days; i--) {
            if(mData.elementAt(i).getLow() < mData.elementAt(ind).getLow())
                return false;
        }
        return true;
    }
    // checks if the bar at the current index is an x day high (in our case, a 10-day high)
    private boolean xDaysHigh(int ind, int days) {
        for (int i = ind-1; i > ind-days; i--) {
            if(mData.elementAt(i).getHigh() > mData.elementAt(ind).getHigh())
                return false;
        }
        return true;
    }
    /*
    outcomes() is used once a pattern is spotted, and takes in the trade values and the given index in mData where
    the trade starts. It then, for either the long trade or the short trade case, looks for the close of the given
    trade.
    */
    void outcomes(Trade T, int ind) {
        for(int i = ind; i < mData.size(); i++) {
            if(T.getDir() == Direction.LONG) {
                if(mData.elementAt(i).getHigh() > T.getTarget()) { //it is a win
                    //consider a gap day; a day where no trading occurs, but the security's price either rises or falls
                    if(mData.elementAt(i).getOpen() > T.getTarget()) {
                        //close at open in the gap up day case
                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
                        return;
                    }else {
                        //close the trade at target
                        T.close(mData.elementAt(i).getDate(), T.getTarget(), i-ind);
                        return;
                    }
                } else if(mData.elementAt(i).getLow() < T.getStopLoss()) {
                    //check if there is a gap down
                    if(mData.elementAt(i).getOpen() < T.getStopLoss()) {
                        //get out at the open
                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
                        return;
                    }else {
                        //get out at stoploss
                        T.close(mData.elementAt(i).getDate(), T.getStopLoss(), i-ind);
                        return;
                    }

                }
            }else {// it is a short trade
                if(mData.elementAt(i).getLow() <= T.getTarget()) { //it is a win
                    //consider a gap day
                    if(mData.elementAt(i).getOpen() < T.getTarget()) {
                        //close at open; a gap down day
                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
                        return;
                    }else {
                        //close the trade at target
                        T.close(mData.elementAt(i).getDate(), T.getTarget(), i-ind);
                        return;
                    }
                } else if(mData.elementAt(i).getHigh() >= T.getStopLoss()) {
                    //check if there is a gap down
                    if(mData.elementAt(i).getOpen() > T.getStopLoss()) {
                        //get out at the open
                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
                        return;
                    }else {
                        //get out at stoploss
                        T.close(mData.elementAt(i).getDate(), T.getStopLoss(), i-ind);
                        return;
                    }

                }

            }
        }//end of for
        //if we get here the trade is not closed, close it at the close of the last day
        T.close(mData.elementAt(mData.size()-1).getDate(), mData.elementAt(mData.size()-1).getClose(), mData.size()-1-ind);
    }

    public boolean test() {
        //error handling on data loading
        //loads code if not loaded
        // returns an  error if error can't be loaded
        if(!loaded) {
            loadData();
            if (!loaded) {
                System.out.println("cannot load data");
                return false;
            }
        }

        // The following code contains our pattern:
        // loops through the data.
        // to avoid out of bound exceptions it start at index 10 to data size  -2
        for(int i = 10; i <mData.size()-2; i++) {
            // if the days low are in the last 10 days conditons are met
            if(xDaysLow(i, 10)
                    // Current low is less than previous low
                    && mData.elementAt(i).getLow() < mData.elementAt(i-1).getLow()
                    // Current high is greater than previous high
                    && mData.elementAt(i).getHigh() > mData.elementAt(i-1).getHigh()
                    // Current close is greater than previous close
                    && mData.elementAt(i).getClose() > mData.elementAt(i-1).getClose()
                    // checks the next day's opening price is above the current day's low
                    && mData.elementAt(i+1).getOpen() > mData.elementAt(i).getLow()) {
                // Next day's open is greater than current low
                // We have a trade opportunity: buy at the open of the next day
                float entryprice = mData.elementAt(i+1).getOpen();
                float stoploss = mData.elementAt(i).getLow() - 0.01f; // Set stop-loss just below current low
                float risk = entryprice - mData.elementAt(i).getLow(); // Calculate risk based on entry price and current low
                float target = entryprice + riskFactor * risk; // Target price based on risk and a risk factor

                Trade T = new Trade();

                // Open the trade
                T.open(mSymbol, mData.elementAt(i+1).getDate(), entryprice, stoploss, target, Direction.LONG);
                outcomes(T, i+1);
                //add the trade to the Trade vector
                mTrades.add(T);

                //Short for reverse trade change low to high, high to low larger to smaller and smaller to larger
            }else if(xDaysHigh(i, 10)
                    && mData.elementAt(i).getHigh() > mData.elementAt(i-1).getHigh()
                    && mData.elementAt(i).getLow() < mData.elementAt(i-1).getLow()
                    && mData.elementAt(i).getClose() < mData.elementAt(i-1).getClose()
                    && mData.elementAt(i+1).getOpen() < mData.elementAt(i).getHigh())
            {
                //we have a trade, buy at open of i+1 (tomorrow) stop-loss i.low, target = entry+factor*risk
                float entryprice = mData.elementAt(i+1).getOpen();

                // Set stop-loss just above current high
                float stoploss = mData.elementAt(i).getHigh() + 0.01f;

                float risk = stoploss - entryprice;

                float target = entryprice - riskFactor * risk;
                Trade T = new Trade();
                T.open(mSymbol, mData.elementAt(i+1).getDate(), entryprice, stoploss, target, Direction.SHORT);
                outcomes(T, i+1);
                //add the trade to the Trade vector
                mTrades.add(T);
            }
        }

        return true;
    }

}
