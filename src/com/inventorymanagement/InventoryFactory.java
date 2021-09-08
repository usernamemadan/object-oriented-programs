package com.inventorymanagement;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class InventoryFactory {
	public static List<JSONObject> getList() {
		
		List<JSONObject> list = new ArrayList<>();
		JSONParser jsonParser = new JSONParser();
		try {
			Reader reader = new FileReader("/Users/madanar/eclipse-workspace/YML training/employee.java/Object oriented programs/data/inventory.json");
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			reader.close();
			JSONArray array = (JSONArray) jsonObject.get("inventory");
			Iterator iterator = array.iterator();
			while(iterator.hasNext()) {
				JSONObject jsonObject2 = (JSONObject) iterator.next();
				list.add(jsonObject2);
			}
			
		}
		catch(IOException | ParseException e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
		
		
	
	
}
