package com.example.loaderexample;

import android.os.Bundle;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity 
	extends Activity 
	implements LoaderCallbacks<String> {
	
	private static final String TAG = "LOADER_EXAMPLE";
	private TextView _tv;
	
	private final void d(String m){
		Log.d(TAG,String.format("[%d] %s", hashCode(), m));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_tv = (TextView) findViewById(R.id.textView);
		d("onCreate");
		
		
		getLoaderManager().initLoader(0, null, this);
		
		
		
		
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener(){
			@Override public void onClick(View v) {
				_tv.setText(R.string.initial_msg);
				getLoaderManager().restartLoader(0, null, MainActivity.this);
			}			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public Loader<String> onCreateLoader(int id, Bundle args) {
		d("onCreateLoader");
		return new CustomLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<String> arg0, String arg1) {
		d("onLoadFinished");
		_tv.setText(arg1);		
	}

	@Override
	public void onLoaderReset(Loader<String> arg0) {
		d("onLoaderReset");		
	}
	
	@Override
	public void onPause(){
		super.onPause();
		d("onPause");
	}
	
	@Override
	public void onStop(){
		super.onStop();
		d("onStop");
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		d("onDestroy");
	}
}
