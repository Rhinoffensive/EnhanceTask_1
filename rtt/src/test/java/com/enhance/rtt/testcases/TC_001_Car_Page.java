package com.enhance.rtt.testcases;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.enhance.rtt.pages.CarPage;
import com.maven.enchance.rtt.Query;
import com.maven.enchance.rtt.QueryResult;
import com.maven.enchance.rtt.QueryResultManager;

public class TC_001_Car_Page {

	WebDriver driver;
	QueryResultManager manager;

	private String car_attr_formatter = "CAR ATTRIBUTES: \n \n\t Odometer: %d, \n\t Seats: %d, \n\t Body Type: %s, \n\t Number Plate: %s";

	@DataProvider(name = "Static")
	public Object[][] TestDataGenerator() {
		return manager.TestProvider();
	}

	@Test(dataProvider = "Static")
	public void TestCarPage(String key, QueryResult qr) {

		CarPage car_page = new CarPage(driver, key);
		// Reporter.log("Link: " + "<a href=\"" + key + "\">" + key + "</a>",);
		Reporter.log(key);
		car_page.GetCarAttributes();
		Reporter.log(String.format(car_attr_formatter, car_page.GetOdometer(), car_page.GetSeatsNumber(),
				car_page.GetBodyStyle(), car_page.GetPlateNumber()));

		boolean test_status = true;

		int i = 0;
		for (Query q : qr.query) {
//			
			Reporter.log("---Query #" + (++i) + "---");
			test_status &= q.ValidateOdometer(car_page.GetOdometer());
			test_status &= q.ValidateSeatsNumber(car_page.GetSeatsNumber());
			test_status &= q.ValidatePlateNumber(car_page.GetPlateNumber());
			test_status &= q.ValidateBodyStyle(car_page.GetBodyStyle());
			Reporter.log("------------------");
		}
		
		Reporter.log("Step STATUS : " + (test_status? "PASS":"FAIL"));
		//assertTrue(test_status);
		assertTrue(test_status);

		System.out.println("-------------------------");

	}

	@BeforeTest
	public void Init() {

		// Reporter.setEscapeHtml(false);
		System.setProperty("webdriver.chrome.driver", "drivers" + File.separator + "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();

		options.addArguments("headless");
		driver = new ChromeDriver(options);
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		manager = QueryResultManager.Deserialize();

	}

}
