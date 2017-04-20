package cs240.iainlee.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SearchActivity extends AppCompatActivity {
	
	private static final String TAG = "SearchActivity";
	
	public static Intent newIntent(Context context) {
		Intent intent = new Intent(context, SearchActivity.class);
		return intent;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.search_fragment_container);
		
		if (fragment == null) {
			fragment = SearchFragment.newInstance();
			fm.beginTransaction().add(R.id.search_fragment_container, fragment).commit();
			Log.d(TAG, "Loaded the search fragment");
		}
		
	}
}
