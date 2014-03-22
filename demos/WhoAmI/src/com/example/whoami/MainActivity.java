package com.example.whoami;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.view.Menu;
import android.widget.TextView;
import android.os.Process;

public class MainActivity extends Activity {

	private TextView _tv;	
	
	private void println(String msg){
		_tv.setText(_tv.getText()+msg+"\n");
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_tv = (TextView) this.findViewById(R.id.textView);
		_tv.setText("");
		println("hashCode = "+this.hashCode());
		int pid = Process.myPid();
		println("pid = "+pid);
		println("uid = "+Process.myUid());
		
		ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);		
		for(RunningAppProcessInfo pi : am.getRunningAppProcesses()){
			if(pi.pid == pid){
				println("Process name = "+pi.processName);				
			}			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
