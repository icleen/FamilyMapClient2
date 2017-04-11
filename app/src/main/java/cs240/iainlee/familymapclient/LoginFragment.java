package cs240.iainlee.familymapclient;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import cs240.iainlee.models.Event;
import cs240.iainlee.models.LoginInfo;
import cs240.iainlee.models.LoginResponse;
import cs240.iainlee.models.Person;
import cs240.iainlee.models.UserInfo;
import cs240.iainlee.servercommunicator.ServerProxy;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnLoginListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
	
	private static final String TAG = "LoginFrag";
	
	private EditText mServerHost;
	private EditText mServerPort;
	private EditText mUsername;
	private EditText mPassword;
	private EditText mFirstName;
	private EditText mLastName;
	private EditText mEmail;
	
//	private RadioButton mMale;
//	private RadioButton mFemale;
	private RadioGroup mRadioGroup;
	
	private Button mSignIn;
	private Button mRegister;
	
	private LoginInfo mLoginInfo;
	private LoginResponse mResponse;
	
	private Person[] mPersons;
	private Event[] mEvents;
	
	private OnLoginListener mListener;
	
	public LoginFragment() {
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment LoginFragment.
	 */
	public static LoginFragment newInstance() {
		LoginFragment fragment = new LoginFragment();
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLoginInfo = new LoginInfo();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		
		mUsername = (EditText) view.findViewById(R.id.username);
		mUsername.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mLoginInfo.setUsername(s.toString());
//				Log.i("username", "added the username: " + mLoginInfo.getUsername());
				changeAccessibility();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		mPassword = (EditText) view.findViewById(R.id.password);
		mPassword.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mLoginInfo.setPassword(s.toString());
//				Log.d("password", "added the password: " + mLoginInfo.getPassword());
				changeAccessibility();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		mFirstName = (EditText) view.findViewById(R.id.firstName);
		mFirstName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mLoginInfo.setFirstName(s.toString());
				changeAccessibility();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		mLastName = (EditText) view.findViewById(R.id.lastName);
		mLastName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mLoginInfo.setLastName(s.toString());
				changeAccessibility();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		mEmail = (EditText) view.findViewById(R.id.email);
		mEmail.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mLoginInfo.setEmail(s.toString());
//				Log.d("email", "added the email: " + mLoginInfo.getEmail());
				changeAccessibility();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		mLoginInfo.setServerHost("192.168.0.109");
		mServerHost = (EditText) view.findViewById(R.id.server_host);
		mServerHost.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mLoginInfo.setServerHost(s.toString());
//				Log.d("server_host", "added the server host number " + mLoginInfo.getServerHost());
				changeAccessibility();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		mLoginInfo.setServerPort("3740");
		mServerPort = (EditText) view.findViewById(R.id.server_port);
		mServerPort.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mLoginInfo.setServerPort(s.toString());
//				Log.d("server_port", "added the server port number " + mLoginInfo.getServerPort());
				changeAccessibility();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
					case R.id.male_radio:
						mLoginInfo.setGender("m");
						break;
					case R.id.female_radio:
						mLoginInfo.setGender("f");
						break;
				}
//				Log.d("gender", "gender: " + mLoginInfo.getGender());
				changeAccessibility();
			}
		});
		
		mSignIn = (Button) view.findViewById(R.id.sign_in_button);
		mSignIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d(TAG, "Now loging in");
				try {
					new loginServerTask().execute();
				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
					Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		mRegister = (Button) view.findViewById(R.id.register_button);
		mRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Now registering");
				try {
					new registerInServerTask().execute();
				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
					Toast.makeText(getActivity(), "Register Failed", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		mSignIn.setEnabled(false);
		mRegister.setEnabled(false);
		
		return view;
	}
	
	public void onLogin() {
		if (mListener != null) {
			mListener.onLogin();
		}
	}
	
	private void changeAccessibility() {
		if(mLoginInfo.isLoginReady()) {
			mSignIn.setEnabled(true);
		}if(mLoginInfo.isRegisterReady()) {
			mRegister.setEnabled(true);
		}
	}
	
	private class registerInServerTask extends AsyncTask<Void, Void, LoginResponse> {
		
		@Override
		protected LoginResponse doInBackground(Void... params) {
			LoginResponse response = new ServerProxy().registerUser(mLoginInfo);
			return response;
		}
		
		@Override
		protected void onPostExecute(LoginResponse r) {
			mResponse = r;
			if(mResponse != null) {
				Log.d(TAG, mResponse.toString());
			}
			if (mResponse.getErrorMessage() != null) {
				Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
				return;
			}
			
			new fetchPersonsTask().execute();
			new fetchEventsTask().execute();
			
			Toast.makeText(getActivity(), "Registered", Toast.LENGTH_SHORT).show();
		}
	}
	
	private class loginServerTask extends AsyncTask<Void, Void, LoginResponse> {
		
		@Override
		protected LoginResponse doInBackground(Void... params) {
			LoginResponse response = new ServerProxy().userLogin(mLoginInfo);
			return response;
		}
		
		@Override
		protected void onPostExecute(LoginResponse r) {
			mResponse = r;
			if(mResponse != null) {
				Log.d(TAG, mResponse.toString());
			}
			if (mResponse.getErrorMessage() != null) {
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
			mEvents = r;
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
			mPersons = r;
			UserInfo.get().setPersons(r);
			Person user = getUserPerson();
			if (user == null) {
				Log.e(TAG, "There was no person associated with the user!");
				Toast.makeText(getActivity(), "No User Error", Toast.LENGTH_SHORT).show();
				return;
			}
			UserInfo.get().setUser(user);
			Toast.makeText(getActivity(), user.getFirstName() + ", " + user.getLastName(), Toast.LENGTH_SHORT).show();
			onLogin();
		}
	}
	
	private Person getUserPerson() {
//		Log.i(TAG, mResponse.getPersonId());
		for (int i = 0; i < mPersons.length; i++) {
//			Log.i(TAG, mPersons[i].getId());
			if( mPersons[i].getId().equals(mResponse.getPersonId()) ) {
				return mPersons[i];
			}
		}
		return null;
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnLoginListener) {
			mListener = (OnLoginListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
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
	public interface OnLoginListener {
		void onLogin();
	}
}
