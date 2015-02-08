package edu.brown.cs.kbcole.sentiment_heatmap;

import java.util.HashMap;

import twitter4j.GeoLocation;

public class Cities {
	
	public static String[] cities = {"Providence", "Boston", "New York", "San Francisco", "Los Angeles", "Chicago", 
		"Philadelphia", "Washington", "Baltimore", "Cleveland", "Detroit", "Miami",
		"New Orleans", "Las Vegas", "Seattle", "Cincinatti", "Portland", "Dallas", 
		"San Antonio", "Austin", "Pittsburgh", "Denver", "Nashville"};
	private HashMap<String, GeoLocation> cityCords = new HashMap<>();
			
	public Cities() {
		cityCords.put("Providence", new GeoLocation(41.8236, -71.4222));
		cityCords.put("Boston", new GeoLocation(42.3581, -71.0636));
		cityCords.put("New York", new GeoLocation(40.7127, -74.0059));
//		cityCords.put("San Francisco", new GeoLocation(37.7833, -122.4167));
//		cityCords.put("Los Angeles", new GeoLocation(34.0500, -118.2500));
//		cityCords.put("Chicago", new GeoLocation(41.8369, -87.6847));
//		cityCords.put("Philadelphia", new GeoLocation(39.9500, -75.1667));
//		cityCords.put("Washington", new GeoLocation(38.8951, -77.0367));
//		cityCords.put("Baltimore", new GeoLocation(39.2833, -76.6167));
//		cityCords.put("Cleveland", new GeoLocation(41.4822, -81.6697));
//		cityCords.put("Detroit", new GeoLocation(42.3314, -83.0458));
//		cityCords.put("Miami", new GeoLocation(25.7877, -80.2241));
//		cityCords.put("New Orleans", new GeoLocation(29.9667, -90.0500));
//		cityCords.put("Las Vegas", new GeoLocation(36.1215, -115.1739));
//		cityCords.put("Seattle", new GeoLocation(47.6097, -122.3331));
//		cityCords.put("Cincinatti", new GeoLocation(39.1000, -84.5167));
//		cityCords.put("Portland", new GeoLocation(45.5200, -122.6819));
//		cityCords.put("Dallas", new GeoLocation(32.7758, -96.7967));
//		cityCords.put("San Antonio", new GeoLocation(29.4167, -98.5000));
//		cityCords.put("Austin", new GeoLocation(30.2500, -97.7500));
//		cityCords.put("Pittsburgh", new GeoLocation(40.4417, -80.0000));
//		cityCords.put("Denver", new GeoLocation(39.7618, -104.8811));
//		cityCords.put("Nashville", new GeoLocation(36.1667, -86.7833));
	}
	
	public String[] getCities() {
		return cities;
	}
	
	public HashMap<String, GeoLocation> getCityCoords() {
		return cityCords;
	}
}
