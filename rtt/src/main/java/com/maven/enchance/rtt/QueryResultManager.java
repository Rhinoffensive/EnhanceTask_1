package com.maven.enchance.rtt;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class QueryResultManager {

	public Map<String, QueryResult> QueryResults;

	public QueryResultManager() {
		QueryResults = new HashMap<String, QueryResult>();
	}
	
	public QueryResultManager(Map<String, QueryResult>  qrs) {
		QueryResults = qrs;
	}
	
	public Object[][] TestProvider(){
		
		Object[][] res = new Object[QueryResults.size()][2];
		int i = 0;
		for(String key : QueryResults.keySet()) {
			Object[] pair = new Object[] {key,QueryResults.get(key)};
			res[i++] = pair;
		}
		return res;
	}

//	public boolean Contains(Query qr) {
//		return QueryResults.containsKey(qr.URLLink);
//	}

	public void Serialize() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String jsonString = gson.toJson(QueryResults);

		File myObj = new File("data"+File.separator+"QueryResults.json");
		try {
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
				FileWriter myWriter = new FileWriter("data"+File.separator+"QueryResults.json");
				myWriter.write(jsonString);
				myWriter.close();
			} else {
				System.out.println("File already exists.");
				FileWriter myWriter = new FileWriter("data"+File.separator+"QueryResults.json");
				myWriter.write(jsonString);
				myWriter.close();

			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		// System.out.print(jsonString);
	}

	public static QueryResultManager Deserialize() {
		File myObj = new File("data"+File.separator+"QueryResults.json");
		String jsonFile = "";
		boolean parsed = false;
		try {
			if (myObj.exists()) {
				FileReader reader = new FileReader(myObj);
				int i;
				while ((i = reader.read()) != -1)
					jsonFile += (char) i;
				parsed = true;
				reader.close();

			} else {
				System.out.println("File does not exists.");

			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		//System.out.print(jsonFile);
		if (parsed) {
			Gson gson = new GsonBuilder().create();

			Type type = new TypeToken<Map<String, QueryResult>>() {
			}.getType();

			// HashMap<String, QueryResult> outputList = gson.fromJson(jsonFile, type);
			Map<String, QueryResult> tempRes;// = new HashMap<String, QueryResult>();
			tempRes = gson.fromJson(jsonFile, type);
			return new QueryResultManager(tempRes);
		}
		return new QueryResultManager();

	}

	public void Add(QueryResult qr) {
		if(!QueryResults.containsKey(qr.URLLink))
			QueryResults.put(qr.URLLink,qr);
		else
			QueryResults.get(qr.URLLink).AddQuery(qr.query.get(0));
		
	}

}
