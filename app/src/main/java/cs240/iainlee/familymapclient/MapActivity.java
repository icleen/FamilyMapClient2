package cs240.iainlee.familymapclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MapActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.map_fragment);
		if (fragment == null) {
			fragment = FamilyMapFragment.newInstance();
			fm.beginTransaction().add(R.id.map_fragment, fragment).commit();
		}
	}
}
