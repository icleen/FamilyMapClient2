package cs240.iainlee.familymapclient;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.List;

import cs240.iainlee.models.Event;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.UserInfo;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends Fragment {
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM = "param";
	private static final String TAG = "PersonFragment";
	
	private Drawable mEventDrawable;
	private Drawable mMaleDrawable;
	private Drawable mFemaleDrawable;
	
	private RecyclerView mPersonRecycler;
	private RecyclerView mEventRecycler;
	private EventAdapter mEventAdapter;
	private FamilyAdapter mFamilyAdapter;
	
	private Person mPerson;
	
	public PersonFragment() { // Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @param personId the id of the person to load
	 * @return A new instance of fragment PersonFragment.
	 */
	public static PersonFragment newInstance(String personId) {
		PersonFragment fragment = new PersonFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM, personId);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() == null) {
			throw new IllegalArgumentException();
		}
		String personId = getArguments().getString(ARG_PARAM);
		mPerson = UserInfo.get().getPerson(personId);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_person, container, false);
		
		TextView name = null;
		name = (TextView) view.findViewById(R.id.person_first_name);
		name.setText(mPerson.getFirstName());
		name = (TextView) view.findViewById(R.id.person_last_name);
		name.setText(mPerson.getLastName());
		
		name = (TextView) view.findViewById(R.id.person_gender);
		if (mPerson.getGender().equals("m")) {
			name.setText("Male");
		}else {
			name.setText("Female");
		}
		
		mPersonRecycler = (RecyclerView) view.findViewById(R.id.person_recycler_view);
		mPersonRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		mEventRecycler = (RecyclerView) view.findViewById(R.id.event_recycler_view);
		mEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		
		mEventDrawable = new IconDrawable(getContext(), Iconify.IconValue.fa_map_marker).
				colorRes(R.color.forest_green).sizeDp(40);
		
		mMaleDrawable = new IconDrawable(getContext(), Iconify.IconValue.fa_male).
				colorRes(R.color.colorPrimary).sizeDp(40);
		
		mFemaleDrawable = new IconDrawable(getContext(), Iconify.IconValue.fa_female).
				colorRes(R.color.colorAccent).sizeDp(40);
		
		updateUI();
		
		return view;
	}
	
	private void updateUI() {
		try {
			List<Person> family = UserInfo.get().getChildren(mPerson);
			List<String> relations = new ArrayList<>();
			family.add(0, UserInfo.get().getPerson(mPerson.getFather()));
//			Log.d(TAG, "father: " + mPerson.getFather());
			relations.add(0, "Father");
	
			family.add(1, UserInfo.get().getPerson(mPerson.getMother()));
//			Log.d(TAG, "mother: " + mPerson.getMother());
			relations.add(1, "Mother");
	
			family.add(2, UserInfo.get().getPerson(mPerson.getSpouse()));
//			Log.d(TAG, "spouse: " + mPerson.getSpouse());
			relations.add(2, "Spouse");
			
			for (int i = 0; i < family.size(); i++) {
				relations.add("Child");
			}
			assert(family.size() == relations.size());
			
			mFamilyAdapter = new FamilyAdapter(family, relations);
			mPersonRecycler.setAdapter(mFamilyAdapter);
			
			List<Event> events = UserInfo.get().getEventsById(mPerson.getId());
			mEventAdapter = new EventAdapter(events);
			mEventRecycler.setAdapter(mEventAdapter);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
	}
	
	
	
	private class FamilyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		
		private Person mFamilyPerson;
		
		public TextView mPersonInfo;
		public TextView mRelationshipInfo;
		public ImageView mPersonImage;
		
		public FamilyHolder(View view) {
			super(view);
			view.setOnClickListener(this);
			
			mPersonInfo = (TextView) view.findViewById(R.id.list_view_info1);
			mRelationshipInfo = (TextView) view.findViewById(R.id.list_view_info2);
			mPersonImage = (ImageView) view.findViewById(R.id.list_view_image);
		}
		
		public void bindPerson(Person person, String relation) {
			mFamilyPerson = person;
			if (mFamilyPerson != null) {
				mPersonInfo.setText(mFamilyPerson.getFirstName() + " " + mFamilyPerson.getLastName());
				
				if (person.getGender().equals("m")) {
					mPersonImage.setImageDrawable(mMaleDrawable);
				}else {
					mPersonImage.setImageDrawable(mFemaleDrawable);
				}
			}else {
				mPersonInfo.setText("NA");
				if (relation.equals("Father")) {
					mPersonImage.setImageDrawable(mMaleDrawable);
				}
				else if (relation.equals("Mother")) {
					mPersonImage.setImageDrawable(mFemaleDrawable);
				}
				else if (mPerson.getGender().equals("m")) { // it must be a null spouse so the gender depends on user gender
					mPersonImage.setImageDrawable(mFemaleDrawable);
				}
				else {
					mPersonImage.setImageDrawable(mMaleDrawable);
				}
			}
			mRelationshipInfo.setText(relation);
		}
		
		@Override
		public void onClick(View view) {
			Intent intent = PersonActivity.newIntent(getContext(), mFamilyPerson.getId());
			startActivity(intent);
		}
		
	}
	
	private class FamilyAdapter extends RecyclerView.Adapter<FamilyHolder> {
		
		private List<Person> mPersonList;
		private List<String> mRelations;
		
		public FamilyAdapter(List<Person> personList, List<String> relations) {
			mPersonList = personList;
			mRelations = relations;
		}
		
		public FamilyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			View view = inflater.inflate(R.layout.list_view, parent, false);
			return new FamilyHolder(view);
		}
		
		public void onBindViewHolder(FamilyHolder holder, int position) {
			holder.bindPerson(mPersonList.get(position), mRelations.get(position));
		}
		
		public int getItemCount() {
			return mPersonList.size();
		}
		
	}
	
	private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		
		public TextView mEventInfo;
		public TextView mEventPerson;
		public ImageView mEventImage;
		
		private Event mEvent;
		
		public EventHolder(View view) {
			super(view);
			view.setOnClickListener(this);
			
			mEventInfo = (TextView) view.findViewById(R.id.list_view_info1);
			mEventPerson = (TextView) view.findViewById(R.id.list_view_info2);
			mEventImage = (ImageView) view.findViewById(R.id.list_view_image);
		}
		
		public void bindEvent(Event event) {
			mEvent = event;
			mEventInfo.setText(mEvent.getEventType() + ": "
					+ mEvent.getCity() + ", " + mEvent.getCountry()
					+ " (" + mEvent.getYear() + ")");
			mEventPerson.setText(mPerson.getFirstName() + " " + mPerson.getLastName());
			
			mEventImage.setImageDrawable(mEventDrawable);
		}
		
		@Override
		public void onClick(View view) {
			try {
				Intent intent = MapActivity.newIntent(getContext(), mEvent.getEventId());
				startActivity(intent);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
				Log.e(TAG, "" + e.getStackTrace());
			}
		}
		
	}
	
	private class EventAdapter extends RecyclerView.Adapter<EventHolder> {
		
		private List<Event> mEventList;
		
		public EventAdapter(List<Event> eventList) {
			mEventList = eventList;
		}
		
		public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			View view = inflater.inflate(R.layout.list_view, parent, false);
			return new EventHolder(view);
		}
		
		public void onBindViewHolder(EventHolder holder, int position) {
			Event event = mEventList.get(position);
			holder.bindEvent(event);
		}
		
		public int getItemCount() {
			return mEventList.size();
		}
		
	}
	
}
