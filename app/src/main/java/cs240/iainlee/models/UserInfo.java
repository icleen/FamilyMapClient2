package cs240.iainlee.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by iain on 4/4/17.
 */

public class UserInfo {
	
	private static final String TAG = "UserInfo";
	
	private static UserInfo SINGLETON;
	
	Person[] mPersons;
	Event[] mEvents;
	
	Person mUser;
	
	LoginInfo mLoginInfo;
	
	private UserInfo() {
	}
	
	public static UserInfo get() {
		if (SINGLETON == null) {
			SINGLETON = new UserInfo();
		}
		return SINGLETON;
	}
	
	public void setLoginInfo(LoginInfo info) {
		mLoginInfo = info;
	}
	
	public LoginInfo getLoginInfo() {
		return mLoginInfo;
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
	
	public Person getPerson(String id) {
		if (id == null || id.isEmpty() || mPersons == null) {
			return null;
		}
		for (int i = 0; i < mPersons.length; i++) {
			if (mPersons[i].getId().equals(id)) {
				return mPersons[i];
			}
		}
		return null;
	}
	
	public Event getEvent(String id) {
		if (id == null || id.isEmpty() || mEvents == null) {
			return null;
		}
		for (int i = 0; i < mEvents.length; i++) {
			if (mEvents[i].getEventId().equals(id)) {
				return mEvents[i];
			}
		}
		return null;
	}
	
	public List<Event> getEventsById(String personId) {
		List<Event> events = new ArrayList<Event>();
		for (int i = 0; i < mEvents.length; i++) {
			if (mEvents[i].getPersonId().equals(personId)) {
				events.add(mEvents[i]);
			}
		}
		return events;
	}
	
	public List<Person> getChildren(Person person) {
		if (person == null || !person.isValid()) {
			return null;
		}
		ArrayList<Person> children = new ArrayList<>();
		String id = person.getId();
		if (person.getGender().equals("m")) {
			for (int i = 0; i < mPersons.length; i++) {
				if (mPersons[i].getFather() != null && mPersons[i].getFather().equals(id)) {
					children.add(mPersons[i]);
				}
			}
		}
		else {
			for (int i = 0; i < mPersons.length; i++) {
				if (mPersons[i].getMother() != null && mPersons[i].getMother().equals(id)) {
					children.add(mPersons[i]);
				}
			}
		}
		return children;
	}
	
	public Event getOldestEvent(String personId) {
		List<Event> events = getEventsById(personId);
		Event oldest = null;
		String year = null;
		int number = 0;
		int previous = 2020;
		for (Event e : events) {
			if (e.getYear() != null) {
				year = e.getYear();
				number = Integer.parseInt(year);
				if (number < previous) {
					previous = number;
					oldest = e;
				}
			}
		}
		return oldest;
	}
	
	public String[] getEventTypes() {
		if (mEvents == null) {
			Log.e(TAG, "events are null");
		}
		ArrayList<String> types = new ArrayList<>();
		ArrayList<Event> events = new ArrayList<>();
		for (Event event : mEvents) {
			if ( !types.contains(event.getEventType().toLowerCase()) ) {
				types.add(event.getEventType().toLowerCase());
				events.add(event);
			}
		}
		String[] results = new String[types.size()];
		for (int i = 0; i < results.length; i++) {
			results[i] = events.get(i).getEventType();
		}
		return results;
	}
	
	public static void clear() {
		SINGLETON = null;
	}
	
}// *************************** THE END *******************************
