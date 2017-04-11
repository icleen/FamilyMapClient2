package cs240.iainlee.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class FilterActivity extends FragmentActivity {
	
	private static final String TAG = "FilterActivity";
	private static final String EXTRA_FILTER_INDEX = "key to the filterIndex";
	
	public static Intent newIntent(Context packageContext, int filterIndex) {
		Intent intent = new Intent(packageContext, FilterActivity.class);
		intent.putExtra(EXTRA_FILTER_INDEX, filterIndex);
		return intent;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		
		int filterIndex = getIntent().getIntExtra(EXTRA_FILTER_INDEX, -1);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.filter_fragment_container);
		
		if (fragment == null) {
			fragment = FilterFragment.newInstance(filterIndex);
			fm.beginTransaction().add(R.id.filter_fragment_container, fragment).commit();
			Log.d(TAG, "Loaded the login fragment");
		}
	}
}
