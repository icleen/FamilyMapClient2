package cs240.iainlee.familymapclient;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

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
	
	private Drawable mEventDrawable;
	private Drawable mMaleDrawable;
	private Drawable mFemaleDrawable;
	
	private RecyclerView mPersonRecycler;
	private RecyclerView mEventRecycler;
	private EventAdapter mEventAdapter;
	
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
		List<Event> events = UserInfo.get().getEventsById(mPerson.getId());
		mEventAdapter = new EventAdapter(events);
		mEventRecycler.setAdapter(mEventAdapter);
	}
	
	private class FamilyHolder extends RecyclerView.ViewHolder {
		
		private Person mPerson;
		private String mRelationship;
		
		public TextView mPersonInfo;
		public TextView mRelationshipInfo;
		public ImageView mPersonImage;
		
		public FamilyHolder(View view) {
			super(view);
			
			mPersonInfo = (TextView) view.findViewById(R.id.list_view_info1);
			mRelationshipInfo = (TextView) view.findViewById(R.id.list_view_info2);
			mPersonImage = (ImageView) view.findViewById(R.id.list_view_image);
		}
		
		public void bindPerson(Person person, String relationship) {
			mPerson = person;
			mRelationship = relationship;
			
			mPersonInfo.setText(mPerson.getFirstName() + " " +mPerson.getLastName());
			mRelationshipInfo.setText(relationship);
			
			if (person.getGender().equals("m")) {
				mPersonImage.setImageDrawable(mMaleDrawable);
			}else {
				mPersonImage.setImageDrawable(mFemaleDrawable);
			}
		}
		
	}
	
	private class FamilyAdapter extends RecyclerView.Adapter<FamilyHolder> {
		
		private List<Person> mPersonList;
		
		public FamilyAdapter(List<Person> personList) {
			mPersonList = personList;
		}
		
		public FamilyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			View view = inflater.inflate(R.layout.list_view, parent, false);
			return new FamilyHolder(view);
		}
		
		public void onBindViewHolder(FamilyHolder holder, int position) {
			Person person = mPersonList.get(position);
			holder.bindPerson(person, null);
		}
		
		public int getItemCount() {
			return mPersonList.size();
		}
		
	}
	
	private class EventHolder extends RecyclerView.ViewHolder {
		
		public TextView mEventInfo;
		public TextView mEventPerson;
		public ImageView mEventImage;
		
		private Event mEvent;
		
		public EventHolder(View view) {
			super(view);
			
			mEventInfo = (TextView) view.findViewById(R.id.list_view_info1);
			mEventPerson = (TextView) view.findViewById(R.id.list_view_info2);
			mEventImage = (ImageView) view.findViewById(R.id.list_view_image);
		}
		
		public void bindEvent(Event event) {
			mEvent = event;
			mEventInfo.setText(event.getEventType() + ": "
					+ event.getCity() + ", " + event.getCountry()
					+ " (" + event.getYear() + ")");
			mEventPerson.setText(mPerson.getFirstName() + " " + mPerson.getLastName());
			
			mEventImage.setImageDrawable(mEventDrawable);
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
