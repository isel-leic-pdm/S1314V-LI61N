package pt.isel.pdm.packageinspector;

import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	public void d(String msg){
		Log.d("PACKAGE_INSPECTOR",msg);
	}	
	
	private void showPackages(){
		PackageManager pm = getPackageManager();
		List<ResolveInfo> ris = pm.queryIntentActivities(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345")),0);
		d("resolve info");
		for(ResolveInfo ri : ris){
			d(String.format("Activity: %s in %s", ri.activityInfo.name, ri.activityInfo.packageName));
			d(String.format("flags: %x, launch mode: %x", ri.activityInfo.flags,ri.activityInfo.launchMode));
		}		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		showPackages();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
