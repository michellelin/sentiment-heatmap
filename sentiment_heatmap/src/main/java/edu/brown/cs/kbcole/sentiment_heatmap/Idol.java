package edu.brown.cs.kbcole.sentiment_heatmap;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;

import twitter4j.JSONException;
import twitter4j.JSONObject;

public class Idol {

	public static double getScore(String text) throws IOException, JSONException {
		String apikey = "dbc36ad3-a064-41ae-a099-f911f8c99991";
		String url = "https://api.idolondemand.com/1/api/sync/analyzesentiment/v1?text=";
		
		URL obj = new URL(url + URLEncoder.encode(text, "UTF-8") + "&apikey=" + apikey);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		InputStream response = con.getInputStream();
		
		StringBuilder builder = new StringBuilder();
		int c;
		while((c = response.read()) != -1) {
			builder.append((char)c);
		}
		String responseString = builder.toString();
		
    	JSONObject jsonObject = new JSONObject(responseString);

    	double score = Double.parseDouble(jsonObject.getJSONObject("aggregate").getString("score"));

    	return score;
	}

}