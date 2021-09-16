package com.commercialdataprocessing;

import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Main {
    public static String FilePath = "/Users/madanar/eclipse-workspace/YML training/employee.java/Object oriented programs/data/stocks.json";

    private static StockAccount account = new StockAccount("/Users/madanar/eclipse-workspace/YML training/employee.java/Object oriented programs/data/account.json");

    public static void main(String[] args) {
        account.initialize();

        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out, true);

        out.println("Choose an Option ");
        while (true) {
            out.println("Main Menu");
            out.println("1. Buy Stocks\n2. Sell Stocks\n3. Print Stock Report\n4. Exit");
            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:
                    buyStock();
                    break;
                case 2:
                    sellStock();
                    break;
                case 3:
                    account.printReport();
                    break;
                case 4:
                    return;
                default:
                    out.println("Invalid Option");
                    break;
            }
        }
    }
    
    /**
     * Method to take input from user and sell the stock
     */
    private static void sellStock() {
        PrintWriter out = new PrintWriter(System.out, true);
        Scanner in = new Scanner(System.in);
        
        out.println("Select the stock you want to Sell");
        int count = 1;
        for (CompanyShares companyShare : account.getCompanyShares()) {
            out.println(count + ":");
            out.println("Stock Symbol : " + companyShare.getStockSymbol());
            out.println("Number Of Shares : " + companyShare.getNumberOfShares());
            out.println();
            count++;
        }

        int stockChoice = in.nextInt();
        while (stockChoice >= count) {
            out.println("Invalid option");
            stockChoice = in.nextInt();
        }

        out.println("Enter the amount to sell");
        int amount = in.nextInt();
        CompanyShares selectedStock = account.getCompanyShares().get(stockChoice - 1);
        while (amount > (long) selectedStock.getNumberOfShares() || amount<=0)
        {
            out.println("Enter a valid amount");
            amount = in.nextInt();
        }

        account.sell(amount, selectedStock.getStockSymbol());
        account.save("/Users/madanar/eclipse-workspace/YML training/employee.java/Object oriented programs/data/account.json");
    }

    /**
     * Method to input the stock from user and buy the stock
     */
    private static void buyStock() {
        PrintWriter out = new PrintWriter(System.out, true);
        Scanner in = new Scanner(System.in);

        out.println("Select the stock you want to buy");
        JSONArray stocks = readJSON();
        Iterator<JSONObject> itr = stocks.iterator();
        int count = 1;
        while (itr.hasNext()) {
            out.println(count + ":");
            JSONObject stock = itr.next();
            out.println("Stock Name: " + stock.get("stockName"));
            out.println("Stock Symbol: " + stock.get("stockSymbol"));
            out.println("Share price: " + stock.get("sharePrice"));
            out.println("Number Of Shares: " + stock.get("numberOfShares"));
            out.println();
            count++;
        }
        
        int stockChoice = in.nextInt();
        while (stockChoice >= count) {
            out.println("Invalid option");
            stockChoice = in.nextInt();
        }

        out.println("Enter the amount to buy");
        int amount = in.nextInt();
        JSONObject selectedStock = (JSONObject) stocks.get(stockChoice - 1);
        while (amount > (long) selectedStock.get("numberOfShares") || amount<=0)
        {
            out.println("Enter a valid amount");
            amount = in.nextInt();
        }

        account.buy(amount, (String) selectedStock.get("stockSymbol"));
        account.save("/Users/madanar/eclipse-workspace/YML training/employee.java/Object oriented programs/data/account.json");
    }
    
    
    /** 
     * Method used to read JSON from stocks
     */
    private static JSONArray readJSON() {
        try {
            FileReader reader = new FileReader(FilePath);
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(reader);
            JSONArray stocks = (JSONArray) obj.get("stocks");
            return stocks;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}