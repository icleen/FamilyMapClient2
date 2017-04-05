package cs240.iainlee.models;

public class LoginRequest {
	
	private String username;
	private String password;
	
	public LoginRequest() {
	}
	
	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString() {
		return ("username: " + username + ", password: " + password);
	}
	
	public boolean isValid() {
		if(this.username == null || this.password == null || this.username.equals("") || this.password.equals("")) {
			return false;
		}
		return true;
	}

}
