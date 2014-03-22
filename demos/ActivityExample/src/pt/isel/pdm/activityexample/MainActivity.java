package pt.isel.pdm.activityexample;

import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private int _count;
	
	private void d(String msg){
		Log.d("ACTIVITY_EXAMPLE_MAIN", String.format("[count = %s]: %s",Integer.toString(_count),msg));
	}
	
	private void showStack(){
		ActivityManager man = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = man.getRunningTasks(100);
		d("Running tasks = "+tasks.size());
		for(ActivityManager.RunningTaskInfo task : tasks){
			if(task.id == getTaskId()){
				d("No of activities on current task = "+task.numActivities);				
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MainActivity.class);
				intent.putExtra("count", _count);
				startActivity(intent);				
			}			
		});
		
		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
						//Uri.parse("geo:38.7549028,-9.1141339?z=18"));
						Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
				startActivity(intent);				
			}
		});
		
		Button btn3 = (Button) findViewById(R.id.button3);
		btn3.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
						Uri.parse("http://www.isel.pt"));
				startActivity(intent);				
			}
		});
		
		Intent intent = getIntent();
		if(intent.getAction() == Intent.ACTION_MAIN){
			d("intent: CATEGORY_LAUNCHER.ACTION_MAIN");			
			_count = 0;
		}else {			
			_count = intent.getIntExtra("count", -1);
			_count += 1;
			d("intent action: "+intent.getAction() != null ? intent.getAction() : "no action");
		}
		
		d("onCreate");
		
		TextView tv = (TextView) findViewById(R.id.countTextBox);
		tv.setText(Integer.toString(_count));
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		d("onStart");
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		d("onResume");
		showStack();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		d("onPause");
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		d("onStop");
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		d("onDestroy");
	}
	
	@Override
	protected void onRestart(){
		super.onRestart();
		d("onRestart");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle b){
		super.onSaveInstanceState(b);
		d("onSaveInstanceState");
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle b){
		super.onRestoreInstanceState(b);
		d("onRestoreInstanceState");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
