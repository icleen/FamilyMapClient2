package cs240.iainlee.models;

public class LoginResponse {
	
	private String authCode;	// Non-empty auth token string
	private String username;	// User name passed in with request
	private String personId;	// Non-empty string containing the Person ID of the
	private String message;// error for if the operation failed
	
	public LoginResponse() {
		
	}
	
	public LoginResponse(String authCode, String username, String personId) {
		this.authCode = authCode;
		this.username = username;
		this.personId = personId;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getErrorMessage() {
		return message;
	}

	public void setErrorMessage(String message) {
		this.message = message;
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("{\n");
		if(this.message != null) {
			output.append("message:\"" + message + "\"\n");
		}else {
			output.append("authToken:\"" + authCode + "\",\n");
			output.append("username:\"" + username + "\",\n");
			output.append("persondId:\"" + personId + "\"\n");
		}
		output.append("}");
		return output.toString();
	}
	
	public boolean equals(Object o) {
		if(o.getClass() != this.getClass()) {
			return false;
		}
		LoginResponse temp = (LoginResponse) o;
		if(this.message != null || temp.message != null) {
			if(this.message != null && temp.message != null) {
				if( !this.message.equals(temp.message) ) {
					return false;
				}else {
					return true;
				}
			}else {
				return false;
			}
		}
		if(this.authCode.equals(temp.authCode) && this.personId.equals(temp.personId) 
				&& this.username.equals(temp.username)) 
		{
			return true;
		}
		return false;
	}

}
