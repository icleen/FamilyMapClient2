package cs240.iainlee.models;

public class Locations {
	public Location[] data;
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("This is the locations:\n");
		for(int i = 0; i < data.length; i++) {
			s.append(data[i] + ", ");
		}
		return s.toString();
	}
	
	public int size() {
		return data.length;
	}
	
	public Location get(int index) {
		return data[index];
	}
}
