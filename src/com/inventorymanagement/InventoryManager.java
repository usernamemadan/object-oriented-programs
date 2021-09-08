package com.inventorymanagement;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class InventoryManager {
	public static void main(String[] args) {
		
		List<JSONObject> list = InventoryFactory.getList();
		Map<String,Double> map = new HashMap<String,Double>();
		
		for (JSONObject jsonObject : list) {
			String type = (String) jsonObject.get("type");
			String name = (String) jsonObject.get("name");
			double price = (double) jsonObject.get("price");
			double weight = (double) jsonObject.get("weight");
			System.out.println("Type: " +type+ " Name: " +name+ ", price : " +price+ ", weight : " +weight);
			map.put(name, price*weight);
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
			Writer writer = new FileWriter("/Users/madanar/eclipse-workspace/YML training/employee.java/Object oriented programs/data/inventoryResultOuput.json");
			writer.write(mainObject.toJSONString());
			writer.flush();
			writer.close();
			System.out.println("output is successfully wirtten into a file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


