package cs240.iainlee.models;

public class Message {
	
	private String message;

	public Message(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("{\n");
		output.append("\t\"message\":\"" + message + "\"\n");
		output.append("}");
		return output.toString();
	}
	
	public boolean equals(Object o) {
		if(o.getClass() != this.getClass()) {
			return false;
		}
		Message m = (Message) o;
		if(!this.message.equals(m.message)) {
			return false;
		}
		return true;
	}

}
