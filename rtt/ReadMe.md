# Enhance Test
### Question 1 ###

As a Web UI test: Query any existing Used Car listing and confirm that the following details
are shown for that car and that the values always match:
* Number plate
* Kilometers
* Body
* Seats
 - - - -
#### *Disclaimer* : #### 
I have no experience in Java.
I am not familiar with the build environment of Java. Yet, I can build my project as a single jar file. The project is far from a deployment quality. 

I have used  Eclipse as an IDE and  Maven as the package manager.

- - - - 

## Approach




My project consists of two stages:
1. trademe.nz has quite a populated car listing (32,000+).
In order to decrease the workload by preventing looking for the same link more than once, the script first stores links as a dictionary. The dictionary's key is the link and its value is a list of queries that hits. This makes the queries and serializes the results as json file.

        /rtt/src/main/java/com/maven/enhance/rtt/App.java 
    

2.  The test case deserializes the json file and runs a single test step in a data-oriented manner.

        /rtt/src/test/java/com/enhance/rtt/testcases/TC_001_Car_Page.java
    


- - - -

## How to use it
1. We need a chromedriver.exe that matches with the installed chrome version. chromedriver.exe must be under the drivers folder.

2. Navigate to  /rtt/src/main/java/com/maven/enhance/rtt/App.java . I strongly recommend to limit the query.
```java

	public static void main(String[] args) {	
		
//		int[] odometer = { 100, 1000, 5000, 10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000, 120000, 140000, 160000, 180000, 200000, 250000, 300000 };
//		int[] seats = { 1, 2, 3, 4, 5, 6, 7, 8 };
//		String[] body = { "convertible", "coupe", "hatchback", "sedan", "stationwagon", "suv", "ute", "van", "other" };
		
		int[] odometer = { 100, 1000, 5000 };
		int[] seats = { 1, -1};
		String[] body = { "convertible", "hatchback" };	

		QueryResultManager manager = QueryResultManager.Deserialize();
		QueryResultPopulator qrp = new QueryResultPopulator();
		qrp.Init();
		qrp.Populate(manager,odometer,seats,body);
		manager.Serialize();
			

	};
```
QueryResultPopulator's Populate method combines given parameters.
Body parameter has no trick. Populate method just loops over it.
But odometer and seats parameters do it differently. 
The method creates min, max pairs from the given values for odometer and seats.
When any of the given values is -1, It checks all possible values.
```java
int[] odometer = { 100, 1000, 5000 };
// {odometer_min, odometer_max}
// {100, 1000}, {100, 5000}, {1000, 5000};

int[] seats = { 1, -1};
// {seats_min, seats_max}
// {1, Any}
```

3. Run it! It will eventually create QueryResults.json file under data folder.
```json

"https://www.trademe.co.nz/a/motors/cars/peugeot/208/listing/3348608453": {
    "URLLink": "https://www.trademe.co.nz/a/motors/cars/peugeot/208/listing/3348608453",
    "query": [
      {
        "_odometer_min": 100,
        "_odometer_max": 1000,
        "_seats_min": 1,
        "_seats_max": -1,
        "_body_style": "hatchback"
      },
      {
        "_odometer_min": 100,
        "_odometer_max": 5000,
        "_seats_min": 1,
        "_seats_max": -1,
        "_body_style": "hatchback"
      },
      {
        "_odometer_min": 1000,
        "_odometer_max": 5000,
        "_seats_min": 1,
        "_seats_max": -1,
        "_body_style": "hatchback"
      }
    ]
  },
  "https://www.trademe.co.nz/a/motors/cars/mercedes-benz/b-180/listing/3458969978": {
    "URLLink": "https://www.trademe.co.nz/a/motors/cars/mercedes-benz/b-180/listing/3458969978",
    "query": [...  
```

4. Now data is ready for the test run! Run test run!

But before the test, I initialize chrome with headless options. I believe it is a huge performance booster. And deserialize json file. 

```java 
	@DataProvider(name = "Static")
	public Object[][] TestDataGenerator() {
		return manager.TestProvider();
	}

	@Test(dataProvider = "Static")
	public void TestCarPage(String key, QueryResult qr) {

		CarPage car_page = new CarPage(driver, key);		
		Reporter.log(key);
		car_page.GetCarAttributes();
		Reporter.log(String.format(car_attr_formatter, car_page.GetOdometer(), car_page.GetSeatsNumber(),
				car_page.GetBodyStyle(), car_page.GetPlateNumber()));

		boolean test_status = true;
		int i = 0;
		for (Query q : qr.query) {
			
			Reporter.log("---Query #" + (++i) + "---");
			test_status &= q.ValidateOdometer(car_page.GetOdometer());
			test_status &= q.ValidateSeatsNumber(car_page.GetSeatsNumber());
			test_status &= q.ValidatePlateNumber(car_page.GetPlateNumber());
			test_status &= q.ValidateBodyStyle(car_page.GetBodyStyle());
			Reporter.log("------------------");
		}
		
		Reporter.log("Step STATUS : " + (test_status? "PASS":"FAIL"));
		assertTrue(test_status);
		System.out.println("-------------------------");
	}
```
This is the body of the test. I preferred single assertion because single assertion interrupts the current test step. But we already have data in hand why not check it!

5. Time to see the report.

        /rtt/test-output/emailable-report.html

#### Overall Test Result
![picture alt](https://github.com/Rhinoffensive/EnhanceTask_1/blob/master/rtt/gitpic/test_overall.PNG?raw=true "Overall Test Result")

#### Failed Test Step
![picture alt](https://github.com/Rhinoffensive/EnhanceTask_1/blob/master/rtt/gitpic/fail_step.PNG?raw=true "Failed Test Step")

#### Passed Test Step
![picture alt](https://github.com/Rhinoffensive/EnhanceTask_1/blob/master/rtt/gitpic/pass_step.PNG?raw=true "Passed Test Step")
















