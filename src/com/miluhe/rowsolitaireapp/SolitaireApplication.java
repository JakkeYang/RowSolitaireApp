package com.miluhe.rowsolitaireapp;

import android.app.Application;
import android.content.Context;

public class SolitaireApplication extends Application {
	public enum TStageMarker {
		EStageStart,
		EStagePokerTable,
		EStageResult
	};
	
	private static Context mContext;
	private static TStageMarker mStageMarker = TStageMarker.EStageStart;
	
	@Override
	public void onCreate() {
		// get context
		mContext = getApplicationContext();
	}
	
	// return context 
	public static Context getContextObject(){
		return mContext;
	}
	
	public static void setStageMarker(TStageMarker marker) {
		mStageMarker = marker;
	}
	
	public static TStageMarker getStageMarker() {
		return mStageMarker;
	}
}
