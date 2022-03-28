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

trademe.nz has quite a populated car listing. (32,000+)
In order to decrease the workload by preventing looking for the same link more than once, the script first stores links as a dictionary. The dictionary's key is the link and its values are list of queries that hits.


My project consists of two stages.
1.  /rtt/src/main/java/com/maven/enhance/rtt/App.java 
    This makes the queries and serializes the results as json file.
2. /rtt/src/test/java/com/enhance/rtt/testcases/TC_001_Car_Page.java
    This deserializes the json file and runs a single test step in a data-oriented manner.

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











