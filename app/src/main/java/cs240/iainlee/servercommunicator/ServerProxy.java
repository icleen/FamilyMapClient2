package cs240.iainlee.servercommunicator;

import android.util.Log;

import cs240.iainlee.models.LoginInfo;
import cs240.iainlee.models.Event;
import cs240.iainlee.models.Events;
import cs240.iainlee.models.LoginRequest;
import cs240.iainlee.models.LoginResponse;
import cs240.iainlee.models.Message;
import cs240.iainlee.models.People;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.User;

public class ServerProxy {
	
	private final String TAG = "ServerProxy";
	
	public void addServerInfo(LoginInfo info) {
		ClientCommunicator.SINGLETON.setServerHost(info.getServerHost());
		ClientCommunicator.SINGLETON.setServerPort(info.getServerPort());
	}
	
	/**
	 * creates a new user and generates 4 generations of ancestors for the user
	 * @param info an object of LoginInfo
	 */
	public LoginResponse registerUser(LoginInfo info) {
		ClientCommunicator.SINGLETON.setServerHost(info.getServerHost());
		ClientCommunicator.SINGLETON.setServerPort(info.getServerPort());
		User user = new User(info.getUsername(), info.getPassword(), info.getEmail(), info.getFirstName(), info.getLastName(), info.getGender(), null, null);
		LoginResponse response = ClientCommunicator.SINGLETON.register(user);
		if(response.getErrorMessage() != null) {
			Log.d(TAG, response.getErrorMessage());
		}
//		Log.d(TAG, "Got Info");
		return response;
//		return null;
	}
	
	/**
	 * creates a new user and generates 4 generations of ancestors for the user
	 * @param user an object of User
	 */
	public LoginResponse registerUser(User user) {
		LoginResponse response = ClientCommunicator.SINGLETON.register(user);
		if(response.getErrorMessage() != null) {
//			System.out.println(response.getErrorMessage());
		}
		return response;
	}
	
	/**
	 * logs a user in and makes the server's user object equal to the user specified
	 * @param info an object of LoginInfo
	 */
	public LoginResponse userLogin(LoginInfo info) {
		ClientCommunicator.setServerHost(info.getServerHost());
		ClientCommunicator.setServerPort(info.getServerPort());
		LoginRequest lr = new LoginRequest();
		lr.setUserName(info.getUsername());
		lr.setPassword(info.getPassword());
		LoginResponse response = ClientCommunicator.SINGLETON.login(lr);
		if(response.getErrorMessage() != null) {
			Log.d(TAG, response.getErrorMessage());
		}
//		Log.d(TAG, "Got Info");
		return response;
//		return null;
	}
	
	/**
	 * logs a user in and makes the server's user object equal to the user specified
	 * @param user an object of User
	 */
	public LoginResponse userLogin(User user) {
		LoginRequest lr = new LoginRequest();
		lr.setUserName(user.getusername());
		lr.setPassword(user.getPassword());
		LoginResponse response = ClientCommunicator.SINGLETON.login(lr);
		if(response.getErrorMessage() != null) {
//			System.out.println(response.getErrorMessage());
		}
		return response;
	}

	/**
	 * deletes all data from the database
	 */
	public String clear() {
		Message result = ClientCommunicator.SINGLETON.clear();
//		System.out.println(result);
		return result.toString();
	}

	/**
	 * generates the number of generations specified in the database
	 * @param user an object of User
	 * @param generations the number of generations you want to add to this user
	 */
	public String fill(User user, int generations) {
		Message result = ClientCommunicator.SINGLETON.fill(user.getusername(), generations);
//		System.out.println(result);
		return result.toString();
	}

	/**
	 * loads the specified data into the database, deleting everything that was there before
	 * @param users an array of User objects
	 * @param people an array of Person objects
	 * @param events an array of Event objects
	 */
	public String load(User[] users, Person[] people, Event[] events) {
		Message result = ClientCommunicator.SINGLETON.load(users, people, events);
//		System.out.println(result);
		return result.toString();
	}

	/**
	 * get all of the people related to the user
	 * @return an array of Person objects, for each of the Persons connected to the user
	 */
	public Person[] getPeople() {
		Object result = ClientCommunicator.SINGLETON.person(null);
		assert(result.getClass() == People.class);
		People temp = (People) result;
		if(temp.message != null) {
//			System.out.println(temp);
			return null;
		}
		Person[] people = temp.data;
		return people;
	}

	/**
	 * get the person a specific person based on the id
	 * @param id a String; the id of the person you want access to
	 * @return a person object 
	 */
	public Person getPerson(String id) {
		Object result = ClientCommunicator.SINGLETON.person(id);
		assert(result.getClass() == Person.class);
		Person person = (Person) result;
		if(person.getMessage() != null) {
//			System.out.println(person.getMessage());
			return null;
		}
		return person;
	}

	/**
	 * gives you all of the events for all the family members of the current user 
	 * @return an array of Event objects
	 */
	public Event[] getEvents() {
		Object result = ClientCommunicator.SINGLETON.event(null);
		assert(result.getClass() == Events.class);
		Events temp = (Events) result;
		if(temp.message != null) {
//			System.out.println(temp.message);
			return null;
		}
		Event[] events = temp.data;
		return events;
	}

	/**
	 * get an event specified by the id
	 * @param id a String for a specific event
	 * @return an event object
	 */
	public Event getEvent(String id) {
		Object result = ClientCommunicator.SINGLETON.event(id);
		assert(result.getClass() == Event.class);
		Event event = (Event) result;
		if(event.getMessage() != null) {
//			System.out.println(event.getMessage());
			return null;
		}
		return event;
	}

}
