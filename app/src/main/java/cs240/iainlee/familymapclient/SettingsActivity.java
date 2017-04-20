package cs240.iainlee.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {
	
	private static final String TAG = "SettingsActivity";
	private static final String EXTRA_EVENTID = "this is the key for the event id";
	
	public static Intent newIntent(Context packageContext, String eventid) {
		Intent intent = new Intent(packageContext, SettingsActivity.class);
		intent.putExtra(EXTRA_EVENTID, eventid);
		return intent;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		String eventId = getIntent().getStringExtra(EXTRA_EVENTID);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.settings_fragment_container);

		if (fragment == null) {
			fragment = SettingsFragment.newInstance(eventId);
			fm.beginTransaction().add(R.id.settings_fragment_container, fragment).commit();
			Log.d(TAG, "Loaded the settings fragment");
		}
		
	}
}
