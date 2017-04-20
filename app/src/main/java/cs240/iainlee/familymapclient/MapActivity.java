package cs240.iainlee.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MapActivity extends AppCompatActivity implements FamilyMapFragment.OnLogoutListener {
	
	private static final String TAG = "MapActivity";
	private static final String EXTRA_EVENTID = "this is the key for the event id";
	
	public static Intent newIntent(Context packageContext, String eventId) {
		Intent intent = new Intent(packageContext, MapActivity.class);
		intent.putExtra(EXTRA_EVENTID, eventId);
		return intent;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		String eventId = getIntent().getStringExtra(EXTRA_EVENTID);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.map_activity_container);
		
		if (fragment == null) {
			fragment = FamilyMapFragment.newInstance(eventId);
			fm.beginTransaction().add(R.id.map_activity_container, fragment).commit();
		}
	}
	
	public void onLogout() { // this is just for compatibility
	}
	
}
