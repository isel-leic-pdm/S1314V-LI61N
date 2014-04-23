package com.example.contentproviderclientexample;

import android.os.Bundle;
import android.os.Handler;
import android.net.*;
import android.app.Activity;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {

	private static final String TAG = "CONTENT_PROVIDER_EXAMPLE"; 
	
	private ListView _lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d(TAG, "(Client) MainActivity.onCreate");
		
		_lv = (ListView) findViewById(R.id.listView1);
		final Cursor c = getContentResolver()
				.query(ToDoContract.CONTENT_URI, null, null, null, null);
		
		final SimpleCursorAdapter adapter = 
				new SimpleCursorAdapter(this, 
				R.layout.templ_todo_item, c,
				new String[]{ToDoContract.DESCRIPTION}, 
				new int[]{R.id.textView1},0);
		
		c.registerContentObserver(
			new ContentObserver(new Handler()){
				
			   public void onChange(boolean selfChange) {
			      this.onChange(selfChange, null);
			   }     
			   @Override

			   public void onChange(boolean selfChange, Uri uri) {
				   Log.d(TAG, String.format("onChange(%s,%s)",selfChange,uri));
				   adapter.getCursor().close();
				   Cursor c2 = getContentResolver().query(ToDoContract.CONTENT_URI, null, null, null, null);
				   try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
								
				}
				   adapter.changeCursor(c2);
				   c2.registerContentObserver(this);
			   }			
		});		
		
		_lv.setAdapter(adapter);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				for(;;){
					ContentValues values = new ContentValues();
					String desc = "learn loaders "+System.currentTimeMillis();
					values.put(ToDoContract.DESCRIPTION, desc);
					Uri uri = getContentResolver().insert(ToDoContract.CONTENT_URI, values);
					Log.d(TAG, String.format("inserted %s on %s",
							desc,
							uri));
					try {
						Thread.sleep(2*1000);
					} catch (InterruptedException e) {
						// retry it
					}
				}
			}			
		}).start();
		
		Log.d(TAG, "(Client) MainActivity.onCreate ended");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
