package cs240.iainlee.servercommunicator;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cs240.iainlee.models.Event;
import cs240.iainlee.models.Events;
import cs240.iainlee.models.LoadRequest;
import cs240.iainlee.models.LoginRequest;
import cs240.iainlee.models.LoginResponse;
import cs240.iainlee.models.Message;
import cs240.iainlee.models.People;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.User;

public class ClientCommunicator extends BaseClientCommunicator {
	
	private final String TAG = "ClientCommunicator";

	public static final String REGISTER_DESIGNATOR = "/user/register";
	public static final String LOGIN_DESIGNATOR = "/user/login";
	public static final String CLEAR_DESIGNATOR = "/clear";
	public static final String FILL_DESIGNATOR = "/fill";
	public static final String LOAD_DESIGNATOR = "/load";
	public static final String PERSON_DESIGNATOR = "/person";
	public static final String EVENT_DESIGNATOR = "/event";
	public static final String DEFAULT_DESIGNATOR = "/";
	
	public static ClientCommunicator SINGLETON = new ClientCommunicator();
	
	public static final String USERNAME_KEY = "username";
	public static final String GENERATIONS_KEY = "gens";
	public static final String PERSONS_KEY = "person";
	public static final String EVENTS_KEY = "event";
	
	private ClientCommunicator() {
	}
	
	public LoginResponse register(User toBeSent) {
		Object response = null;
		HttpURLConnection connection = openConnection(REGISTER_DESIGNATOR, HTTP_POST, authCode, true);
		Log.d(TAG, "Connection made");
		if(connection == null) {
			return null;
		}
		sendToServer(connection, toBeSent);
		response = getResponse(connection, LoginResponse.class);
		LoginResponse temp = (LoginResponse) response;
		Log.d(TAG, temp.toString());
		if(temp.getAuthCode() != null) {
			this.authCode = temp.getAuthCode();
		}
		return temp;
	}
	
	public LoginResponse login(LoginRequest toBeSent) {
		Object response = null;
		HttpURLConnection connection = openConnection(LOGIN_DESIGNATOR, HTTP_POST, authCode, true);
		if(connection == null) {
			return null;
		}
		sendToServer(connection, toBeSent);
		response = getResponse(connection, LoginResponse.class);
		LoginResponse temp = (LoginResponse) response;
		if(temp.getAuthCode() != null) {
			this.authCode = temp.getAuthCode();
		}
		return temp;
	}
	
	public Message clear() {
		Object response = null;
		HttpURLConnection connection = openConnection(CLEAR_DESIGNATOR, HTTP_POST, authCode, false);
		if(connection == null) {
			return null;
		}
		response = getResponse(connection, Message.class);
		return (Message) response;
	}
	
	public Message fill(String userName, int generations) {
		Object response = null;
		String header = null;
		if(generations < 0) {
			header = "/" + userName;
		}else {
			header = "/" + userName + "/" + generations;
		}
		HttpURLConnection connection = openConnection(FILL_DESIGNATOR, HTTP_POST, null, false, header);
		if(connection == null) {
			return null;
		}
		response = getResponse(connection, Message.class);
		return (Message) response;
	}
	
	public Message load(User[] users, Person[] people, Event[] events) {
		Object response = null;
		LoadRequest request = new LoadRequest(users, people, events);
		HttpURLConnection connection = openConnection(LOAD_DESIGNATOR, HTTP_POST, authCode, true);
		if(connection == null) {
			return null;
		}
		sendToServer(connection, request);
		response = getResponse(connection, Message.class);
		return (Message) response;
	}
	
	public Object event(String eventId) {
		Object response = null;
		String header = null;
		if(eventId != null) {
			header = "/" + eventId;
		}
		HttpURLConnection connection = openConnection(EVENT_DESIGNATOR, HTTP_POST, authCode, false, header);
		if(connection == null) {
			return null;
		}
		if(eventId != null) {
			response = getResponse(connection, Event.class);
		}else {
			response = getResponse(connection, Events.class);
//			System.out.println(response);
		}
		
		return response;
	}
	
	public Object person(String personId) {
		Object response = null;
		String header = null;
		if(personId != null) {
			header = "/" + personId;
		}
		HttpURLConnection connection = openConnection(PERSON_DESIGNATOR, HTTP_POST, authCode, false, header);
		if(connection == null) {
			return null;
		}
		if(personId != null) {
			response = getResponse(connection, Person.class);
		}else {
			response = getResponse(connection, People.class);
		}
		return response;
	}
	
	
	
	protected HttpURLConnection openConnection(String context,
												String action,
												String authCode,
												boolean sendingToServer,
												String header)
	{
		if(header == null) {
			return openConnection(context, action, authCode, sendingToServer);
		}
		HttpURLConnection result = null;
		try {
			URL url = new URL(URL_PREFIX + context + header);
			result = (HttpURLConnection) url.openConnection();
			result.setRequestMethod(action);
			result.setDoOutput(sendingToServer);
			result.setRequestProperty(AUTHORIZATION_KEY, authCode);
			
			result.connect();

		} catch (MalformedURLException e) {
			System.err.println("The url did not work! " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.err.println("Could not connect to the server! " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return result;
	}
	
}


//public Object hello(Object toBeSent) {
//	Object response = null;
//	HttpURLConnection connection = openConnection(ServerCommunicator.HELLO_DESIGNATOR, HTTP_POST, authCode, true);
//	if(connection == null) {
//		return null;
//	}
//	sendToServer(connection, toBeSent);
//	response = getResponse(connection, toBeSent.getClass());
//	return response;
//}
//
//public Object primitive(Object toBeSent) {
//	Object response = null;
//	HttpURLConnection connection = openConnection(ServerCommunicator.PRIMITIVE_DESIGNATOR, HTTP_POST, authCode, true);
//	if(connection == null) {
//		return null;
//	}
//	sendToServer(connection, toBeSent);
//	response = getResponse(connection, toBeSent.getClass());
//	return response;
//}
