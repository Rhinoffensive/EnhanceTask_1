package com.maven.enhance.rtt;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static java.lang.System.out;

import java.io.File;

public class QueryResultPopulator {

	ChromeDriver driver = null;
	String rootURL = "https://www.trademe.co.nz/a/motors/cars/";

	public void Init() {
		System.setProperty("webdriver.chrome.driver", "drivers" + File.separator + "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();		

		options.addArguments("headless");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public void Populate(QueryResultManager qrm, int[] odometer, int[] seats, String[] body) {

		int total_listing = 0;

		for (int minIndex = 0; minIndex < odometer.length - 1; minIndex++) {
			for (int maxIndex = minIndex + 1; maxIndex < odometer.length; maxIndex++) {
				for (int minSIndex = 0; minSIndex < seats.length - 1; minSIndex++) {
					for (int maxSIndex = minSIndex + 1; maxSIndex < seats.length; maxSIndex++) {
						int seat_min = seats[minSIndex];
						int seat_max = seats[maxSIndex];

						for (String b : body) {
							int odometer_min = odometer[minIndex];
							int odometer_max = odometer[maxIndex];

							Query q = new Query().SetOdometer(odometer_min, odometer_max).SetSeats(seat_min, seat_max)
									.SetBodyType(b);

							int currentPage = 1;
							boolean oCardPresent = true;

							while (oCardPresent) {

//								driver.get(String.format(rootURL
//										+ "search?odometer_min=%d&odometer_max=%d&seats_min=%d&seats_max=%d&body_style=%s&page=%d",
//										odometer_min, odometer_max, seat_min, seat_max, b, currentPage));

								driver.get(q.GetQueryString() + String.format("&page=%d", currentPage));

								List<WebElement> elements = driver.findElementsByCssSelector("div.o-card");

								if (elements.size() <= 0) {
									oCardPresent = false;
								} else {
									int o_card_counter = 1;
									for (int i = 1; i < elements.size(); i++) {

										WebElement elm = elements.get(i);

										try {
											WebElement tag = elm.findElement(By.tagName("a"));
											String address = tag.getAttribute("href");
											out.println(String.format("Page : %d, OCard : %d, link: %s", currentPage,
													o_card_counter, address));

											qrm.Add(new QueryResult(address, q));
											total_listing++;
										} catch (Exception e) {
											out.println(String.format("Page : %d , OCard : %d not found", currentPage,
													o_card_counter));
										}
										o_card_counter++;
									}
								}
								currentPage++;
							}
						}
					}
				}
			}
		}
		out.println("Total listing :" + total_listing);

	}

}
