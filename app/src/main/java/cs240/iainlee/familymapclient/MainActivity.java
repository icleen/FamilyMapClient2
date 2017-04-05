package cs240.iainlee.familymapclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends FragmentActivity implements LoginFragment.OnLoginListener, FamilyMapFragment.OnLogoutListener {
	
	private static final String TAG = "mainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);

		if (fragment == null) {
			fragment = LoginFragment.newInstance();
			fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
			Log.d(TAG, "Loaded the login fragment");
		}
	}
	
	public void onLogin() {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = FamilyMapFragment.newInstance();
		fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
		Log.d(TAG, "Loaded the family map fragment");
	}
	
	public void onLogout() {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = LoginFragment.newInstance();
		fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
		Log.d(TAG, "Loaded the login fragment");
	}
}
