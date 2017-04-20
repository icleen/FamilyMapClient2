package cs240.iainlee.support;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cs240.iainlee.familymapclient.EventFilterer;
import cs240.iainlee.models.Event;
import cs240.iainlee.models.MapFilters;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.UserInfo;

/**
 * Created by iain on 4/19/17.
 */

public class Filterer {
	
	private static final String TAG = "Filterer";
	
	public static Event[] getEvents(boolean[] filterValues, String[] eventTypes) {
		filterValues = MapFilters.get().getFilterValues();
		if (filterValues == null) {
			return UserInfo.get().getEvents();
		}
		assert (filterValues.length == eventTypes.length + 4);
		Log.d(TAG, "filtering events");
		ArrayList<Event> results = new ArrayList<>();
		int i;
		for (i = 0; i < eventTypes.length; i++) {
			if (filterValues[i]) {
				results.addAll(EventFilterer.get().filterByEventType(eventTypes[i]));
			}
		}
		if (filterValues[i++]) {
			results.addAll(EventFilterer.get().filterByFamilySide(true));
		}
		if (filterValues[i++]) {
			results.addAll(EventFilterer.get().filterByFamilySide(false));
		}
		if (filterValues[i++]) {
			results.addAll(EventFilterer.get().filterByGender(true));
		}
		if (filterValues[i++]) {
			results.addAll(EventFilterer.get().filterByGender(false));
		}
		Event[] events = new Event[results.size()];
		for (int j = 0; j < results.size(); j++) {
			events[j] = results.get(j);
		}
		return events;
	}
	
}
