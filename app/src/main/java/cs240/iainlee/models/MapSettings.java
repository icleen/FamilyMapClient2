package cs240.iainlee.models;

/**
 * Created by iain on 4/11/17.
 */

public class MapSettings {
	
//	private static MapSettings SINGLETON;
//
//	private MapSettings() {
//	}
//
//	public static MapSettings get() {
//		if (SINGLETON == null) {
//			SINGLETON = new MapSettings();
//		}
//		return SINGLETON;
//	}
	
	private boolean mLifeLines;
	private int mLifeColor;
	private boolean mFamilyLines;
	private int mFamilyColor;
	private boolean mSpouseLines;
	private int mSpouseColor;
	private int mMapType;
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
	
	public int getMapType() {
		return mMapType;
	}
	
	public void setMapType(int mapType) {
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
	
	public int getLifeColor() {
		return mLifeColor;
	}
	
	public void setLifeColor(int lifeColor) {
		mLifeColor = lifeColor;
	}
	
	public int getFamilyColor() {
		return mFamilyColor;
	}
	
	public void setFamilyColor(int familyColor) {
		mFamilyColor = familyColor;
	}
	
	public int getSpouseColor() {
		return mSpouseColor;
	}
	
	public void setSpouseColor(int spouseColor) {
		mSpouseColor = spouseColor;
	}
}
