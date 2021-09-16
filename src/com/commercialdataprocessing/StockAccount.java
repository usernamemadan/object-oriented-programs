package com.commercialdataprocessing;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * This class is used to calculate stock for the user
 */
public class StockAccount implements StockInterface {
    private final String FilePathOfStock = "data/stocks.json";

    private String fileName;
    
    public List<CompanyShares> getCompanyShares() {
        return companyShares;
    }

    public void setCompanyShares(List<CompanyShares> companyShares) {
        this.companyShares = companyShares;
    }

    private JSONArray stocksData;
    List<CompanyShares> companyShares = new ArrayList<CompanyShares>();

    StockAccount(String fileName) {
        this.fileName = fileName;
    }

    public void initialize(){
        try {
            List<CompanyShares> companySharesList = new ArrayList<CompanyShares>();
            FileReader reader = new FileReader(fileName);
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(reader);
            JSONArray companyShares = (JSONArray) obj.get("companyShares");
            if (companyShares == null) {
                return;
            }
            Iterator<JSONObject> iterate = companyShares.iterator();
            while (iterate.hasNext()) {
                CompanyShares companyShare = new CompanyShares();
                JSONObject compShare = iterate.next();
                companyShare.setStockSymbol(compShare.get("stockSymbol").toString());
                companyShare.setNumberOfShares((long) compShare.get("numberOfShares"));

                JSONArray transactions = (JSONArray) compShare.get("transactions");
                Iterator<JSONObject> iterate2 = transactions.iterator();

                List<Transaction> transactionList = new ArrayList<Transaction>();
                while (iterate2.hasNext()) {
                    Transaction transact = new Transaction();
                    JSONObject transaction = iterate2.next();
                    transact.setDateTime(transaction.get("DateTime").toString());
                    transact.setNumberOfShares((long) transaction.get("numberOfShares"));
                    transact.setState((String) transaction.get("State"));
                    transactionList.add(transact);
                }

                companyShare.setTransactions(transactionList);
                companySharesList.add(companyShare);
            }
            this.companyShares = companySharesList;
            System.out.println("Old data found");
        } catch (Exception e) {
            System.out.println("data file not found");
            return;
        }
    }

    
    /** 
     * to calculate total value of stocks of each company
     */
    @Override
    public double valueof() {
        double value = 0;
        for (CompanyShares companyShare : companyShares) {
            value += valueof(companyShare);
        }  
        return value;
    }

    
    /** 
     * to buy the stock 
     */
    @Override
    public void buy(int amount, String symbol) {
        readJSON();
        Iterator<JSONObject> iterate = stocksData.iterator();
        PrintWriter out = new PrintWriter(System.out,true);

        long numberOfShares = 0;
        while (iterate.hasNext()) {
            JSONObject stock = iterate.next();
            if (stock.get("stockSymbol").equals(symbol)) {
                numberOfShares = (long) stock.get("numberOfShares");
            }
        }

        if (amount > numberOfShares) {
            out.println("Insufficient Shares Available");
        }
        else {
            CompanyShares newCompanyShare = null;
            for (CompanyShares companyShare : companyShares) {
                if (companyShare.getStockSymbol().equals(symbol)) {
                    newCompanyShare = companyShare;
                    companyShares.remove(companyShare);
                    break;
                }
            }
            if (newCompanyShare == null) {
                newCompanyShare = new CompanyShares(symbol);
            }

            updateValue(symbol, amount, newCompanyShare ,Transaction.BUY);
        }
        
    }

    
    /** 
     * method to update the company share object after buying and selling the shares
     */
    private void updateValue(String symbol, long numberOfShares, CompanyShares companyShare, String state) {
        readJSON();
        
        long prevShares = companyShare.getNumberOfShares();
        if (state == Transaction.BUY) {
            companyShare.setNumberOfShares(prevShares + numberOfShares);
        }
        else {
            companyShare.setNumberOfShares(prevShares - numberOfShares);
        }
        long millis = System.currentTimeMillis();
        Date dateTime = new Date(millis);
        Transaction transaction = new Transaction(dateTime.toString(), numberOfShares, state);
        companyShare.addTransaction(transaction);
        companyShares.add(companyShare);

        Iterator<JSONObject> iterate = stocksData.iterator();
        
        while (iterate.hasNext()) {
            JSONObject stock = iterate.next();
            if (stock.get("stockSymbol").equals(symbol)) {
                prevShares = (long)stock.get("numberOfShares");
                stock.remove("numberOfShares");
                if (state == Transaction.BUY) {
                    stock.put("numberOfShares", prevShares - numberOfShares);
                }
                else {
                    stock.put("numberOfShares", prevShares + numberOfShares);
                }
            }
        }

        try {
            FileWriter writer = new FileWriter(FilePathOfStock);
            JSONObject result = new JSONObject();
            result.put("stocks", stocksData);
            writer.write(result.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (state == Transaction.BUY) {
            System.out.println("Bought successfully");
        }
        else {
            System.out.println("Sold successfully");
        }
        
    }

    
    /** 
     *  method to sell the stock 
     */
    @Override
    public void sell(int amount, String symbol) {
        readJSON();
        PrintWriter out = new PrintWriter(System.out);
        long numberOfShares = 0;

        for (CompanyShares companyShare : companyShares) {
            if (companyShare.getStockSymbol().equals(symbol)) {
                numberOfShares = companyShare.getNumberOfShares();
            }
        }

        if (numberOfShares == 0 || amount > numberOfShares) {
            out.println("Insufficient Shares available");
        }
        else {
            CompanyShares selectedShare = null;
            for (CompanyShares companyShare : companyShares) {
                if (companyShare.getStockSymbol().equals(symbol)) {
                    selectedShare = companyShare;
                    companyShares.remove(companyShare);
                    break;
                }
            }

            if (selectedShare != null) {
                updateValue(symbol, amount, selectedShare, Transaction.SELL);
            }
        }
    }

    
    /** 
     * Method to save the data to the stock account
     */
    @Override
    public void save(String filename) {
        JSONArray compShares = new JSONArray();
        for (CompanyShares companyShare : companyShares) {
            String stockSymbol = companyShare.getStockSymbol();
            long numberOfShares = companyShare.getNumberOfShares();
            JSONArray transactions = new JSONArray();
            for (Transaction transaction : companyShare.getTransactions()) {
                JSONObject transactionObject = new JSONObject();
                transactionObject.put("DateTime", transaction.getDateTime().toString());
                transactionObject.put("numberOfShares", transaction.getNumberOfShares());
                transactionObject.put("State", transaction.getState());
                transactions.add(transactionObject);
            }
            JSONObject obj = new JSONObject();
            obj.put("stockSymbol", stockSymbol);
            obj.put("numberOfShares", numberOfShares);
            obj.put("transactions", transactions);
            compShares.add(obj);
       }

        JSONObject finalJSON = new JSONObject();
        finalJSON.put("companyShares", compShares);

       try {
           FileWriter writer = new FileWriter(filename);
           writer.write(finalJSON.toJSONString());
           writer.flush();
           writer.close();
       } catch (Exception e) {
           e.printStackTrace();
       }       
    }
    
    /** 
     * to calculate total value of a particular company
     */
    public double valueof(CompanyShares companyShare) {
        readJSON();
        Iterator<JSONObject> iterate = stocksData.iterator();
        double sharePrice = 0.0;
        while (iterate.hasNext()) {
            JSONObject stock = iterate.next();
            if (stock.get("stockSymbol").equals(companyShare.getStockSymbol())) {
                sharePrice = (double) stock.get("sharePrice");
            }
        }
        
        return sharePrice * companyShare.getNumberOfShares();
    }
    
    /**
     * to read the json file
     */
    private void readJSON(){
        try{
            FileReader reader = new FileReader(FilePathOfStock);
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(reader);
            stocksData = (JSONArray) obj.get("stocks");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Method to print the portfolio
     */
    @Override
    public void printReport() {
        PrintWriter out = new PrintWriter(System.out, true);
        out.println("Stock Report");
        out.println("Holding Shares\n");
        for (CompanyShares companyShare : companyShares) {
            out.println("Share Symbol : " + companyShare.getStockSymbol());
            out.println("Number of Shares Holding : " + companyShare.getNumberOfShares());
            double valueEach = 0;
            if (companyShare.getNumberOfShares() != 0) {
                valueEach = valueof(companyShare) / companyShare.getNumberOfShares();
            }
            out.println("Value of each share : " + valueEach);
            out.println("Total Share Value : " + valueof(companyShare));
            out.println();
        }
        out.println("Total Value of portfolio: " + valueof());
    }

}