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
		for (int i = 0; i < mPersons.length; i++) {
			if (mPersons[i].getId().equals(id)) {
				return mPersons[i];
			}
		}
		return null;
	}
	
	public Event getEvent(String id) {
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
	
	public HashMap<Person, String> getRelatives(Person person) {
		Person father = getPerson(person.getFather());
		Person mother = getPerson(person.getMother());
		Person spouse = getPerson(person.getSpouse());
		HashMap<Person, String> relatives = new HashMap<>();
		relatives.put(father, "father");
		relatives.put(mother, "mother");
		relatives.put(spouse, "spouse");
		String id = person.getId();
		for (int i = 0; i < mPersons.length; i++) {
			if (mPersons[i].getFather().equals(id) || mPersons[i].getMother().equals(id)) {
				relatives.put(mPersons[i], "child");
			}
		}
		return relatives;
	}
	
}
