package cs240.iainlee.models;

public class ObjectResponse {
	
	public Object[] data;
	
	public ObjectResponse(Object[] data) {
		if(data == null || data.length == 0) {
			this.data = null;
		}
		else if(data[0].getClass() == Person.class) {
			Person[] temp = new Person[data.length];
			for(int i = 0; i < data.length; i++) {
				temp[i] = (Person) data[i];
			}
			this.data = temp;
		}
		else if(data[0].getClass() == Event.class) {
			Event[] temp = new Event[data.length];
			for(int i = 0; i < data.length; i++) {
				temp[i] = (Event) data[i];
			}
			this.data = temp;
		}
	}

}
