package com.maven.enchance.rtt;


public class App {

	public static void main(String[] args) {

		

		//QueryResultManager manager = new QueryResultManager();
		QueryResultManager manager = QueryResultManager.Deserialize();
		QueryResultPopulator qrp = new QueryResultPopulator();
		qrp.Init();
		qrp.Populate(manager);
		manager.Serialize();
		
		
		
//		manager.Add(new QueryResult("link1", new int[] { 100, 200 }, 5, "Sedan"));
//		manager.Add(new QueryResult("link2", new int[] { 100, 200 }, 3, "Sedan"));
//		manager.Serialize();

//		QueryResultManager.Deserialize();
//		Map<String, QueryResult> map = QueryResultManager.QueryResults;
//
//		System.out.print(map);
		// "https://www.trademe.co.nz/a/motors/cars/search?odometer_min=100&odometer_max=1000&seats_min=4&seats_max=4&body_style=convertible"

	};

}
