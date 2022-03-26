package com.maven.enchance.rtt;

public class Query {
	//"https://www.trademe.co.nz/a/motors/cars/search?odometer_min=100&odometer_max=1000&seats_min=4&seats_max=4&body_style=convertible"
	private final String BaseSearchURL = "https://www.trademe.co.nz/a/motors/cars/search?";
	private int _odometer_min;
	private int _odometer_max;
	private String odometer_string = "odometer_min=%d&odometer_max=%d";
	
	private int _seats_min;
	private int _seats_max;
	private String seats_string = "seats_min=%d&seats_max=%d";
	
	private String _body_style;
	private String body_style_string = "body_style=%s";
	
	public Query SetOdometer(int odometer_min, int odometer_max) {
		_odometer_min = odometer_min;
		_odometer_max = odometer_max;		
		return this;
	}
	
	public Query SetSeats(int seats_min, int seats_max) {
		_seats_min = seats_min;
		_seats_max = seats_max;
		return this;
	}
	
	public Query SetBodyType(String body_style) {
		_body_style = body_style;
		return this;
	}
	
	public String GetQueryString() {
		return BaseSearchURL+ String.format(odometer_string,_odometer_min,_odometer_max)+"&"
					+String.format(seats_string, _seats_min,_seats_max) +"&"
					+String.format(body_style_string, _body_style);
	}
	
	
	
	int[] odometer = { 100, 1000, 5000, 10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000, 120000,
			140000, 160000, 180000, 200000, 250000, 300000 };

}
