package cs240.iainlee.familymapclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.amazon.geo.mapsv2.MapFragment;

import cs240.iainlee.models.UserInfo;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginListener, FamilyMapFragment.OnLogoutListener {
	
	private static final String TAG = "mainActivity";
	public static boolean isMap = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);

		if (fragment == null && !isMap) {
			fragment = LoginFragment.newInstance();
			fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
			Log.d(TAG, "Loaded the login fragment");
		}else if (fragment == null && isMap) {
			fragment = FamilyMapFragment.newInstance(null);
			fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
			isMap = true;
			Log.d(TAG, "Loaded the login fragment");
		}
	}
	
	public void onLogin() {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = FamilyMapFragment.newInstance(null);
		fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
		isMap = true;
		Log.d(TAG, "Loaded the family map fragment");
	}
	
	public void onLogout() {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = LoginFragment.newInstance();
		fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
		isMap = false;
		Log.d(TAG, "Loaded the login fragment");
	}
}
