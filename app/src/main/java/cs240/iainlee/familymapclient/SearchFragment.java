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
import android.widget.Button;
import android.widget.EditText;
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
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
	
	private static final String TAG = "SearchFragment";
	
	private Drawable mEventDrawable;
	private Drawable mMaleDrawable;
	private Drawable mFemaleDrawable;
	
	private RecyclerView mPersonRecycler;
	private RecyclerView mEventRecycler;
	private EventAdapter mEventAdapter;
	private PersonAdapter mPersonAdapter;
	
	private Button mSearchButton;
	private EditText mSearchBar;
	
	public SearchFragment() { // Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment SearchFragment.
	 */
	public static SearchFragment newInstance() {
		SearchFragment fragment = new SearchFragment();
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_search, container, false);
		
		mSearchBar = (EditText) view.findViewById(R.id.search_bar);
		
		mSearchButton = (Button) view.findViewById(R.id.search_button);
		mSearchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String search = mSearchBar.getText().toString();
				Log.d(TAG, "Search: " + search);
				updateUI(search);
			}
		});
		
		mPersonRecycler = (RecyclerView) view.findViewById(R.id.search_recycler_person);
		mPersonRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		mEventRecycler = (RecyclerView) view.findViewById(R.id.search_recycler_event);
		mEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		
		mEventDrawable = new IconDrawable(getContext(), Iconify.IconValue.fa_map_marker).
				colorRes(R.color.forest_green).sizeDp(40);
		
		mMaleDrawable = new IconDrawable(getContext(), Iconify.IconValue.fa_male).
				colorRes(R.color.colorPrimary).sizeDp(40);
		
		mFemaleDrawable = new IconDrawable(getContext(), Iconify.IconValue.fa_female).
				colorRes(R.color.colorAccent).sizeDp(40);
		
		updateUI(null);
		
		return view;
	}
	
	private void updateUI(String search) {
		if (search == null) {
			return;
		}
		try {
			mPersonAdapter = new PersonAdapter( getPersons(search.toLowerCase()) );
			mPersonRecycler.setAdapter(mPersonAdapter);
			
			mEventAdapter = new EventAdapter( getEvents(search.toLowerCase()) );
			mEventRecycler.setAdapter(mEventAdapter);
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
	}
	
	private List<Person> getPersons(String search) {
		ArrayList<Person> results = new ArrayList<>();
		Person[] persons = UserInfo.get().getPersons();
		for (Person p : persons) {
			if (p.getFirstName().toLowerCase().contains(search) || p.getLastName().toLowerCase().contains(search)) {
				results.add(p);
			}
		}
		
		return results;
	}
	
	private List<Event> getEvents(String search) {
		ArrayList<Event> results = new ArrayList<>();
		if (search.isEmpty()) {
			return results;
		}
		Person[] persons = UserInfo.get().getPersons();
		for (Person p : persons) {
			if (p.getFirstName().toLowerCase().contains(search) || p.getLastName().toLowerCase().contains(search)) {
				results.addAll(UserInfo.get().getEventsById(p.getId()));
			}
		}
		Event[] events = UserInfo.get().getEvents();
		for (Event e : events) {
			if ( e.getEventType().toLowerCase().contains(search) ) {
				results.add(e);
			}else if (e.getCity() != null && e.getCity().toLowerCase().contains(search)) {
				results.add(e);
			}else if (e.getCountry() != null && e.getCountry().toLowerCase().contains(search)) {
				results.add(e);
			}else if (e.getYear() != null && e.getYear().toLowerCase().contains(search)) {
				results.add(e);
			}
		}
		
		return results;
	}
	
	private class PersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		
		private Person mPerson;
		
		public TextView mPersonInfo;
		public TextView mRelationshipInfo;
		public ImageView mPersonImage;
		
		public PersonHolder(View view) {
			super(view);
			view.setOnClickListener(this);
			
			mPersonInfo = (TextView) view.findViewById(R.id.list_view_info1);
			mRelationshipInfo = (TextView) view.findViewById(R.id.list_view_info2);
			mPersonImage = (ImageView) view.findViewById(R.id.list_view_image);
		}
		
		public void bindPerson(Person person) {
			mPerson = person;
			assert (mPerson != null);
			mPersonInfo.setText(mPerson.getFirstName() + " " + mPerson.getLastName());
			if (person.getGender().equals("m")) {
				mPersonImage.setImageDrawable(mMaleDrawable);
			}else {
				mPersonImage.setImageDrawable(mFemaleDrawable);
			}
		}
		
		@Override
		public void onClick(View view) {
			if (mPerson != null) {
				Intent intent = PersonActivity.newIntent(getContext(), mPerson.getId());
				startActivity(intent);
			}
		}
		
	}
	
	private class PersonAdapter extends RecyclerView.Adapter<PersonHolder> {
		
		private List<Person> mPersonList;
		
		public PersonAdapter(List<Person> personList) {
			mPersonList = personList;
		}
		
		public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			View view = inflater.inflate(R.layout.list_view, parent, false);
			return new PersonHolder(view);
		}
		
		public void onBindViewHolder(PersonHolder holder, int position) {
			holder.bindPerson(mPersonList.get(position));
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
			Person person = UserInfo.get().getPerson(mEvent.getPersonId());
			assert (event != null);
			assert (person != null);
			mEventInfo.setText(mEvent.getEventType() + ": "
					+ mEvent.getCity() + ", " + mEvent.getCountry()
					+ " (" + mEvent.getYear() + ")");
			mEventPerson.setText(person.getFirstName() + " " + person.getLastName());
			
			mEventImage.setImageDrawable(mEventDrawable);
		}
		
		@Override
		public void onClick(View view) {
			try {
				Log.d(TAG, "puttiing the event id in the intent: " + mEvent.getEventId());
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
