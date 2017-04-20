package cs240.iainlee.support;

import android.util.Log;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Polyline;
import com.amazon.geo.mapsv2.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import cs240.iainlee.models.Event;
import cs240.iainlee.models.MapSettings;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.UserInfo;

/**
 * Created by iain on 4/19/17.
 */

public class LineDrawer {
	
	private static final String TAG = "LineDrawer";
	private static final float WIDTH_SUBTRACTOR = 2;
	
	public LineDrawer() {
	}
	
	private List<Polyline> mPolylines;
	
	public List<Polyline> setLines(AmazonMap amazonMap, Event[] events, Event currentEvent, EventColors eventColors) {
		MapSettings settings = MapSettings.get();
		mPolylines = new ArrayList<Polyline>();
		Log.d(TAG, "mapType: " + settings.getMapType());
		if (currentEvent == null) {
			Log.d(TAG, "current event is null so can't make lines");
			return mPolylines;
		}
		
		Log.d(TAG, "spouseLines: " + settings.isSpouseLines() + " " + settings.getSpouseColor());
		if (settings.isSpouseLines()) { // make spouse lines
			PolylineOptions p = setSpouseLines(currentEvent, eventColors);
			mPolylines.add(amazonMap.addPolyline(p));
		}
		
		try {
			Log.d(TAG, "lifeLines: " + settings.isLifeLines() + " " + settings.getLifeColor());
			if (settings.isLifeLines()) { // make life lines
				PolylineOptions options = setLifeLines(events, currentEvent, eventColors);
				mPolylines.add(amazonMap.addPolyline(options));
			}
		}catch (NullPointerException exception) {
			Log.e(TAG, exception.getMessage());
		}
		
		Log.d(TAG, "familyLines: " + settings.isFamilyLines() + " " + settings.getFamilyColor());
		if (settings.isFamilyLines()) { // make Family Lines
			setFamilyLines(amazonMap, eventColors.getColor(MapSettings.get().getFamilyColor()), currentEvent);
		}
		return mPolylines;
	}
	
	private PolylineOptions setSpouseLines(Event mCurrentEvent, EventColors mEventColors) {
		Log.d(TAG, "making spouse lines");
		Person currentPerson = UserInfo.get().getPerson(mCurrentEvent.getPersonId());
		Event temp = UserInfo.get().getOldestEvent(currentPerson.getSpouse());
		Double lat = Double.parseDouble(temp.getLatitude());
		Double lon = Double.parseDouble(temp.getLongitude());
		LatLng point = new LatLng(lat, lon);
		lat = Double.parseDouble(mCurrentEvent.getLatitude());
		lon = Double.parseDouble(mCurrentEvent.getLongitude());
		LatLng origin = new LatLng(lat, lon);
		PolylineOptions p = new PolylineOptions().add(origin).add(point)
				.color(mEventColors.getColor(MapSettings.get().getSpouseColor()));
		Log.d(TAG, "adding lines to map");
		return p;
	}
	
	private PolylineOptions setLifeLines(Event[] events, Event mCurrentEvent, EventColors mEventColors) {
		Log.d(TAG, "making life lines");
		ArrayList<LatLng> points = new ArrayList<>();
		LatLng ll = null;
		Double lat, lon;
		int i = 0;
		for (Event e : events) {
			if (e.getPersonId().equals(mCurrentEvent.getPersonId())) {
				if (e.getLatitude() != null) {
					lat = Double.parseDouble(e.getLatitude());
					lon = Double.parseDouble(e.getLongitude());
					ll = new LatLng(lat, lon);
					points.add(ll);
				}
			}
		}
		PolylineOptions options = new PolylineOptions().addAll(points)
				.color( mEventColors.getColor(MapSettings.get().getLifeColor()) );
		Log.d(TAG, "adding lines to map");
		return options;
	}
	
	private void setFamilyLines(AmazonMap amazonMap, int color, Event mCurrentEvent) {
		Log.d(TAG, "making family lines");
		
		Person root = UserInfo.get().getPerson(mCurrentEvent.getPersonId());
		LatLng base = new LatLng( Double.parseDouble(mCurrentEvent.getLatitude()),
				Double.parseDouble(mCurrentEvent.getLongitude()) );
		Person f = UserInfo.get().getPerson(root.getFather());
		Person m = UserInfo.get().getPerson(root.getMother());
		LatLng father = setLineParents(f, amazonMap, color, 10);
		LatLng mother = setLineParents(m, amazonMap, color, 10);
		
		if (mother != null) {
			PolylineOptions p = new PolylineOptions()
					.add(base).add(father).color(color).width(20);
			mPolylines.add(amazonMap.addPolyline(p));
		}
		if (mother != null) {
			PolylineOptions p = new PolylineOptions()
					.add(base).add(mother).color(color).width(20);
			mPolylines.add(amazonMap.addPolyline(p));
		}
	}
	
	private LatLng setLineParents(Person person, AmazonMap amazonMap, int color, float width) {
		if (person == null) {
			return null;
		}
		Person f = UserInfo.get().getPerson(person.getFather());
		Person m = UserInfo.get().getPerson(person.getMother());
		LatLng father = setLineParents(f, amazonMap, color, width / WIDTH_SUBTRACTOR);
		LatLng mother = setLineParents(m, amazonMap, color, width / WIDTH_SUBTRACTOR);
		
		Event birth = UserInfo.get().getOldestEvent(person.getId());
		LatLng base = new LatLng( Double.parseDouble(birth.getLatitude()),
				Double.parseDouble(birth.getLongitude()) );
		if (mother != null) {
			PolylineOptions p = new PolylineOptions()
					.add(base).add(father).color(color).width(width);
			mPolylines.add(amazonMap.addPolyline(p));
		}
		if (mother != null) {
			PolylineOptions p = new PolylineOptions()
					.add(base).add(mother).color(color).width(width);
			mPolylines.add(amazonMap.addPolyline(p));
		}
		return base;
	}
	
}
