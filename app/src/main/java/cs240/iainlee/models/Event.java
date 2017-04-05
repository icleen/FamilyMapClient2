package cs240.iainlee.models;

public class Event extends Object {
	
	private String descendant;
	private final String eventID;
	private final String personID;
	
	private String latitude;
	private String longitude;
	private String country;
	private String city;
	private String eventType;
	private String year;
	
	private String message;
	
	public Event(String eventId, String descendant, String personId) {
		this.eventID = eventId;
//		this.userId = userId;
		this.descendant = descendant;
		this.personID = personId;
	}
	
	public Event(String eventId, String descendant, String personId, String latitude, String longitude, 
			String country, String city, String eventType, String year) {
		this.eventID = eventId;
//		this.userId = userId;
		this.descendant = descendant;
		this.personID = personId;
		
		this.latitude = latitude;
		this.longitude = longitude;
		this.country = country;
		this.city = city;
		this.eventType = eventType;
		this.year = year;
	}
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPersonId() {
		return personID;
	}
	public String getEventId() {
		return eventID;
	}
	
	public void setDescendant(String descendant) {
		this.descendant = descendant;
	}
	
	public String getDescendant() {
		return descendant;
	}

//	public String getUserId() {
//		return userId;
//	}
	
	public String toString() {
		if(message != null) {
			return message;
		}
		StringBuilder output = new StringBuilder();
		output.append("{\n");
		output.append("descendant: " + this.descendant + "\",\n");
		output.append("eventID:\"" + this.eventID + "\",\n");
		output.append("personID:\"" + this.personID + "\",\n");
		output.append("lattitude:\"" + this.latitude + "\",\n");
		output.append("longitude:\"" + this.longitude + "\",\n");
		output.append("country:\"" + this.country + "\",\n");
		output.append("city:\"" + this.city + "\",\n");
		output.append("eventeventType:\"" + this.eventType + "\",\n");
		output.append("year:\"" + this.year + "\"\n");
		output.append("}\n");
		return output.toString();
	}
	
	public boolean equals(Event other) {
		if( this.descendant.equals(other.getDescendant()) && this.eventID.equals(other.eventID) && this.personID.equals(other.personID)
				&& this.eventType.equals(other.eventType) && this.latitude.equals(other.latitude) && this.longitude.equals(other.longitude) 
				&& this.country.equals(other.country) && this.city.equals(other.city) && this.year.equals(other.year) ) {
			return true;
		}
		return false;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isValid() {
		if(this.descendant == null || this.personID == null || this.eventID == null) {
			return false;
		}
		return true;
	}
	
}
