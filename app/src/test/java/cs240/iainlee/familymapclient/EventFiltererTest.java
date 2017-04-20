package cs240.iainlee.familymapclient;

/**
 * Created by iain on 4/12/17.
 */

import org.junit.Test;

import java.util.ArrayList;

import cs240.iainlee.models.Event;
import cs240.iainlee.models.LoginInfo;
import cs240.iainlee.models.LoginResponse;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.UserInfo;
import cs240.iainlee.servercommunicator.ServerProxy;

import static org.junit.Assert.*;

public class EventFiltererTest {
	
	@Test
	public void testFilterByEventType() {
		Event[] events = null;
		Person[] persons = null;
		ArrayList<Event> results = null;
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setServerHost("192.168.0.107");
		loginInfo.setServerPort("3740");
		loginInfo.setUsername("a");
		loginInfo.setPassword("a");
		ServerProxy serverProxy = new ServerProxy();
		serverProxy.userLogin(loginInfo);
		events = serverProxy.getEvents();
		persons = serverProxy.getPeople();
		UserInfo.get().setEvents(events);
		UserInfo.get().setPersons(persons);
		
		results = EventFilterer.get().filterByEventType("birth");
		for (Event temp : results) {
			assertTrue(temp.getEventType().equals("birth"));
			assertFalse(temp.getEventType().equals("marriage"));
		}
		results = EventFilterer.get().filterByEventType("death");
		for (Event temp : results) {
			assertTrue(temp.getEventType().equals("death"));
			assertFalse(temp.getEventType().equals("birth"));
		}
	}
	
	@Test
	public void testFilterByGender() {
		Event[] events = null;
		Person[] persons = null;
		ArrayList<Event> results = null;
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setServerHost("192.168.0.107");
		loginInfo.setServerPort("3740");
		loginInfo.setUsername("a");
		loginInfo.setPassword("a");
		ServerProxy serverProxy = new ServerProxy();
		serverProxy.userLogin(loginInfo);
		events = serverProxy.getEvents();
		persons = serverProxy.getPeople();
		UserInfo.get().setEvents(events);
		UserInfo.get().setPersons(persons);
		
		results = EventFilterer.get().filterByGender(true);
		Person person = null;
		for (Event temp : results) {
			person = UserInfo.get().getPerson(temp.getPersonId());
			assertTrue(person.getGender().equals("m"));
			assertFalse(person.getGender().equals("f"));
		}
		results = EventFilterer.get().filterByGender(false);
		person = null;
		for (Event temp : results) {
			person = UserInfo.get().getPerson(temp.getPersonId());
			assertTrue(person.getGender().equals("f"));
			assertFalse(person.getGender().equals("m"));
		}
	}
	
	@Test
	public void testFilterByFamilySide() {
		Event[] events = null;
		Person[] persons = null;
		ArrayList<Event> results = null;
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setServerHost("192.168.0.107");
		loginInfo.setServerPort("3740");
		loginInfo.setUsername("a");
		loginInfo.setPassword("a");
		ServerProxy serverProxy = new ServerProxy();
		LoginResponse response = serverProxy.userLogin(loginInfo);
		events = serverProxy.getEvents();
		persons = serverProxy.getPeople();
		UserInfo.get().setEvents(events);
		UserInfo.get().setPersons(persons);
		UserInfo.get().setUser(UserInfo.get().getPerson(response.getPersonId()));
		
		results = EventFilterer.get().filterByFamilySide(true);
		assertTrue(results.size() == events.length/2);
	}
	
	@Test
	public void testAddAll() {
		ArrayList<Person> persons = new ArrayList<>();
		persons.addAll(new ArrayList<Person>());
		System.out.println(persons.size());
		for (Person person : persons) {
			System.out.println(person);
		}
	}
	
	@Test
	public void testGetFirstEvent() {
		
		Event[] events = null;
		Person[] persons = null;
		ArrayList<Event> results = null;
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setServerHost("192.168.0.107");
		loginInfo.setServerPort("3740");
		loginInfo.setUsername("a");
		loginInfo.setPassword("a");
		ServerProxy serverProxy = new ServerProxy();
		LoginResponse response = serverProxy.userLogin(loginInfo);
		events = serverProxy.getEvents();
		persons = serverProxy.getPeople();
		UserInfo.get().setEvents(events);
		UserInfo.get().setPersons(persons);
		Person user = UserInfo.get().getPerson(response.getPersonId());
		Event oldest = UserInfo.get().getOldestEvent(user.getFather());
		assertTrue(oldest.getEventType().equals("birth"));
		
	}
	
}
