package cs240.iainlee.familymapclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import cs240.iainlee.models.MapSettings;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM = "param1";
	
	private String mEventId;
	private MapSettings mOptions;
	
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
		mOptions = new MapSettings();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_settings, container, false);
		
		mLifeStorySwitch = (Switch) view.findViewById(R.id.life_story_switch);
		mLifeStorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					buttonView.setText(R.string.on_button);
					mOptions.setLifeLines(true);
				}else {
					buttonView.setText(R.string.off_button);
					mOptions.setLifeLines(false);
				}
			}
		});
		
		mLifeSpinner = (Spinner) view.findViewById(R.id.life_color_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.line_colors, android.R.layout.simple_spinner_item); // Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
		mLifeSpinner.setAdapter(adapter);
		
		mFamilyTreeSwitch = (Switch) view.findViewById(R.id.family_tree_switch);
		mFamilyTreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					buttonView.setText(R.string.on_button);
					mOptions.setFamilyLines(true);
				}else {
					buttonView.setText(R.string.off_button);
					mOptions.setFamilyLines(false);
				}
			}
		});
		
		mFamilySpinner = (Spinner) view.findViewById(R.id.family_color_spinner);
		adapter = ArrayAdapter.createFromResource(getContext(), R.array.line_colors, android.R.layout.simple_spinner_item); // Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
		mFamilySpinner.setAdapter(adapter);
		
		mSpouseSwitch = (Switch) view.findViewById(R.id.spouse_switch);
		mSpouseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					buttonView.setText(R.string.on_button);
					mOptions.setSpouseLines(true);
				}else {
					buttonView.setText(R.string.off_button);
					mOptions.setSpouseLines(false);
				}
			}
		});
		
		mSpouseSpinner = (Spinner) view.findViewById(R.id.spouse_color_spinner);
		adapter = ArrayAdapter.createFromResource(getContext(), R.array.line_colors, android.R.layout.simple_spinner_item); // Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
		mSpouseSpinner.setAdapter(adapter);
		
		mMapTypeSpinner = (Spinner) view.findViewById(R.id.map_type_spinner);
		adapter = ArrayAdapter.createFromResource(getContext(),	R.array.map_types, android.R.layout.simple_spinner_item); // Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
		mMapTypeSpinner.setAdapter(adapter);
		
		mResync = (TextView) view.findViewById(R.id.settings_resync);
		mResync.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mOptions.setResync(true);
				Toast.makeText(getContext(), "resync", Toast.LENGTH_SHORT).show();
			}
		});
		
		mLogout = (TextView) view.findViewById(R.id.settings_logout);
		mLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mOptions.setLogout(true);
				Toast.makeText(getContext(), "logout", Toast.LENGTH_SHORT).show();
			}
		});
		
		return view;
	}
	
}
