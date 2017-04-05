package cs240.iainlee.models;

public class Names {
	
	public String[] data;
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < data.length; i++) {
			s.append(data[i] + ", ");
		}
		return s.toString();
	}
	
	public int size() {
		return data.length;
	}
	
	public String get(int index) {
		return data[index];
	}
}
