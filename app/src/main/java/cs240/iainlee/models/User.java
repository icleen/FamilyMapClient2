package cs240.iainlee.models;

public class User {
	
	private String userName;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	
	private String userID;
	private String personID;
	
	public User(String id, String personId) {
		this.userID = id;
		this.personID = personId;
	}
	
	public User(String username, String password, String email, String id, String personId) {
		this.userID = id;
		this.personID = personId;
		this.userName = username;
		this.password = password;
		this.email = email;
	}
	
	public User(String username, String password, String email, String firstname, String lastname, String gender, String id, String personId) {
		this.userID = id;
		this.personID = personId;
		this.userName = username;
		this.password = password;
		this.email = email;
		this.firstName = firstname;
		this.lastName = lastname;
		this.gender = gender;
	}
	
	public String getId() {
		return userID;
	}

	public String getPersonId() {
		return personID;
	}
	
	public void setPersonId(String personId) {
		this.personID = personId;
	}

	public String getfirstname() {
		return firstName;
	}

	public void setfirstname(String firstname) {
		this.firstName = firstname;
	}

	public String getlastname() {
		return lastName;
	}

	public void setlastname(String lastname) {
		this.lastName = lastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCorrectPassword(String password) {
		if(this.password.equals(password)) {
			return true;
		}
		return false;
	}
	
	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getusername() {
		return userName;
	}

	public void setusername(String username) {
		this.userName = username;
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("{\n");
		output.append("\tuserId:\"" + this.userID + "\",\n");
		output.append("\tuserName: " + this.userName + "\",\n");
		output.append("\tpassword:\"" + this.password + "\",\n");
		output.append("\temail:\"" + this.email + "\",\n");
		output.append("\tfirstname:\"" + this.firstName + "\",\n");
		output.append("\tlastName:\"" + this.lastName + "\",\n");
		output.append("\tgender:\"" + this.gender + "\",\n");
		output.append("\tpersonID:\"" + this.personID + "\",\n");
		output.append("}");
		return output.toString();
	}
	
	public boolean equals(Object o) {
		
		if(o.getClass() != this.getClass()) {
			return false;
		}
		User temp = (User) o;
		if(!this.userName.equals(temp.userName) || !this.password.equals(temp.password) 
				|| !this.email.equals(temp.email) || !this.userID.equals(temp.userID) 
				|| !this.personID.equals(temp.personID) || !this.firstName.equals(temp.firstName)
				|| !this.lastName.equals(temp.lastName) || !this.gender.equals(temp.gender)) {
			return false;
		}
		
		return true;
	}
	
	public boolean isValid() {
		if(this.userName == null || this.password == null || this.email == null 
				|| this.firstName == null || this.lastName == null || this.gender == null) {
			return false;
		}
		return true;
	}

	public void setId(String temp) {
		this.userID = temp;
	}

}
