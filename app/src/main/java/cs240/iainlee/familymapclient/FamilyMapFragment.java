package cs240.iainlee.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.AmazonMapOptions;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.amazon.geo.mapsv2.model.Polyline;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs240.iainlee.models.Event;
import cs240.iainlee.models.MapSettings;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.UserInfo;
import cs240.iainlee.support.EventColors;
import cs240.iainlee.support.Filterer;
import cs240.iainlee.support.LineDrawer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FamilyMapFragment.OnLogoutListener} interface
 * to handle interaction events.
 * Use the {@link FamilyMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FamilyMapFragment extends Fragment {
	
	private static final String TAG = "FamilyMapFrag";
	
	private SupportMapFragment mMapFragment;
	private static final String MAP_FRAGMENT_TAG = "mapfragment";
	
	private static final int REQUEST_CODE_FILTER = 0;
	private static final int REQUEST_CODE_SETTINGS = 2;
	private static final int REQUEST_CODE_SEARCH = 4;
	
	private static final String ARG_PARAM1 = "eventId";
	
	private static EventColors sEventColors = new EventColors();
	private static LineDrawer sLineDrawer = new LineDrawer();
	
	private ImageView mEventImage;
	private TextView mEventName;
	private TextView mEventInfo;
	private View mEventInfoWindow;
	
	private Person mCurrentPerson;
	private static Event mCurrentEvent;
	
	private HashMap<Marker, Event> mEventMarkers;
	private List<Polyline> mPolylines = new ArrayList<Polyline>();
	
	private OnLogoutListener mListener;
	
	private String[] mEventTypes;
	private boolean[] mFilterValues;
	
	public FamilyMapFragment() { // Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment FamilyMapFragment.
	 */
	public static FamilyMapFragment newInstance(String eventId) {
		FamilyMapFragment fragment = new FamilyMapFragment();
		if (eventId != null) {
			Bundle args = new Bundle();
			args.putString(ARG_PARAM1, eventId);
			fragment.setArguments(args);
		}
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (getActivity().getClass() == MapActivity.class) {
			( (AppCompatActivity) getActivity() ).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		mEventMarkers = new HashMap<>();
		String eventId = null;
		if (getArguments() != null) {
			Log.d(TAG, "setting the current Event");
			eventId = getArguments().getString(ARG_PARAM1);
			Log.d(TAG, "event is: " + eventId + ", " + UserInfo.get().getEvent(eventId));
			mCurrentEvent = UserInfo.get().getEvent(eventId);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_family_map, container, false);
		
		mEventTypes = UserInfo.get().getEventTypes();
		
		mEventImage = (ImageView) view.findViewById(R.id.event_window_image2);
		Drawable andriodIcon = new IconDrawable(getContext(), Iconify.IconValue.fa_map_marker).
				colorRes(R.color.colorPrimary).sizeDp(40);
		mEventImage.setImageDrawable(andriodIcon);
		mEventName = (TextView) view.findViewById(R.id.event_window_name2);
		mEventInfo = (TextView) view.findViewById(R.id.event_window_info2);
		
		mEventInfoWindow = view.findViewById(R.id.event_info_window);
		mEventInfoWindow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickEvent();
			}
		});
		
		AmazonMapOptions opt = new AmazonMapOptions();
		opt.mapToolbarEnabled(false);
		if (MapSettings.get().getMapType().equals("Hybrid")) {
			Log.d(TAG, "mapType is Hybrid");
			opt.mapType(AmazonMap.MAP_TYPE_HYBRID);
		}else if (MapSettings.get().getMapType().equals("Satellite")) {
			Log.d(TAG, "mapType is Satellite");
			opt.mapType(AmazonMap.MAP_TYPE_SATELLITE);
		}else if (MapSettings.get().getMapType().equals("Terrain")) {
			Log.d(TAG, "mapType is Terrain");
			opt.mapType(AmazonMap.MAP_TYPE_TERRAIN);
		}
		mMapFragment = SupportMapFragment.newInstance(opt);
		// Add the new fragment to the fragment manager. Note that
		// fragment_container is the ID for the frame layout defined in
		// programmatic_layout.xml
		getFragmentManager().beginTransaction()
				.add(R.id.mapFragment, mMapFragment, MAP_FRAGMENT_TAG).commit();
		
		if (mCurrentEvent != null) {
			Log.d(TAG, "putting in the event window");
			addEventInfoWindow(mCurrentEvent);
		}
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "resuming");
		mMapFragment.getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(AmazonMap amazonMap) {
				onMapLoad(amazonMap);
			}
		});
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBooleanArray(TAG, mFilterValues);
		Log.d(TAG, "put the filterValues");
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		Log.d(TAG, "activities: " + getActivity().getClass() + " " + MainActivity.class);
		if (getActivity().getClass() == MainActivity.class) {
			inflater.inflate(R.menu.map_toolbar, menu);
			
			MenuItem item = null;
			Drawable icon = null;
			item = menu.findItem(R.id.menu_search);
			icon = new IconDrawable(getContext(), Iconify.IconValue.fa_search).sizeDp(20);
			item.setIcon(icon);
			
			item = menu.findItem(R.id.menu_filters);
			icon = new IconDrawable(getContext(), Iconify.IconValue.fa_filter).sizeDp(20);
			item.setIcon(icon);
			
			item = menu.findItem(R.id.menu_settings);
			icon = new IconDrawable(getContext(), Iconify.IconValue.fa_gear).sizeDp(20);
			item.setIcon(icon);
		}else {
			inflater.inflate(R.menu.person_toolbar, menu);
			
			MenuItem item = null;
			Drawable icon = null;
			item = menu.findItem(R.id.go_to_top);
			icon = new IconDrawable(getContext(), Iconify.IconValue.fa_angle_double_up).sizeDp(40);
			item.setIcon(icon);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
			case R.id.menu_search:
				intent = SearchActivity.newIntent(getContext());
				startActivity(intent);
				return true;
			case R.id.menu_filters:
				intent = FilterActivity.newIntent(getContext(), mEventTypes, mFilterValues);
				startActivity(intent);
				return true;
			case R.id.menu_settings:
				intent = SettingsActivity.newIntent(getContext(), null);
				startActivity(intent);
				return true;
			case R.id.go_to_top:
				Log.d(TAG, "Going to the top");
				intent = new Intent(getActivity(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				return true;
			case android.R.id.home:
				getActivity().finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void onMapLoad(AmazonMap amazonMap) {
		amazonMap.clear();
		
		if (mCurrentEvent != null) {
			LatLng ll = new LatLng(Double.parseDouble(mCurrentEvent.getLatitude()),
					Double.parseDouble(mCurrentEvent.getLongitude()));
			amazonMap.animateCamera( CameraUpdateFactory.newLatLng( ll ) );
		}
		
		Event[] events = Filterer.getEvents(mFilterValues, mEventTypes);
		Event event = null;
		Double lat, lon;
		MarkerOptions options = null;
		if (events != null) {
			for (int i = 0; i < events.length; i++) {
				try {
					event = events[i];
					if (!event.getLatitude().isEmpty()) {
						lat = Double.parseDouble(event.getLatitude());
						lon = Double.parseDouble(event.getLongitude());
						LatLng point = new LatLng(lat, lon);
						options = new MarkerOptions()
								.position(point)
								.title("Event: " + event.getEventType() + " at: ")
								.snippet(point.toString())
								.icon(BitmapDescriptorFactory.defaultMarker(sEventColors.eventColor(event)));
						
						mEventMarkers.put(amazonMap.addMarker(options), event);
					}
				} catch (NullPointerException e) {
//				Log.e(TAG, "this event doesn't have coordinates (ie hasn't happened yet)");
//				Log.e(TAG, event.toString());
				}
			}
		}else {
			Log.e(TAG, "there are no events in the UserInfo class: " + events);
		}
		
		setLines(amazonMap, events);
		
		amazonMap.setInfoWindowAdapter(new EventInfoWindow());
	}
	
	private void setLines(AmazonMap amazonMap, Event[] events) {
		MapSettings settings = MapSettings.get();
		Log.d(TAG, "mapType: " + settings.getMapType());
		mPolylines = sLineDrawer.setLines(amazonMap, events, mCurrentEvent, sEventColors);
	}
	
	private void addEventInfoWindow(Event event) {
		String personId = event.getPersonId();
		Person person = UserInfo.get().getPerson(personId);
		if (person == null) {
			Log.e(TAG, "497: person was null: " + event.getPersonId());
			return;
		}
		String fName = person.getFirstName(), lName = person.getLastName();
		mEventName.setText(fName + " " + lName);
		mEventInfo.setText(event.getEventType() + ": " + event.getCity() + ", "
				+ event.getCountry() + " (" + event.getYear() + ")");
		if (person.getGender().equals("m")) {
			Drawable genderIcon = new IconDrawable(getContext(), Iconify.IconValue.fa_male).
					colorRes(R.color.colorPrimary).sizeDp(40);
			mEventImage.setImageDrawable(genderIcon);
		}else {
			Drawable genderIcon = new IconDrawable(getContext(), Iconify.IconValue.fa_female).
					colorRes(R.color.colorAccent).sizeDp(40);
			mEventImage.setImageDrawable(genderIcon);
		}
		mCurrentPerson = person;
		mCurrentEvent = event;
		mMapFragment.getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(AmazonMap amazonMap) {
				setLines(amazonMap, Filterer.getEvents(mFilterValues, mEventTypes));
			}
		});
//		Log.d(TAG, "There should be stuff in the tag");
	}
	
	private void onClickEvent() {
		if (mCurrentPerson != null) {
			Intent intent = PersonActivity.newIntent(getContext(), mCurrentPerson.getId());
			startActivity(intent);
		}
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnLogoutListener) {
			mListener = (OnLogoutListener) context;
		} else {
			throw new RuntimeException(context.toString() + " must implement OnLogoutListener");
		}
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}
	
	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 */
	public interface OnLogoutListener {
		void onLogout();
	}
	
	
	private class EventInfoWindow implements AmazonMap.InfoWindowAdapter {
		
		@Override
		public View getInfoContents(Marker marker) {
			Event event = mEventMarkers.get(marker);
			addEventInfoWindow(event);
			return null;
		}
		
		@Override
		public View getInfoWindow(Marker marker) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			View v = inflater.inflate(R.layout.event_info_window, null);
			Event event = mEventMarkers.get(marker);
			addEventInfoWindow(event);
			return v;
		}
		
	}
	
}
