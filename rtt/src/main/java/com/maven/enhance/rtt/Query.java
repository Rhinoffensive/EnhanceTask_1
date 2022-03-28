package com.maven.enhance.rtt;

import org.testng.Reporter;

public class Query {
	// "https://www.trademe.co.nz/a/motors/cars/search?odometer_min=100&odometer_max=1000&seats_min=4&seats_max=4&body_style=convertible"
	private transient final String BaseSearchURL = "https://www.trademe.co.nz/a/motors/cars/search?";
	private int _odometer_min;
	private int _odometer_max;
	private transient String odometer_string = "odometer_min=%d&odometer_max=%d";

	private int _seats_min;
	private int _seats_max;
	private transient String seats_string = "seats_min=%d&seats_max=%d";

	private String _body_style;
	private transient String body_style_string = "body_style=%s";

	public Query SetOdometer(int odometer_min, int odometer_max) {
		_odometer_min = odometer_min;
		_odometer_max = odometer_max;

		if (odometer_max == -1) {
			odometer_string = "odometer_min=%d";
		}
		if (odometer_min == -1) {
			odometer_string = "odometer_max=%d";
		}
		return this;
	}

	public boolean ValidateOdometer(int value) {
		System.out.println("Odometer: " + this._odometer_min + " <= " + value + " <= " + this._odometer_max);
		boolean isOk = (_odometer_min <= value || (_odometer_min == -1 ? true : false))	&& (_odometer_max >= value || (_odometer_max == -1 ? true : false));
		Reporter.log("Check odometer: " + this._odometer_min + "<= actual_val :(" + value + ") <= " + this._odometer_max
				+ " STATUS: " + (isOk ? "PASS" : "FAIL"));

		return isOk;
	}

	public boolean ValidateSeatsNumber(int value) {
		System.out.println("Seats: " + this._seats_min + " <= " + value + " <= " + this._seats_max);
		boolean isOk = (_seats_min <= value || (_seats_min == -1 ? true : false))
				&& (_seats_max >= value || (_seats_max == -1 ? true : false));
		Reporter.log("Check seats: " + this._seats_min + "<= actual_val :(" + value + ") <= " + this._seats_max
				+ " STATUS: " + (isOk ? "PASS" : "FAIL"));

		return isOk;
	}

	public boolean ValidateBodyStyle(String value) {
		System.out.println("Body style: " + this._body_style + " == " + value);
		boolean isOk = this._body_style.toLowerCase().equals(value.toLowerCase());
		Reporter.log("Check body: expected(" + this._body_style + ") == actual(" + value + ")" + " STATUS: "
				+ (isOk ? "PASS" : "FAIL"));
		return isOk;
	}

	public boolean ValidatePlateNumber(String value) {

		boolean isOk = !(value == null) ? true : false;
		Reporter.log("Check plate : " + value + " STATUS: " + (isOk ? "PASS" : "FAIL"));
		return isOk;

	}

	public Query SetSeats(int seats_min, int seats_max) {
		_seats_min = seats_min;
		_seats_max = seats_max;

		if (_seats_max == -1) {
			seats_string = "seats_min=%d";
		}
		if (_seats_min == -1) {
			seats_string = "seats_max=%d";
		}
		return this;
	}

	public Query SetBodyType(String body_style) {
		_body_style = body_style;
		return this;
	}

	public String GetQueryString() {
		return BaseSearchURL + String.format(odometer_string, _odometer_min, _odometer_max) + "&"
				+ String.format(seats_string, _seats_min, _seats_max) + "&"
				+ String.format(body_style_string, _body_style);
	}

	// int[] odometer = { 100, 1000, 5000, 10000, 20000, 30000, 40000, 50000, 60000,
	// 70000, 80000, 90000, 100000, 120000,140000, 160000, 180000, 200000, 250000,
	// 300000 };

}
