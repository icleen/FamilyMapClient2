package cs240.iainlee.familymapclient;

import org.junit.Test;

import java.util.ArrayList;

import cs240.iainlee.models.Event;
import cs240.iainlee.models.LoginInfo;
import cs240.iainlee.models.LoginResponse;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.UserInfo;
import cs240.iainlee.servercommunicator.ServerProxy;

import static org.junit.Assert.*;

/**
 * Created by iain on 4/19/17.
 */

public class ServerProxyTest {
	
	@Test
	public void testGetPersons() {
		System.out.println("Starting");
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
		Person user = UserInfo.get().getPerson(response.getPersonId());
		UserInfo.get().setUser(user);
		
		String userId = user.getFirstName();
		String descendant = null;
		for (int i = 0; i < persons.length; i++) {
			descendant = persons[i].getDescendant();
			assertTrue(userId.equals(descendant));
		}
		System.out.println("Finished");
	}
	
	@Test
	public void testGetEvents() {
		System.out.println("Starting");
		Event[] events = null;
		Person[] persons = null;
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
		UserInfo.get().setUser(user);
		
		String userId = user.getFirstName();
		String descendant = null;
		for (int i = 0; i < events.length; i++) {
			descendant = events[i].getDescendant();
			assertTrue(userId.equals(descendant));
		}
		System.out.println("Finished");
	}
	
}
