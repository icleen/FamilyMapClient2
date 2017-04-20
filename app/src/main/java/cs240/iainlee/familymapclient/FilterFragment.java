package cs240.iainlee.familymapclient;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cs240.iainlee.models.MapFilters;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.UserInfo;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {
	
	private static final String TAG = "FilterFragment";
	
	private static String ARG_PARAM1 = "this is the key for the filterNames";
	private static String ARG_PARAM2 = "this is the key for the filterValues";
	private static String EXTRA_FILTER_VALUES = "the key for filter values";
	
	private boolean[] mFilterValues;
	private String[] mFilterNames;
	
	private RecyclerView mFilterListView;
	private FilterAdapter mFilterAdapter;
	
	private Switch mFatherSideSwitch;
	private Switch mMotherSideSwitch;
	private Switch mMaleSwitch;
	private Switch mFemaleSideSwitch;
	
	public FilterFragment() { // Required empty public constructor
	}
	
	public static boolean[] getFilterValues(Intent result) {
		return result.getBooleanArrayExtra(EXTRA_FILTER_VALUES);
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param values an array of the boolean value for the different filters
	 * @return A new instance of fragment FilterFragment.
	 */
	public static FilterFragment newInstance(String[] filterNames, boolean[] values) {
		FilterFragment fragment = new FilterFragment();
		Bundle args = new Bundle();
		args.putStringArray(ARG_PARAM1, filterNames);
		args.putBooleanArray(ARG_PARAM2, values);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
//			mFilterNames = getArguments().getStringArray(ARG_PARAM1);
			mFilterValues = getArguments().getBooleanArray(ARG_PARAM2);
		}
		mFilterNames = UserInfo.get().getEventTypes();
		if (mFilterValues == null) {
			mFilterValues = new boolean[mFilterNames.length + 4];
			for (int i = 0; i < mFilterValues.length; i++) {
				mFilterValues[i] = true;
			}
		}
		assert (mFilterNames.length + 4 == mFilterValues.length);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_filter, container, false);
		
		mFilterListView = (RecyclerView) view.findViewById(R.id.filter_list_view);
		mFilterListView.setLayoutManager(new LinearLayoutManager(getActivity()));
		
		int index = mFilterValues.length;
		mFemaleSideSwitch = (Switch) view.findViewById(R.id.female_side_switch);
		mFemaleSideSwitch.setChecked(mFilterValues[--index]);
		if (mFilterValues[index]) {
			mFemaleSideSwitch.setText("On");
		}else {
			mFemaleSideSwitch.setText("Off");
		}
		mFemaleSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					buttonView.setText(R.string.on_button);
					mFilterValues[mFilterValues.length - 1] = true;
				}else {
					buttonView.setText(R.string.off_button);
					mFilterValues[mFilterValues.length - 1] = false;
				}
				setReturnValues();
			}
		});
		
		mMaleSwitch = (Switch) view.findViewById(R.id.male_side_switch);
		mMaleSwitch.setChecked(mFilterValues[--index]);
		if (mFilterValues[index]) {
			mMaleSwitch.setText("On");
		}else {
			mMaleSwitch.setText("Off");
		}
		mMaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					buttonView.setText(R.string.on_button);
					mFilterValues[mFilterValues.length - 2] = true;
				}else {
					buttonView.setText(R.string.off_button);
					mFilterValues[mFilterValues.length - 2] = false;
				}
				setReturnValues();
			}
		});
		
		mMotherSideSwitch = (Switch) view.findViewById(R.id.mother_side_switch);
		mMotherSideSwitch.setChecked(mFilterValues[--index]);
		if (mFilterValues[index]) {
			mMotherSideSwitch.setText("On");
		}else {
			mMotherSideSwitch.setText("Off");
		}
		mMotherSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					buttonView.setText(R.string.on_button);
					mFilterValues[mFilterValues.length - 3] = true;
				}else {
					buttonView.setText(R.string.off_button);
					mFilterValues[mFilterValues.length - 3] = false;
				}
				setReturnValues();
			}
		});
		
		mFatherSideSwitch = (Switch) view.findViewById(R.id.father_side_switch);
		mFatherSideSwitch.setChecked(mFilterValues[--index]);
		if (mFilterValues[index]) {
			mFatherSideSwitch.setText("On");
		}else {
			mFatherSideSwitch.setText("Off");
		}
		mFatherSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					buttonView.setText(R.string.on_button);
					mFilterValues[mFilterValues.length - 4] = true;
				}else {
					buttonView.setText(R.string.off_button);
					mFilterValues[mFilterValues.length - 4] = false;
				}
				setReturnValues();
			}
		});
		
		updateUI();
		
		return view;
	}
	
	private void setReturnValues() {
		MapFilters.get().setFilterValues(mFilterValues);
		Intent intent = new Intent();
		intent.putExtra(EXTRA_FILTER_VALUES, mFilterValues);
		getActivity().setResult(Activity.RESULT_OK, intent);
	}
	
	private void updateUI() {
		mFilterAdapter = new FilterAdapter(mFilterNames, mFilterValues);
		mFilterListView.setAdapter(mFilterAdapter);
	}
	
	private class FilterHolder extends RecyclerView.ViewHolder {
		
		private String mName;
		private boolean mValue;
		private int mPosition;
		
		private TextView mTitle;
		private TextView mDescriptor;
		private Switch mSwitch;
		
		public FilterHolder(View view) {
			super(view);
			
			mTitle = (TextView) view.findViewById(R.id.filter_title);
			mDescriptor = (TextView) view.findViewById(R.id.filter_descriptor);
			mSwitch = (Switch) view.findViewById(R.id.filter_switch);
		}
		
		public void bindFilter(String name, boolean value, int position) {
			mName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
			mValue = value;
			mPosition = position;
			mTitle.setText(mName + " Events");
			mDescriptor.setText("Filter by " + mName + " Events");
			mSwitch.setChecked(mValue);
			if (mValue) {
				mSwitch.setText("On");
			}else {
				mSwitch.setText("Off");
			}
			mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						buttonView.setText(R.string.on_button);
						mFilterValues[mPosition] = true;
					}else {
						buttonView.setText(R.string.off_button);
						mFilterValues[mPosition] = false;
					}
				}
			});
		}
		
	}
	
	private class FilterAdapter extends RecyclerView.Adapter<FilterHolder> {
		
		private String[] mFilterNames;
		private boolean[] mFilterValues;
		
		public FilterAdapter(String[] filterNames, boolean[] values) {
			mFilterNames = filterNames;
			mFilterValues = values;
			assert (mFilterValues.length == mFilterNames.length + 4);
		}
		
		public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			View view = inflater.inflate(R.layout.event_filter_view, parent, false);
			return new FilterHolder(view);
		}
		
		public void onBindViewHolder(FilterHolder holder, int position) {
			if (position < mFilterNames.length) {
				holder.bindFilter(mFilterNames[position], mFilterValues[position], position);
			}
		}
		
		public int getItemCount() {
			return mFilterNames.length;
		}
		
	}
	
}
