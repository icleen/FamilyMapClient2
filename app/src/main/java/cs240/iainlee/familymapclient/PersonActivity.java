package cs240.iainlee.familymapclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PersonActivity extends AppCompatActivity {
	
	private static final String TAG = "personActivity";
	
	private Button mBackButton;
	private Button mTopButton;
	private Button mMapButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		
		mBackButton = (Button) findViewById(R.id.person_back);
		mBackButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onReturnToParent();
			}
		});
		
		mTopButton = (Button) findViewById(R.id.person_top);
		mTopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onToTop();
			}
		});
		
		mMapButton = (Button) findViewById(R.id.person_map);
		mMapButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onGoToMap();
			}
		});
		
	}
	
	private void onReturnToParent() {
		Log.d(TAG, "to parent");
	}
	
	private void onToTop() {
		Log.d(TAG, "to top");
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
