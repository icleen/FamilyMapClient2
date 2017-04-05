package cs240.iainlee.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.MapFragment;
import com.amazon.geo.mapsv2.SupportMapFragment;


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
	private AmazonMap mAmazonMap;
	
	private static final String ARG_PARAM1 = "people";
	private static final String ARG_PARAM2 = "events";
	
	private Button mLogoutButton;
	private Button mToPerson;
	private Button mToSettings;
	private Button mToFilters;
	private Button mToSearch;
	
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
		
		mToPerson = (Button) view.findViewById(R.id.person_button);
		mToPerson.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onChangeActivity(PersonActivity.class);
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
		
		mMapFragment = SupportMapFragment.newInstance();

		// Add the new fragment to the fragment manager. Note that
		// fragment_container is the ID for the frame layout defined in
		// programmatic_layout.xml
		getFragmentManager().beginTransaction()
				.add(R.id.mapFragment, mMapFragment, MAP_FRAGMENT_TAG).commit();
		
		return view;
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
}
