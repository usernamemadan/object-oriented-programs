package com.inventory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class StockAccountManagement {

	public static void main(String[] args) {
		writeJson();
		readJson();
	}
	private static void readJson() {
		JSONParser jsonParser = new JSONParser();
		try {
			Reader reader = new FileReader("/Users/madanar/eclipse-workspace/YML training/employee.java/Object oriented programs/data/stock.json");
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			reader.close();
			JSONArray array = (JSONArray) jsonObject.get("stock");
			Iterator iterator = array.iterator();
			while(iterator.hasNext()) {
				JSONObject jsonObject2 = (JSONObject) iterator.next();
				System.out.println(jsonObject2);
				String name = (String) jsonObject2.get("name");
				Long n = (Long) jsonObject2.get("noOfShare");
				Long sharePrice =  (Long) jsonObject2.get("sharePrice");
				System.out.println("Name: " +name+ ", NoOfShare : " +n+ ", sharePrice : " +sharePrice);
				outputStock(name, n ,sharePrice);
			}
			
		}
		catch(IOException | ParseException e)
		{
			e.printStackTrace();
		}
	}
		
	private static void writeJson() {
		JSONArray array = new JSONArray();
		Scanner sc = new Scanner(System.in);
		int choice = 1;
		
		while(choice == 1){
			JSONObject object = new JSONObject();
			System.out.println("Enter the name of the stock");
			String name = sc.nextLine();
			object.put("name", name);
			System.out.println("Enter the number of stocks");
			int n = sc.nextInt();
			sc.nextLine();
			object.put("noOfShare", n);
			System.out.println("Enter the stock price");
			int sharePrice = sc.nextInt();
			sc.nextLine();
			object.put("sharePrice", sharePrice);
			
			array.add(object);
			
			System.out.println("Do you want to add another object \n1.yes \n2.no");
			choice = sc.nextInt();
			sc.nextLine();
		}
		
		JSONObject mainObject = new JSONObject();
		mainObject.put("stock", array);
		
		try {
			Writer writer = new FileWriter("/Users/madanar/eclipse-workspace/YML training/employee.java/Object oriented programs/data/stock.json");
			writer.write(mainObject.toJSONString());
			writer.flush();
			writer.close();
			System.out.println("output is successfully wirtten into a file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void outputStock(String name, Long n, Long sharePrice) {
		JSONObject object = new JSONObject();
		object.put("stockname", name);
		object.put("stockValue", n*sharePrice);
		
		try {
			Writer writer = new FileWriter("/Users/madanar/eclipse-workspace/YML training/employee.java/Object oriented programs/data/totalStock.json");
			writer.write(object.toJSONString());
			writer.flush();
			writer.close();
			System.out.println("output is successfully wirtten into a file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
