package com.example.multipleapps2;

import android.os.Bundle;
import android.os.Process;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView _textView;

	private void println(String msg){
		_textView.setText(_textView.getText()+"\n"+msg);
	}
	
	private void showProcessInfo(){
		int pid = Process.myPid();
		println("pid = "+pid);
		println("uid = "+Process.myUid());
		println("tid = "+Process.myTid());
		
		ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);		
		for(RunningAppProcessInfo pi : am.getRunningAppProcesses()){
			if(pi.pid == pid){
				println("Process name = "+pi.processName);				
			}			
		}
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_textView = (TextView) findViewById(R.id.textView1);
		showProcessInfo();
		
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("pt.isel.pdm.intent.action1");
				startActivity(intent);
			}			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
