package cs240.iainlee.familymapclient;

import java.util.ArrayList;

import cs240.iainlee.models.Event;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.UserInfo;

/**
 * Created by iain on 4/12/17.
 */

public class EventFilterer {
	
	private static EventFilterer SINGLETON;
	
	private EventFilterer() {
	}
	
	public static EventFilterer get() {
		if (SINGLETON == null) {
			SINGLETON = new EventFilterer();
		}
		return SINGLETON;
	}
	
	public ArrayList<Event> filterByEventType(String eventType) {
		Event[] baseEvents = UserInfo.get().getEvents();
		Event event = null;
		ArrayList<Event> filtered = new ArrayList<>();
		
		for (int i = 0; i < baseEvents.length; i++) {
			if (baseEvents[i].getEventType().equals(eventType)) {
				filtered.add(baseEvents[i]);
			}
		}
		
		return filtered;
	}
	
	public ArrayList<Event> filterByGender(boolean isMale) {
		Event[] baseEvents = UserInfo.get().getEvents();
		Event event = null;
		Person person = null;
		ArrayList<Event> filtered = new ArrayList<>();
		
		if (isMale) {
			for (int i = 0; i < baseEvents.length; i++) {
				person = UserInfo.get().getPerson( baseEvents[i].getPersonId() );
				if (person.getGender().equals("m")) {
					filtered.add(baseEvents[i]);
				}
			}
		}else {
			for (int i = 0; i < baseEvents.length; i++) {
				person = UserInfo.get().getPerson( baseEvents[i].getPersonId() );
				if (person.getGender().equals("f")) {
					filtered.add(baseEvents[i]);
				}
			}
		}
		
		return filtered;
	}
	
	public ArrayList<Event> filterByFamilySide(boolean isFather) {
		Person user = UserInfo.get().getUser();
		ArrayList<Event> events = null;
		if (isFather) {
			Person father = UserInfo.get().getPerson(user.getFather());
			events = recurseFindAncestors( father );
		}else {
			Person mother = UserInfo.get().getPerson(user.getMother());
			events = recurseFindAncestors( mother );
		}
		return events;
	}
	
	private ArrayList<Event> recurseFindAncestors(Person person) {
		ArrayList<Event> events = new ArrayList<>();
		if (person == null) {
			return events;
		}
		events.addAll(UserInfo.get().getEventsById(person.getId()));
		events.addAll( recurseFindAncestors(UserInfo.get().getPerson(person.getFather())) );
		events.addAll( recurseFindAncestors(UserInfo.get().getPerson(person.getMother())) );
		return events;
	}
	
}
