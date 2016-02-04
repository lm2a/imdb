package com.imdb.and.helpers;

import android.util.Log;

public class MMLog {

	public static final String TAG = "MM";
	
	public static void v(String message) {

		if(Constants.IN_DEBUG && message != null){
			Log.v(TAG, message);
		}
	}
	
	public static void d(String message) {
		if(Constants.IN_DEBUG && message != null){
			Log.d(TAG, message);
		}
	}
	
	public static void e(String message) {
		if(Constants.IN_DEBUG && message != null){
			Log.e(TAG, message);
		}
	}
	
	public static void i(String message) {
		if(Constants.IN_DEBUG && message != null){
			Log.i(TAG, message);
		}
	}
}
