package com.example.loaderexample;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

public class CustomLoader extends AsyncTaskLoader<String>{
	
	private String _data;
	
	public CustomLoader(Context context) {
		super(context);		 
	}

	@Override
	public void onStartLoading(){
		Log.d("LOADER_EXAMPLE","CustomLoader: onStartLoading");
		if(_data != null){
			deliverResult(_data);
		}else{
			forceLoad();
		}
	}
	
	@Override
	protected boolean onCancelLoad(){
		Log.d("LOADER_EXAMPLE","CustomLoader: onCancelLoad");
		return false;
	}
	
	@Override
	protected void onStopLoading(){
		Log.d("LOADER_EXAMPLE","CustomLoader: onStopLoading");		
	}
	
	@Override
	public void deliverResult(String data){
		Log.d("LOADER_EXAMPLE","CustomLoader: deliverResult");
		_data = data;
		super.deliverResult(data);
	}
	
	@Override
	public String loadInBackground() {
		try {
			Log.d("LOADER_EXAMPLE","CustomLoader: loadInBackground");
			Thread.sleep(10*1000);
			Log.d("LOADER_EXAMPLE","CustomLoader: loadInBackground ended");
			return "load completed";
		} catch (InterruptedException e) {
			return "interrupted";
		}
	}
}
