package cs240.iainlee.familymapclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MapActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.map_fragment);
		if (fragment == null) {
			fragment = FamilyMapFragment.newInstance();
			fm.beginTransaction().add(R.id.map_fragment, fragment).commit();
		}
	}
}
