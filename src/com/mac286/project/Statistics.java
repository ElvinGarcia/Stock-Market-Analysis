package com.mac286.project;

public class Statistics {
    double averageProfit;
    double averageHoldingPeriod;
    double averageProfitPerDay;
    double winningPercent;
    double averageProfitLong;
    double averageHoldingPeriodLong;
    double averageProfitPerDayLong;
    double winningPercentLong;
    //Do the same for shorts
    double averageProfitShort;
    double averageHoldingPeriodShort;
    double averageProfitPerDayShort;
    double winningPercentShort;

    @Override
    public String toString() {
        return "****** Average Stats ******\n" +
                "Average Profit: " + averageProfit + "%" +
                "\nAverage Holding Period: " + averageHoldingPeriod +
                "\nAverage Profit Per Day:" + averageProfitPerDay + "%" +
                "\nWinning Percent: " + winningPercent + "%\n\n" +
                "****** Long Trade Stats ******\n" +
                "Average Profit Long: " + averageProfitLong + "%" +
                "\nAverage Holding Period Long: " + averageHoldingPeriodLong +
                "\nAverage Profit Per Day Long: " + averageProfitPerDayLong + "%" +
                "\nWinning Percent Long: " + winningPercentLong + "%\n\n" +
                "****** Short Trade Stats ******\n" +
                "Average Profit Short: " + averageProfitShort + "%" +
                "\nAverage Holding Period Short: " + averageHoldingPeriodShort +
                "\nAverage Profit Per Day Short: " + averageProfitPerDayShort + "%" +
                "\nWinning Percent Short: " + winningPercentShort + "%";
    }
}