package com.inventory;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JSONInventory {

	public static void main(String[] args) {
		Map<String,Double> map = new HashMap<String,Double>();
		JSONParser jsonParser = new JSONParser();
		try {
			Reader reader = new FileReader("/Users/madanar/eclipse-workspace/YML training/employee.java/Object oriented programs/data/inventory.json");
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			reader.close();
			JSONArray array = (JSONArray) jsonObject.get("inventory");
			Iterator iterator = array.iterator();
			while(iterator.hasNext()) {
				JSONObject jsonObject2 = (JSONObject) iterator.next();
				System.out.println(jsonObject2);
				String type = (String) jsonObject2.get("type");
				String name = (String) jsonObject2.get("name");
				double price = (double) jsonObject2.get("price");
				double weight = (double) jsonObject2.get("weight");
				System.out.println("Type: " +type+ " Name: " +name+ ", price : " +price+ ", weight : " +weight);
				map.put(name, price*weight);
			}
			
		}
		catch(IOException | ParseException e)
		{
			e.printStackTrace();
		}
		
		writeJson(map);
	}
	
	
	private static void writeJson(final Map<String, Double> map)
	{
		JSONArray array = new JSONArray();
		
		for(java.util.Map.Entry<String, Double> entry : map.entrySet()){
			JSONObject object = new JSONObject();
			object.put("name", entry.getKey());
			object.put("totalPrice", entry.getValue());
			array.add(object);
		}
		
		JSONObject mainObject = new JSONObject();
		mainObject.put("results", array);
		
		try {
			Writer writer = new FileWriter("/Users/madanar/eclipse-workspace/YML training/employee.java/Object oriented programs/data/results.json");
			writer.write(mainObject.toJSONString());
			writer.flush();
			writer.close();
			System.out.println("output is successfully wirtten into a file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}
