package com.maven.enhance.rtt;

public class App {
	public static void main(String[] args) {	
		
//		int[] odometer = { 100, 1000, 5000, 10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000, 120000, 140000, 160000, 180000, 200000, 250000, 300000 };
//		int[] seats = { 1, 2, 3, 4, 5, 6, 7, 8 };//
//		String[] body = { "convertible", "coupe", "hatchback", "sedan", "stationwagon", "suv", "ute", "van", "other" };
//		
		int[] odometer = { 100, 1000, 5000 };
		int[] seats = { 1 ,-1};
		String[] body = { "convertible", "hatchback" };	

		QueryResultManager manager = QueryResultManager.Deserialize();
		QueryResultPopulator qrp = new QueryResultPopulator();
		qrp.Init();
		qrp.Populate(manager,odometer,seats,body);
		manager.Serialize();
			

	};

}
