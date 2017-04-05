package cs240.iainlee.models;

public class Location {
	public String latitude;
	public String longitude;
	public String city;
	public String country;
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("latitude:" + latitude + "\n");
		s.append("longitude:" + longitude + "\n");
		s.append("city:" + city + "\n");
		s.append("country:" + country + "\n");
		return s.toString();
	}
}