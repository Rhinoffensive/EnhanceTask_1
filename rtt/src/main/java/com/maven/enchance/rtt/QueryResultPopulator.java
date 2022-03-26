package com.maven.enchance.rtt;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static java.lang.System.out;

public class QueryResultPopulator {

	ChromeDriver driver = null;
	String rootURL = "https://www.trademe.co.nz/a/motors/cars/";

	int[] odometer = { 100, 1000,  5000, 10000 };

	
	public void Init() {
		System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();

		options.addArguments("headless");
		driver = new ChromeDriver(options);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		
	}


	public void Populate(QueryResultManager qrm) {

		int total_listing = 0;

		for (int minIndex = 0; minIndex < odometer.length - 1; minIndex++) {
			for (int maxIndex = minIndex + 1; maxIndex < odometer.length; maxIndex++) {

				int odometer_min = odometer[minIndex];
				int odometer_max = odometer[maxIndex];

				out.println("odometer min: " + odometer_min + ", odometer max" + odometer_max);
				
				int currentPage = 1;
				boolean isHaveAnyOCard = true;

				while (isHaveAnyOCard) {

					driver.get(String.format(
							rootURL + "search?odometer_min=%d&odometer_max=%d&seats_min=4&seats_max=4&body_style=convertible&page=%d",
							odometer_min, odometer_max, currentPage));
					// Thread.sleep(10000);
					List<WebElement> elements = driver.findElementsByCssSelector("div.o-card");

					if (elements.size() <= 0) {
						isHaveAnyOCard = false;
					} else {
						int o_card_counter = 1;
						for (int i = 1; i < elements.size(); i++) {

							WebElement elm = elements.get(i);

							try {
								WebElement tag = elm.findElement(By.tagName("a"));

								String address = tag.getAttribute("href");
								out.println(String.format("Page : %d, OCard : %d, link: %s", currentPage,
										o_card_counter, address));
								
								Query q = new Query().SetOdometer(odometer_min, odometer_max).SetSeats(4, 4).SetBodyType("convertible");
								//qrm.Add(new QueryResult(address, new int[] {odometer_min,odometer_max},new int[] {4,4} , "convertible"));
								total_listing++;
							} catch (Exception e) {
								out.println(
										String.format("Page : %d , OCard : %d not found", currentPage, o_card_counter));

							}

							o_card_counter++;
						}

					}
					currentPage++;

				}
			}

		}

		out.println("Total listing :" + total_listing);

	}



}
