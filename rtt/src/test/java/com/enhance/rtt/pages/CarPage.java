package com.enhance.rtt.pages;


import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarPage {

	WebDriver driver;

	private int odometer;
	private Pattern odometer_matcher = Pattern.compile("\\d{3,}");
	private int seats_number;
	private Pattern seats_matcher = Pattern.compile("\\d{1,}");
	private String plate = null;
	private String body_style = null;

	public CarPage(WebDriver driver, String Url) {
		this.driver = driver;
		this.driver.get(Url);
	}

	public int GetOdometer() {
		return odometer;
	}

	public int GetSeatsNumber() {
		return seats_number;
	}

	public String GetPlateNumber() {

		return plate;
	}

	public String GetBodyStyle() {
		return body_style;
	}

	public void GetCarAttributes() {
		// List<WebElement> elements =
		// driver.findElements(By.cssSelector(".tm-motors-vehicle-attributes__tag--content"));
		// List<WebElement> elements = driver.findElements(By.cssSelector(".o-tag"));
		// List<WebElement> elements = driver.findElements(By.xpath("//div
		// [@class=\"tm-motors-vehicle-attributes__tag--content\"]"));
		// List<WebElement> vehicle_tags =
		// driver.findElementsByCssSelector(".tm-motors-vehicle-attributes__tag--content");
		String seatsText = driver.findElement(By.xpath(
				"//div [@class=\"tm-motors-vehicle-attributes__tag--content\"]//tg-vehicle-seat-small-svg/../../../.."))
				.getText();
		String odometerText = driver.findElement(By.xpath(
				"//div [@class=\"tm-motors-vehicle-attributes__tag--content\"]//tg-icon[@name=\"vehicle-odometer\"]/../.."))
				.getText();
		String bodyTypeText = driver.findElement(By.xpath(
				"//div [@class=\"tm-motors-vehicle-attributes__tag--content\"]//tg-icon[@name=\"vehicle-car\"]/../.."))
				.getText();
		List<WebElement> textSpans = driver.findElements(By.cssSelector(".o-tag"));
		String numberPlate = null;

	

		for (WebElement elm : textSpans) {
			if (elm.getText().contains("Number plate:")) {
				numberPlate = elm.getText().split("Number plate:")[1];
			}
		}

		odometerText = odometerText.replace(",", "");
		Matcher match_odo = odometer_matcher.matcher(odometerText);
		if (match_odo.find()) {
			odometer = Integer.parseInt(match_odo.group());
		}

		Matcher match_seat = seats_matcher.matcher(seatsText);
		if (match_seat.find()) {
			seats_number = Integer.parseInt(match_seat.group());
		}

		if (numberPlate != null) {
			plate = numberPlate.strip();
		}

		if (bodyTypeText != null) {
			if (bodyTypeText.contains("Body style")) {
				bodyTypeText = bodyTypeText.split("Body style")[1].strip();
			} else {
				bodyTypeText = bodyTypeText.strip();
			}
			body_style = bodyTypeText;
		}

		System.out.println("Seats: " + seats_number);
		System.out.println("Odometer : " + odometer);
		System.out.println("bodyTypeText : " + body_style);
		System.out.println("numberPlate : " + plate);

	}

	/*
	 * public void GetCarAttributes() { //List<WebElement> elements =
	 * driver.findElements(By.cssSelector(
	 * ".tm-motors-vehicle-attributes__tag--content")); List<WebElement> elements =
	 * driver.findElements(By.cssSelector(".o-tag")); //List<WebElement> elements =
	 * driver.findElements(By.
	 * xpath("//div [@class=\"tm-motors-vehicle-attributes__tag--content\"]")); //
	 * List<WebElement> vehicle_tags = // driver.findElementsByCssSelector(
	 * ".tm-motors-vehicle-attributes__tag--content");
	 * System.out.println("Hadi bakalým : "+driver.findElement(By.
	 * xpath("//div [@class=\"tm-motors-vehicle-attributes__tag--content\"]//tg-vehicle-seat-small-svg/../../../.."
	 * )).getText()); for (WebElement elm : elements) {
	 * 
	 * String elm_str = elm.getText(); System.out.println(elm_str);
	 * 
	 * if(elm.findElement(By.xpath(".//tg-svg")) != null) {
	 * System.out.println(elm.findElement(By.xpath(".//tg-svg")).getTagName()); };
	 * 
	 * if (elm_str.contains("km")) { elm_str = elm_str.replace(",",""); Matcher
	 * match = odometer_matcher.matcher(elm_str); if (match.find()) { odometer =
	 * Integer.parseInt(match.group()); } } else if (elm_str.contains("Body style"))
	 * { body_style = elm_str.split("Body style")[1].strip(); } else if
	 * (elm_str.contains("Seats")) { //System.out.println("Seats str : "+elm_str);
	 * Matcher match = seats_matcher.matcher(elm_str); if (match.find()) {
	 * seats_number = Integer.parseInt(match.group()); }
	 * 
	 * }else if(elm_str.contains("Number plate:")) { plate =
	 * elm_str.split("Number plate:")[1]; }
	 * 
	 * //out.println(elm.getText()); }
	 * 
	 * }
	 */

}
