package cs240.iainlee.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.AmazonMapOptions;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;

import java.util.HashMap;

import cs240.iainlee.models.Event;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.UserInfo;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;


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
	
	private static final String ARG_PARAM1 = "people";
	private static final String ARG_PARAM2 = "events";
	
	private static final float BIRTH_COLOR = 0;
	private static final float BAPTISM_COLOR = 100;
	private static final float MARRIAGE_COLOR = 200;
	private static final float DEATH_COLOR = 300;
	
	private Button mLogoutButton;
	private Button mToSettings;
	private Button mToFilters;
	private Button mToSearch;
	
	private ImageView mEventImage;
	private TextView mEventName;
	private TextView mEventInfo;
	private View mEventInfoWindow;
	
	private Person mCurrentPerson;
	
	private HashMap<Marker, Event> mEventMarkers;
	
	private OnLogoutListener mListener;
	
	public FamilyMapFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment FamilyMapFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static FamilyMapFragment newInstance() {
		return new FamilyMapFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mEventMarkers = new HashMap<>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_family_map, container, false);
		mLogoutButton = (Button) view.findViewById(R.id.logout_button);
		mLogoutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLogout();
			}
		});
		
		mToSettings = (Button) view.findViewById(R.id.settings_button);
		mToSettings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onChangeActivity(SettingsActivity.class);
			}
		});
		
		mToFilters = (Button) view.findViewById(R.id.filter_button);
		mToFilters.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onChangeActivity(FilterActivity.class);
			}
		});
		
		mToSearch = (Button) view.findViewById(R.id.search_button);
		mToSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onChangeActivity(SearchActivity.class);
			}
		});
		
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
		mMapFragment = SupportMapFragment.newInstance(opt);
		// Add the new fragment to the fragment manager. Note that
		// fragment_container is the ID for the frame layout defined in
		// programmatic_layout.xml
		getFragmentManager().beginTransaction()
				.add(R.id.mapFragment, mMapFragment, MAP_FRAGMENT_TAG).commit();
		
		mMapFragment.getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(AmazonMap amazonMap) {
				onMapLoad(amazonMap);
			}
		});
		
		return view;
	}
	
	private void onMapLoad(AmazonMap amazonMap) {
		Event[] events = UserInfo.get().getEvents();
		Event event = null;
		Double lat = null, lon = null;
		MarkerOptions options = null;
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
							.icon(BitmapDescriptorFactory.defaultMarker(eventColor(event)));
					
					mEventMarkers.put(amazonMap.addMarker(options), event);
				}
			} catch (NullPointerException e) {
//				Log.e(TAG, "this event doesn't have coordinates (ie hasn't happened yet)");
//				Log.e(TAG, event.toString());
			}
		}
		
		amazonMap.setInfoWindowAdapter(new EventInfoWindow());
	}
	
	private void addEventInfoWindow(Event event) {
		String personId = event.getPersonId();
		Person person = UserInfo.get().getPerson(personId);
		String fName = person.getFirstName(), lName = person.getLastName();
		mEventName.setText(fName + " " + lName);
		mEventInfo.setText(event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")");
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
		Log.d(TAG, "There should be stuff in the tag");
	}
	
	private float eventColor(Event event) {
		if (event.getEventType().equals("birth")) {
//			Log.d(TAG, "birth color");
			return BIRTH_COLOR;
		}
		else if (event.getEventType().equals("baptism")) {
//			Log.d(TAG, "baptism color");
			return BAPTISM_COLOR;
		}
		else if (event.getEventType().equals("marriage")) {
//			Log.d(TAG, "marriage color");
			return MARRIAGE_COLOR;
		}
		else if (event.getEventType().equals("death")) {
//			Log.d(TAG, "death color");
			return DEATH_COLOR;
		}
		Log.e(TAG, "What eventType is this? " + event.getEventType());
		return 0;
	}
	
	private void onClickEvent() {
		if (mCurrentPerson != null) {
			Intent intent = PersonActivity.newIntent(getContext(), mCurrentPerson.getId());
			startActivity(intent);
		}
	}
	
	private void onLogout() {
		if (mListener != null) {
			mListener.onLogout();
		}
	}
	
	private void onChangeActivity(Class k) {
		try {
			Log.d(TAG, "to " + k.getSimpleName());
			Intent intent = new Intent(getActivity(), k);
			startActivity(intent);
		} catch (ClassCastException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnLogoutListener) {
			mListener = (OnLogoutListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnLogoutListener");
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
