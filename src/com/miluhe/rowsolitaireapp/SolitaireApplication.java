package com.miluhe.rowsolitaireapp;

import android.app.Application;
import android.content.Context;

public class SolitaireApplication extends Application {
	private static Context mContext;
	
	@Override
	public void onCreate() {
		//获取Context
		mContext = getApplicationContext();
	}
	
	//返回
	public static Context getContextObject(){
		return mContext;
	}
}
