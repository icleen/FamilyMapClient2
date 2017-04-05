package cs240.iainlee.servercommunicator;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class BaseClientCommunicator {
	public static BaseClientCommunicator SINGLETON;

	protected static String SERVER_HOST = "localhost";
	protected static String SERVER_PORT = "3740";
	protected static String URL_PREFIX = "http://";
	protected static final String HTTP_POST = "POST";
	protected static final String HTTP_GET = "GET";

	public static final String AUTHORIZATION_KEY = "Authorization";
	protected String authCode;
	

	protected Gson gson = new Gson();

	BaseClientCommunicator() {
	}

	/**
	 * sends an object to the server
	 * @param connection
	 * @param toBeSent
	 */
	protected void sendToServer(HttpURLConnection connection, Object toBeSent) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(connection.getOutputStream());
			gson.toJson(toBeSent, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(writer != null) {
				writer.close();
			}
		}
		return;
	}

	/**
	 * returns an object given by the server
	 * @param connection
	 * @param klass
	 * @return
	 */
	protected Object getResponse(HttpURLConnection connection, Class<?> klass) {
		Object result = null;

		try {
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				int length = connection.getContentLength();
				if(length == 0) {
//					0 indicates an empty response body
					System.out.println("There was nothing in the response body");
				}else if( length == -1) {
//					-1 indicates unknown amount of info
//					System.out.println("There was something there");
					InputStreamReader reader = new InputStreamReader(connection.getInputStream());
					result = gson.fromJson(reader, klass);
					reader.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.getInputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * gives you a connection to the server
	 * @param context
	 * @param action what you want to do
	 * @param authCode the authCode given when you logged in
	 * @param sendingToServer a bool asking if you are sending something
	 * @return a HttpURLConnection
	 */
	protected HttpURLConnection openConnection(String context,
											String action,
											String authCode,
											boolean sendingToServer)
	{
		HttpURLConnection result = null;
		try {
			URL url = new URL(URL_PREFIX + SERVER_HOST + ":" + SERVER_PORT + context);
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

	public static String getServerHost() {
		return SERVER_HOST;
	}

	public static void setServerHost(String serverHost) {
		SERVER_HOST = serverHost;
	}

	public static String getServerPort() {
		return SERVER_PORT;
	}

	public static void setServerPort(String serverPort) {
		SERVER_PORT = serverPort;
	}
}
