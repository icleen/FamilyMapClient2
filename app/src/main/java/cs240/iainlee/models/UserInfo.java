package cs240.iainlee.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	public Person getPerson(String id) {
		if (id == null || id.isEmpty()) {
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
		if (id == null || id.isEmpty()) {
			return null;
		}
		for (int i = 0; i < mEvents.length; i++) {
			if (mEvents[i].getPersonId().equals(id)) {
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
	
}// *************************** THE END *******************************
