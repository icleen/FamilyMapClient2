package cs240.iainlee.models;

import java.util.ArrayList;

/**
 * Created by iain on 4/11/17.
 */

public class MapFilters {
	
	private static ArrayList<MapFilters> SINGLETON = new ArrayList<>();

	private MapFilters() {
		mBirth = true;
		mBaptism = true;
		mMarriage = true;
		mDeath = true;
		mFatherSide = true;
		mMotherSide = true;
		mMaleEvents = true;
		mFemaleEvents = true;
	}

	public static MapFilters get(int index) {
		if (SINGLETON.isEmpty()) {
			MapFilters temp = new MapFilters();
			SINGLETON.add(temp);
			return temp;
		}
		if (index > SINGLETON.size() - 1 || index < 0) {
			return null;
		}
		return SINGLETON.get(index);
	}
	
	public static MapFilters get() {
		MapFilters temp = new MapFilters();
		SINGLETON.add(temp);
		return temp;
	}
	
	public static int size() {
		if (SINGLETON == null) {
			return 0;
		}
		return SINGLETON.size();
	}
//	filters for types of events
	private boolean mBirth;
	private boolean mBaptism;
	private boolean mMarriage;
	private boolean mDeath;
//	filters for side of family
	private boolean mFatherSide;
	private boolean mMotherSide;
//	filters for gender
	private boolean mMaleEvents;
	private boolean mFemaleEvents;
	
	public boolean isBirth() {
		return mBirth;
	}
	
	public void setBirth(boolean birth) {
		mBirth = birth;
	}
	
	public boolean isBaptism() {
		return mBaptism;
	}
	
	public void setBaptism(boolean baptism) {
		mBaptism = baptism;
	}
	
	public boolean isMarriage() {
		return mMarriage;
	}
	
	public void setMarriage(boolean marriage) {
		mMarriage = marriage;
	}
	
	public boolean isDeath() {
		return mDeath;
	}
	
	public void setDeath(boolean death) {
		mDeath = death;
	}
	
	public boolean isFatherSide() {
		return mFatherSide;
	}
	
	public void setFatherSide(boolean fatherSide) {
		mFatherSide = fatherSide;
	}
	
	public boolean isMotherSide() {
		return mMotherSide;
	}
	
	public void setMotherSide(boolean motherSide) {
		mMotherSide = motherSide;
	}
	
	public boolean isMaleEvents() {
		return mMaleEvents;
	}
	
	public void setMaleEvents(boolean maleEvents) {
		mMaleEvents = maleEvents;
	}
	
	public boolean isFemaleEvents() {
		return mFemaleEvents;
	}
	
	public void setFemaleEvents(boolean femaleEvents) {
		mFemaleEvents = femaleEvents;
	}
}
