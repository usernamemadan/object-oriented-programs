package com.dateandtimequeue;

public interface StockInterface {
	  double valueof();

	    void buy(int amount, String symbol);

	    void sell(int amount, String symbol);

	    void save(String filename);

	    void printReport();
}
