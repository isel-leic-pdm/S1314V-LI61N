package pt.isel.pdm.contentproviderimplexample;

import android.util.Log;

public class MyLog {

	private static final String TAG = "CONTENT_PROVIDER_EXAMPLE";
	
	public static void d(String fmt, Object... args){
		Log.d(TAG, String.format(fmt, args));
	}		
}
