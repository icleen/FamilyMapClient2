package cs240.iainlee.models;

/**
 * Created by iain on 4/4/17.
 */

public class UserInfo {
	
	private static UserInfo SINGLETON;
	
	Person[] mPersons;
	Event[] mEvents;
	
	Person mUser;
	
	private UserInfo() {
	}
	
	public static UserInfo get() {
		if (SINGLETON == null) {
			SINGLETON = new UserInfo();
		}
		return SINGLETON;
	}
	
	public Person[] getPersons() {
		return mPersons;
	}
	
	public void setPersons(Person[] persons) {
		mPersons = persons;
	}
	
	public Event[] getEvents() {
		return mEvents;
	}
	
	public void setEvents(Event[] events) {
		mEvents = events;
	}
	
	public Person getUser() {
		return mUser;
	}
	
	public void setUser(Person user) {
		mUser = user;
	}
	
}
