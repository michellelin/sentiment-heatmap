import java.net.*;

public class Idol {
	private String apikey = "dbc36ad3-a064-41ae-a099-f911f8c99991";
	private String url = "https://api.idolondemand.com/1/api/sync/analyzesentiment/v1?text=";

	public double getScore(String text) {
		URL obj = new URL(url + URLEncoder.encode(text, "UTF-8") + "&apikey=" + apikey);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		String responseString = con.getResponseMessage();
	
		JSONObject obj = new JSONObject();
    	JSONObject jsonObject = obj.fromObject(responseString);

    	double score = Double.parseDouble(jsonObject.getJSONObject("aggregate").getJSONString("score"));

    	return score;
	}

}