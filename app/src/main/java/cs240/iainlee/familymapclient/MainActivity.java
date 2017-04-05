package cs240.iainlee.familymapclient;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cs240.iainlee.models.Event;
import cs240.iainlee.models.Person;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginListener, FamilyMapFragment.OnLogoutListener {
	
	private static final String TAG = "mainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);

		if (fragment == null) {
			fragment = LoginFragment.newInstance();
			fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
			Log.d(TAG, "Loaded the login fragment");
		}
	}
	
	public void onLogin() {
		FragmentManager fm = getFragmentManager();
		Fragment fragment = FamilyMapFragment.newInstance();
		fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
		Log.d(TAG, "Loaded the family map fragment");
	}
	
	public void onLogout() {
		FragmentManager fm = getFragmentManager();
		Fragment fragment = LoginFragment.newInstance();
		fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
		Log.d(TAG, "Loaded the login fragment");
	}
}
