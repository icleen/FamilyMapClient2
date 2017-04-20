package cs240.iainlee.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import cs240.iainlee.models.Event;
import cs240.iainlee.models.LoginResponse;
import cs240.iainlee.models.MapFilters;
import cs240.iainlee.models.MapSettings;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.UserInfo;
import cs240.iainlee.servercommunicator.ServerProxy;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
	
	private static final String TAG = "SettingsFragment";
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM = "param1";
	
	private String mEventId;
	private LoginResponse mLoginResponse;
	
	private Switch mLifeStorySwitch;
	private Spinner mLifeSpinner;
	private Switch mFamilyTreeSwitch;
	private Spinner mFamilySpinner;
	private Switch mSpouseSwitch;
	private Spinner mSpouseSpinner;
	
	private Spinner mMapTypeSpinner;
	
	private TextView mResync;
	private TextView mLogout;
	
	public SettingsFragment() { // Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @param eventId Parameter 1.
	 * @return A new instance of fragment SettingsFragment.
	 */
	public static SettingsFragment newInstance(String eventId) {
		SettingsFragment fragment = new SettingsFragment();
		if (eventId != null && !eventId.isEmpty()) {
			Bundle args = new Bundle();
			args.putString(ARG_PARAM, eventId);
			fragment.setArguments(args);
		}
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mEventId = getArguments().getString(ARG_PARAM);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_settings, container, false);
		
		mLifeStorySwitch = (Switch) view.findViewById(R.id.life_story_switch);
		mLifeStorySwitch.setChecked(MapSettings.get().isLifeLines());
		if (MapSettings.get().isLifeLines()) {
			mLifeStorySwitch.setText(R.string.on_button);
		}else {
			mLifeStorySwitch.setText(R.string.off_button);
		}
		mLifeStorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					buttonView.setText(R.string.on_button);
					MapSettings.get().setLifeLines(true);
				}else {
					buttonView.setText(R.string.off_button);
					MapSettings.get().setLifeLines(false);
				}
			}
		});
		
		mLifeSpinner = (Spinner) view.findViewById(R.id.life_color_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.line_colors, android.R.layout.simple_spinner_item); // Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
		mLifeSpinner.setAdapter(adapter);
		mLifeSpinner.setSelection(MapSettings.get().getLifeColorPos());
		mLifeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				MapSettings.get().setLifeColor((CharSequence) parent.getItemAtPosition(position));
				MapSettings.get().setLifeColorPos(position);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				MapSettings.get().setLifeColor("green");
			}
		});
		
		mFamilyTreeSwitch = (Switch) view.findViewById(R.id.family_tree_switch);
		mFamilyTreeSwitch.setChecked(MapSettings.get().isFamilyLines());
		if (MapSettings.get().isFamilyLines()) {
			mFamilyTreeSwitch.setText(R.string.on_button);
		}else {
			mFamilyTreeSwitch.setText(R.string.off_button);
		}
		mFamilyTreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					buttonView.setText(R.string.on_button);
					MapSettings.get().setFamilyLines(true);
				}else {
					buttonView.setText(R.string.off_button);
					MapSettings.get().setFamilyLines(false);
				}
			}
		});
		
		mFamilySpinner = (Spinner) view.findViewById(R.id.family_color_spinner);
		adapter = ArrayAdapter.createFromResource(getContext(), R.array.line_colors, android.R.layout.simple_spinner_item); // Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
		mFamilySpinner.setAdapter(adapter);
		mFamilySpinner.setSelection(MapSettings.get().getFamilyColorPos());
		mFamilySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				MapSettings.get().setFamilyColor((CharSequence) parent.getItemAtPosition(position));
				MapSettings.get().setFamilyColorPos(position);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				MapSettings.get().setFamilyColor("green");
			}
		});
		
		mSpouseSwitch = (Switch) view.findViewById(R.id.spouse_switch);
		mSpouseSwitch.setChecked(MapSettings.get().isSpouseLines());
		if (MapSettings.get().isSpouseLines()) {
			mSpouseSwitch.setText(R.string.on_button);
		}else {
			mSpouseSwitch.setText(R.string.off_button);
		}
		mSpouseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					buttonView.setText(R.string.on_button);
					MapSettings.get().setSpouseLines(true);
				}else {
					buttonView.setText(R.string.off_button);
					MapSettings.get().setSpouseLines(false);
				}
			}
		});
		
		mSpouseSpinner = (Spinner) view.findViewById(R.id.spouse_color_spinner);
		adapter = ArrayAdapter.createFromResource(getContext(), R.array.line_colors, android.R.layout.simple_spinner_item); // Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
//		mSpouseSpinner
		mSpouseSpinner.setAdapter(adapter);
		mSpouseSpinner.setSelection(MapSettings.get().getSpouseColorPos());
		mSpouseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				MapSettings.get().setSpouseColor( (CharSequence) parent.getItemAtPosition(position) );
				MapSettings.get().setSpouseColorPos(position);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				MapSettings.get().setSpouseColor("green");
			}
		});
		
		mMapTypeSpinner = (Spinner) view.findViewById(R.id.map_type_spinner);
		adapter = ArrayAdapter.createFromResource(getContext(),	R.array.map_types, android.R.layout.simple_spinner_item); // Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
		mMapTypeSpinner.setAdapter(adapter);
		mMapTypeSpinner.setSelection(MapSettings.get().getMapTypePos());
		mMapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				MapSettings.get().setMapType((CharSequence) parent.getItemAtPosition(position));
				MapSettings.get().setMapTypePos(position);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				MapSettings.get().setLifeColor("normal");
			}
		});
		
		mResync = (TextView) view.findViewById(R.id.settings_resync);
		mResync.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MapSettings.get().setResync(true);
				Log.d(TAG, "re-syncing");
				try {
					new loginServerTask().execute();
				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
					Toast.makeText(getActivity(), "Re-Sync Failed", Toast.LENGTH_SHORT).show();
				}
				Toast.makeText(getContext(), "resync", Toast.LENGTH_SHORT).show();
			}
		});
		
		mLogout = (TextView) view.findViewById(R.id.settings_logout);
		mLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "logout", Toast.LENGTH_SHORT).show();
				UserInfo.get().clear();
				MapSettings.get().clear();
				MapFilters.get().clear();
				MainActivity.isMap = false;
				goToMap();
			}
		});
		
		return view;
	}
	
	private void goToMap() {
		Intent intent = new Intent(getActivity(), MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	private class loginServerTask extends AsyncTask<Void, Void, LoginResponse> {
		
		@Override
		protected LoginResponse doInBackground(Void... params) {
			LoginResponse response = new ServerProxy().userLogin(UserInfo.get().getLoginInfo());
			return response;
		}
		
		@Override
		protected void onPostExecute(LoginResponse r) {
			mLoginResponse = r;
			if(mLoginResponse != null) {
				Log.d(TAG, mLoginResponse.toString());
			}
			if (mLoginResponse.getErrorMessage() != null) {
				Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
				return;
			}
			
			new fetchEventsTask().execute();
			new fetchPersonsTask().execute();

//			Toast.makeText(getActivity(), "Logged In", Toast.LENGTH_SHORT).show();
		}
	}
	
	private class fetchEventsTask extends AsyncTask<Void, Void, Event[]> {
		
		@Override
		protected Event[] doInBackground(Void... params) {
			return new ServerProxy().getEvents();
		}
		
		@Override
		protected void onPostExecute(Event[] r) {
			if(r != null) {
				Log.d(TAG, "Got Events");
			}
			UserInfo.get().setEvents(r);
//			Toast.makeText(getActivity(), mResponse.getUserName(), Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private class fetchPersonsTask extends AsyncTask<Void, Void, Person[]> {
		
		@Override
		protected Person[] doInBackground(Void... params) {
			return new ServerProxy().getPeople();
		}
		
		@Override
		protected void onPostExecute(Person[] r) {
			if(r != null) {
				Log.d(TAG, "Got People");
			}
			UserInfo.get().setPersons(r);
			Person user = getUserPerson(r);
			if (user == null) {
				Log.e(TAG, "There was no person associated with the user!");
				Toast.makeText(getActivity(), "No User Error", Toast.LENGTH_SHORT).show();
				return;
			}
			assert (user.equals(UserInfo.get().getUser()));
			UserInfo.get().setUser(user);
			goToMap();
		}
	}
	
	private Person getUserPerson(Person[] persons) {
//		Log.i(TAG, mResponse.getPersonId());
		for (int i = 0; i < persons.length; i++) {
//			Log.i(TAG, mPersons[i].getId());
			if( persons[i].getId().equals(mLoginResponse.getPersonId()) ) {
				return persons[i];
			}
		}
		return null;
	}
	
}
