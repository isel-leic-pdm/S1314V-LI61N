package com.example.menuexample;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		
			case R.id.action_settings:
				Intent intent = new Intent(this, TheSettingsActivity.class);
				startActivity(intent);
				return true;
				
			case R.id.action_list:
				Intent intent2 = new Intent(this, TheListActivity.class);
				startActivity(intent2);
				return true;
		
			default:
				return super.onOptionsItemSelected(item);
		}		
	}
}
