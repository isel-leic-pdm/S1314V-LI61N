package com.example.listviewexample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ArrayList<Hashtable<String, Object>> _courses;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView lv = (ListView) findViewById(R.id.listView1);
		
		SharedPreferences prefs = getSharedPreferences("prefs",0);
		Set<String> set = prefs.getStringSet("selected", new HashSet<String>());
		
		Hashtable<String,Object> pdm = new Hashtable<String,Object>();
		pdm.put("shortName", "PDM");
		pdm.put("longName", "Programação em Dispositivos Móveis");
		pdm.put("selected", set.contains("PDM"));
		
		Hashtable<String,Object> mpd = new Hashtable<String,Object>();
		mpd.put("shortName", "MPD");
		mpd.put("longName", "Modelação e Padrões de Desenho");
		mpd.put("selected", set.contains("MPD"));
		
		_courses = 
				new ArrayList<Hashtable<String,Object>>();
		_courses.add(pdm);
		_courses.add(mpd);
		
		SimpleAdapter adapter = new SimpleAdapter(
				this, _courses,
				R.layout.item_template,				
				new String[]{
					"longName",
					"shortName",
					"selected"
				},
				new int[]{
					R.id.longNameTextView1,
					R.id.shortNameTextView1,
					R.id.selected
				}
		);
				
		lv.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_settings){
			Toast.makeText(this, "Menu selected", Toast.LENGTH_LONG).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		ListView lv = (ListView) findViewById(R.id.listView1);
		SharedPreferences prefs = getSharedPreferences("prefs",0);
		SharedPreferences.Editor editor = prefs.edit();
		Set<String> set = new HashSet<String>();
		
		SimpleAdapter adapter = (SimpleAdapter) lv.getAdapter();
		// Quizz: what is the problem with this?
		for(int i = 0 ; i<adapter.getCount() ; ++i){
			Switch s = (Switch) lv.getChildAt(i).findViewById(R.id.selected);
			if(s.isChecked()){
				set.add((String) _courses.get(i).get("shortName"));
			}
		}
		editor.putStringSet("selected", set);
		editor.commit();
	}
	

}
