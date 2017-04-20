package cs240.iainlee.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class FilterActivity extends AppCompatActivity {
	
	private static final String TAG = "FilterActivity";
	private static final String EXTRA_FILTER_NAMES = "key to the filterNames";
	private static final String EXTRA_FILTER_VALUES = "key to the filterValues";
	
	public static Intent newIntent(Context packageContext, String[] names, boolean[] vals) {
		Intent intent = new Intent(packageContext, FilterActivity.class);
		intent.putExtra(EXTRA_FILTER_NAMES, names);
		intent.putExtra(EXTRA_FILTER_VALUES, vals);
		return intent;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		
		String[] fNames = getIntent().getStringArrayExtra(EXTRA_FILTER_NAMES);
		boolean[] fVals = getIntent().getBooleanArrayExtra(EXTRA_FILTER_VALUES);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.filter_fragment_container);
		
		if (fragment == null) {
			fragment = FilterFragment.newInstance(fNames, fVals);
			fm.beginTransaction().add(R.id.filter_fragment_container, fragment).commit();
			Log.d(TAG, "Loaded the login fragment");
		}
	}
}
