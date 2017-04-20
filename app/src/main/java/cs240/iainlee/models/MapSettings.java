package cs240.iainlee.models;

/**
 * Created by iain on 4/11/17.
 */

public class MapSettings {
	
	private static MapSettings SINGLETON;

	private MapSettings() {
		mMapType = "Normal";
		mMapTypePos = 0;
		mSpouseColor = "green";
		mLifeColorPos = 0;
		mFamilyColor = "green";
		mFamilyColorPos = 0;
		mLifeColor = "green";
	}

	public static MapSettings get() {
		if (SINGLETON == null) {
			SINGLETON = new MapSettings();
		}
		return SINGLETON;
	}
	
	private boolean mLifeLines;
	private CharSequence mLifeColor;
	private int mLifeColorPos;
	private boolean mFamilyLines;
	private CharSequence mFamilyColor;
	private int mFamilyColorPos;
	private boolean mSpouseLines;
	private CharSequence mSpouseColor;
	private int mSpouseColorPos;
	private CharSequence mMapType;
	private int mMapTypePos;
	private boolean mResync;
	private boolean mLogout;
	
	
	public boolean isLifeLines() {
		return mLifeLines;
	}
	
	public void setLifeLines(boolean lifeLines) {
		mLifeLines = lifeLines;
	}
	
	public boolean isFamilyLines() {
		return mFamilyLines;
	}
	
	public void setFamilyLines(boolean familyLines) {
		mFamilyLines = familyLines;
	}
	
	public boolean isSpouseLines() {
		return mSpouseLines;
	}
	
	public void setSpouseLines(boolean spouseLines) {
		mSpouseLines = spouseLines;
	}
	
	public CharSequence getMapType() {
		return mMapType;
	}
	
	public void setMapType(CharSequence mapType) {
		mMapType = mapType;
	}
	
	public boolean isResync() {
		return mResync;
	}
	
	public void setResync(boolean resync) {
		mResync = resync;
	}
	
	public boolean isLogout() {
		return mLogout;
	}
	
	public void setLogout(boolean logout) {
		mLogout = logout;
	}
	
	public CharSequence getLifeColor() {
		return mLifeColor;
	}
	
	public void setLifeColor(CharSequence lifeColor) {
		mLifeColor = lifeColor;
	}
	
	public CharSequence getFamilyColor() {
		return mFamilyColor;
	}
	
	public void setFamilyColor(CharSequence familyColor) {
		mFamilyColor = familyColor;
	}
	
	public CharSequence getSpouseColor() {
		return mSpouseColor;
	}
	
	public void setSpouseColor(CharSequence spouseColor) {
		mSpouseColor = spouseColor;
	}
	
	public boolean hasLines() {
		return (mLifeLines || mFamilyLines || mSpouseLines);
	}
	
	public int getLifeColorPos() {
		return mLifeColorPos;
	}
	
	public void setLifeColorPos(int lifeColorPos) {
		mLifeColorPos = lifeColorPos;
	}
	
	public int getFamilyColorPos() {
		return mFamilyColorPos;
	}
	
	public void setFamilyColorPos(int familyColorPos) {
		mFamilyColorPos = familyColorPos;
	}
	
	public int getSpouseColorPos() {
		return mSpouseColorPos;
	}
	
	public void setSpouseColorPos(int spouseColorPos) {
		mSpouseColorPos = spouseColorPos;
	}
	
	public int getMapTypePos() {
		return mMapTypePos;
	}
	
	public void setMapTypePos(int mapTypePos) {
		mMapTypePos = mapTypePos;
	}
	
	public static void clear() {
		SINGLETON = null;
	}
}
