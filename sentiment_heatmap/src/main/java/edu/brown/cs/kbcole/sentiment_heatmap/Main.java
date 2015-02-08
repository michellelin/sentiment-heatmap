package edu.brown.cs.kbcole.sentiment_heatmap;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;
import twitter4j.GeoLocation;
import twitter4j.JSONException;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;


public class Main {
	
	public static void main(String[] args) {
		String oAuthConsumerKey = "zJUtvGJkQe4nKNsdI6nyytCZ7";
	    String oAuthConsumerSecret = "fbQtnsXUsgYdk2HiSA3KJpSHIwZ0YKLgsiblk9b8dH1fPhPe97";

	    String oAuthAccessToken = "2281706174-B4I05DRdLTXoDoHUOzuRgFDavfZeY07fgXuJ64e";
	    String oAuthAccessTokenSecret = "Pml9w8U1ysADSUeIZDuye4dVkT9DbRLYXtNlXVJGpZ0ht";
	    
	    List<ScoredLocation> positives = new ArrayList<>();
		List<ScoredLocation> negatives = new ArrayList<>();
	    
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	      .setOAuthConsumerKey(oAuthConsumerKey)
	      .setOAuthConsumerSecret(oAuthConsumerSecret)
	      .setOAuthAccessToken(oAuthAccessToken)
	      .setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
	    TwitterFactory tf = new TwitterFactory(cb.build());
	    
	    Twitter twitter = tf.getInstance();
	    
	    populateLists(twitter, positives, negatives);
	    
	    StringBuilder posBuilder = new StringBuilder();
    	for(ScoredLocation pos : positives) {
    		posBuilder.append(pos.toString());
    	}
    	
    	StringBuilder negBuilder = new StringBuilder();
    	for(ScoredLocation neg : negatives) {
    		negBuilder.append(neg.toString());
    	}
    	
	    
		Spark.setPort(4572);
		runSparkServer(posBuilder.toString(), negBuilder.toString());
	}
	
	public static void populateLists(Twitter twitter, List<ScoredLocation> positives, List<ScoredLocation> negatives) {
		Cities cities = new Cities();
		for(String city : cities.getCities()) {
			List<Status> tweets = new ArrayList<>();
	    	Query query = new Query();
	    	//query.resultType(ResultType.recent);
	    	query.setCount(100);
	    	GeoLocation location = cities.getCityCoords().get(city);
	    	if(location == null) {
	    		continue;
	    	}
	    	query.setGeoCode(location, 30, Query.Unit.valueOf("mi"));
	    	try {
				tweets = twitter.search(query).getTweets();
				System.out.println(tweets.size());
		    	for(Status tweet : tweets) {
					double score = Idol.getScore(tweet.getText());
					ScoredLocation scoredLocation = new ScoredLocation(score, tweet.getGeoLocation());
					if(score > 0) {
						positives.add(scoredLocation);
					}
					else if(score < 0) {
						negatives.add(scoredLocation);
					}
		    	}
	    	} catch (Exception e) {
				e.printStackTrace();
			}
	    	
//	    	Writer writer = null;
//
//	    	try {
//	    	    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("positives.txt"), "utf-8"));
//	    	    for(ScoredLocation loc: positives) {
//	    	    	
//	    	    }
//	    	    writer.write();
//	    	} catch (IOException ex) {
//	    	  // report
//	    	} finally {
//	    	   try {writer.close();} catch (Exception ex) {}
//	    	}
		}
	}

	private static void runSparkServer(String posString, String negString) {
		Spark.externalStaticFileLocation("src/main/resources/static");
		Spark.get("/", new GetHandler(posString, negString), new FreeMarkerEngine());
	}
	
	private static class GetHandler implements TemplateViewRoute {
		
		private String posString;
		private String negString;
		
		public GetHandler(String posString, String negString) {
			this.posString = posString;
			this.negString = negString;
		}
		@Override
		public ModelAndView handle(Request req, Response res) {
			Map<String, Object> variables = ImmutableMap.of("title", "Sentiment Heatmap", "positives", posString, "negatives", negString);
			return new ModelAndView(variables, "main.ftl");
		}
	}

}
