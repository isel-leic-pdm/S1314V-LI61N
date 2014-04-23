package pt.isel.pdm.sqlitetestapp.test;

import java.io.File;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.test.*;

public class FirstTestCase extends AndroidTestCase {
	
	public void test_that_it_runs(){
		assertTrue(true);
	}
	
	public void test_that_it_has_a_context(){
		Context ctx = this.getContext();
		assertEquals("pt.isel.pdm.sqlitetestapp",ctx.getPackageName());
	}
	
	public void test_that_it_runs_with_the_app_user(){
		Context ctx = this.getContext();
		int pid = Process.myPid();
				
		ActivityManager am = (ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE);		
		for(RunningAppProcessInfo pi : am.getRunningAppProcesses()){
			if(pi.pid == pid){
				assertEquals("pt.isel.pdm.sqlitetestapp",pi.processName);				
			}			
		}
	}
	
	public void test_database_path_uses_app_path(){
		Context ctx = this.getContext();
		File file = ctx.getDatabasePath("db-name.sqlite");
		assertEquals("/data/data/pt.isel.pdm.sqlitetestapp/databases/db-name.sqlite",file.getAbsolutePath());		
	}
	
	public void test_that_with_a_renaming_context_the_db_file_has_a_prefix(){
		Context ctx = new RenamingDelegatingContext(getContext(), "test.");
		File file = ctx.getDatabasePath("db-name.sqlite");
		assertEquals("/data/data/pt.isel.pdm.sqlitetestapp/databases/test.db-name.sqlite",file.getAbsolutePath());		
	}
	
}
