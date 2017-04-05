package cs240.iainlee.models;

public class Events {
	
	public Event[] data;
	public String message;
	
	public Events(Object[] data) {
		if(data == null || data.length == 0) {
			return;
		}
		if(data[0].getClass() == Event.class) {
			this.data = new Event[data.length];
			for(int i = 0; i < data.length; i++) {
				this.data[i] = (Event) data[i];
			}
		}
	}

}
