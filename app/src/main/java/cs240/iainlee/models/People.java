package cs240.iainlee.models;

public class People {
	
	public Person[] data;
	
	public String message;
	
	public People(Object[] data) {
		if(data == null || data.length == 0) {
			return;
		}
		if(data[0].getClass() == Person.class) {
			this.data = new Person[data.length];
			for(int i = 0; i < data.length; i++) {
				this.data[i] = (Person) data[i];
			}
		}
	}

}
