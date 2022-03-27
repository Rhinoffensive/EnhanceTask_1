package com.maven.enchance.rtt;


public class App {

	public static void main(String[] args) {
			
//		int[] odometer = { 100, 1000, 5000, 10000 };
//		int[] seats = { 1, 2, 3, 4, 5, 6, 7, 8 };//
//		String[] body = { "convertible", "coupe", "hatchback", "sedan", "stationwagon", "suv", "ute", "van", "other" };
//		
		int[] odometer = { 100, 1000 };
		int[] seats = { 1 ,-1};
		String[] body = { "convertible" };	
		


		QueryResultManager manager = QueryResultManager.Deserialize();
		QueryResultPopulator qrp = new QueryResultPopulator();
		qrp.Init();
		qrp.Populate(manager,odometer,seats,body);
		manager.Serialize();
			

	};

}
