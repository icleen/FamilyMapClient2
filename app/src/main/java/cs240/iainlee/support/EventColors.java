package cs240.iainlee.support;

import android.graphics.Color;
import android.util.Log;

import java.util.HashMap;
import java.util.Random;

import cs240.iainlee.models.Event;

/**
 * Created by iain on 4/19/17.
 */

public class EventColors {
	
	private static final String TAG = "EventColors";
	
	private HashMap<String, Float> mEventColors = new HashMap<>();
	
	public EventColors() {
	}
	
	public int getColor(CharSequence color) {
		if (color.equals("green")) {
			return Color.GREEN;
		}else if (color.equals("red")) {
			return Color.RED;
		}else if (color.equals("blue")) {
			return Color.BLUE;
		}else {
			return Color.GRAY;
		}
	}
	
	public float eventColor(Event event) {
		if (mEventColors.containsKey(event.getEventType())) {
			return mEventColors.get(event.getEventType());
		}
		Random ran = new Random();
		float color = ran.nextInt() % 35;
		if (color < 0) color *= -1;
		color *= 10;
		Log.d(TAG, "color is: " + color);
		if (color < 0 || color >= 360) {
			Log.e(TAG, "the color is out of range" + color);
		}
		while (mEventColors.containsValue(color)) {
			color = ran.nextInt() % 35;
			if (color < 0) color *= -1;
			color *= 10;
		}
		mEventColors.put(event.getEventType(), color);
		return color;
	}
	
}
