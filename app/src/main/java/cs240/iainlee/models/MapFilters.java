package cs240.iainlee.models;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by iain on 4/11/17.
 */

public class MapFilters {
	
	private static MapFilters SINGLETON;

	private MapFilters() {
	}
	
	public static MapFilters get() {
		if (SINGLETON == null) {
			SINGLETON = new MapFilters();
		}
		return SINGLETON;
	}
	
	private boolean[] filterValues;
	
	public boolean[] getFilterValues() {
		return filterValues;
	}
	
	public void setFilterValues(boolean[] filterValues) {
		this.filterValues = filterValues;
	}
	
	public static void clear() {
		SINGLETON = null;
	}
}
