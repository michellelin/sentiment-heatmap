public class ScoredLocation {
	private double score;
	private GeoLocation loc;

	public ScoredLocation(double score, GeoLocation loc) {
		this.score = score;
		this.loc = loc;
	}

	public ScoredLocation(double score, double lat, double lng) {
		this.score = score;
		this.loc = new GeoLocation (lat, lng);
	}

	public double getScore() {
		return this.score;
	}

	public GeoLocation getLoc() {
		return this.loc;
	}

	public double getLat() {
		return this.loc.getLat();
	}

	public double getLng() {
		return this.loc.getLng();
	}
}