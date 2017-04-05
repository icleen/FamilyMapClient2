package cs240.iainlee.models;

/**
 * Created by iain on 3/18/17.
 */

public class LoginInfo {

    private String mUsername;
    private String mPassword;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mGender;

    private String mServerHost;
    private String mServerPort;
	
	public boolean isLoginReady() {
		if(mUsername != null && mPassword != null && mServerHost != null && mServerPort != null) {
			return true;
		}
		return false;
	}

	public boolean isRegisterReady() {
		if(mUsername != null && mPassword != null && mServerHost != null && mServerPort != null && mFirstName != null && mLastName != null && mEmail != null && mGender != null) {
			return true;
		}
		return false;
	}

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

	public String getGender() {
		return mGender;
	}

	public void setGender(String gender) {
		mGender = gender;
	}

	public String getServerHost() {
        return mServerHost;
    }

    public void setServerHost(String serverHost) {
        mServerHost = serverHost;
    }

    public String getServerPort() {
        return mServerPort;
    }

    public void setServerPort(String serverPort) {
        mServerPort = serverPort;
    }

	@Override
	public String toString() {
		return "LoginInfo{" +
				"mUsername='" + mUsername + '\'' +
				", mPassword='" + mPassword + '\'' +
				", mFirstName='" + mFirstName + '\'' +
				", mLastName='" + mLastName + '\'' +
				", mEmail='" + mEmail + '\'' +
				", mGender='" + mGender + '\'' +
				", mServerHost='" + mServerHost + '\'' +
				", mServerPort='" + mServerPort + '\'' +
				'}';
	}
}
