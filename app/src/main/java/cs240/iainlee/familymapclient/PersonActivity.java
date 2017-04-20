package cs240.iainlee.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cs240.iainlee.models.Person;
import cs240.iainlee.models.UserInfo;

public class PersonActivity extends AppCompatActivity {
	
	private static final String TAG = "personActivity";
	private static final String EXTRA_PERSONID = "this is the key for the person id";
	
	private Person mPerson;
	
	public static Intent newIntent(Context packageContext, String personId) {
		Intent intent = new Intent(packageContext, PersonActivity.class);
		intent.putExtra(EXTRA_PERSONID, personId);
		return intent;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		
		String personId = getIntent().getStringExtra(EXTRA_PERSONID);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.person_fragment_container);
		
		if (fragment == null) {
			fragment = PersonFragment.newInstance(personId);
			fm.beginTransaction().add(R.id.person_fragment_container, fragment).commit();
			Log.d(TAG, "Loaded the person fragment");
		}
	}
	
	private void onGoToMap() {
		Log.d(TAG, "to " + MapActivity.class.getSimpleName());
		try {
			Intent intent = new Intent(this, MapActivity.class);
			startActivity(intent);
		} catch (ClassCastException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
	}
	
}
