package cs240.iainlee.familymapclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cs240.iainlee.models.MapFilters;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {
	
	private static final String TAG = "FilterFragment";
	
	private static String ARG_PARAM = "this is the key for the filter index";
	
	private MapFilters mFilters;
	private int mFilterIndex;
	
	
	
	public FilterFragment() { // Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param filterIndex the index of the instance of mapFilters you want
	 * @return A new instance of fragment FilterFragment.
	 */
	public static FilterFragment newInstance(int filterIndex) {
		FilterFragment fragment = new FilterFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PARAM, filterIndex);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mFilterIndex = getArguments().getInt(ARG_PARAM);
			mFilters = MapFilters.get(mFilterIndex);
		}
		if (mFilters == null) {
			mFilters = MapFilters.get();
			mFilterIndex = MapFilters.size() - 1;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_filter, container, false);
		
		
		
		return view;
	}
	
}
