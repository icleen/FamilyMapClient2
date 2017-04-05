package cs240.iainlee.models;

public class AuthToken {
	
	private String authCode;
	private String userName;
	private String password;
	private String userId;
	
	public AuthToken() {
	}
	
	public AuthToken(String username, String password, String authCode, String userId) {
		this.userName = username;
		this.password = password;
		this.authCode = authCode;
		this.userId = userId;
	}
	
	/**
	 * @return a String with the username
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * set the username
	 * @param username a String witht the username
	 */
	public void setUserName(String username) {
		this.userName = username;
	}
	
	/**
	 * @return a String with the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * set the password
	 * @param password a String with the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 
	 * @return a String with the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}
	/**
	 * set the authCode
	 * @param authCode a String with the authCode
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public boolean equals(AuthToken a) {
		if(this.authCode.equals(a.getAuthCode()) && this.userName.equals(a.getUserName()) && this.password.equals(a.getPassword())) {
			return true;
		}
		return false;
	}
	
}
