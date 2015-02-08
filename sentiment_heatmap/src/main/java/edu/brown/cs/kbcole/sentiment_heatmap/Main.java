package edu.brown.cs.kbcole.sentiment_heatmap;

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
import twitter4j.Query;
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
		// TODO Auto-generated method stub
		String oAuthConsumerKey = "zJUtvGJkQe4nKNsdI6nyytCZ7";
	    String oAuthConsumerSecret = "fbQtnsXUsgYdk2HiSA3KJpSHIwZ0YKLgsiblk9b8dH1fPhPe97";

	    String oAuthAccessToken = "2281706174-B4I05DRdLTXoDoHUOzuRgFDavfZeY07fgXuJ64e";
	    String oAuthAccessTokenSecret = "Pml9w8U1ysADSUeIZDuye4dVkT9DbRLYXtNlXVJGpZ0ht";
	    
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	      .setOAuthConsumerKey(oAuthConsumerKey)
	      .setOAuthConsumerSecret(oAuthConsumerSecret)
	      .setOAuthAccessToken(oAuthAccessToken)
	      .setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
	    TwitterFactory tf = new TwitterFactory(cb.build());
	    
	    
		Spark.setPort(4567);
		runSparkServer(tf.getInstance());
	}

	private static void runSparkServer(Twitter twitter) {
		Spark.externalStaticFileLocation("src/main/resources/static");
		System.out.println("test");
		Spark.get("/", new GetHandler(twitter), new FreeMarkerEngine());
	}
	
	private static class GetHandler implements TemplateViewRoute {
		private Twitter twitter;
		public GetHandler(Twitter twitter) {
			this.twitter = twitter;
		}
		@Override
		public ModelAndView handle(Request req, Response res) {
			List<Status> tweets = new ArrayList<>();
			try {
		    	Query query = new Query();
		    	query.setGeoCode(new GeoLocation(37.781157, -122.398720), 15, Query.Unit.valueOf("mi"));
		    	tweets = twitter.search(query).getTweets();
		    	for(Status tweet : tweets) {
		    		
		    	}
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			
			Map<String, Object> variables = ImmutableMap.of("title", "Sentiment Heatmap", "tweets", tweets.get(0).getText());
			return new ModelAndView(variables, "main.ftl");
		}
	}

}