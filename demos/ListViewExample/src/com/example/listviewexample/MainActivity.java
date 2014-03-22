package com.example.listviewexample;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView lv = (ListView) findViewById(R.id.listView1);
		
		Hashtable<String,String> pdm = new Hashtable<String,String>();
		pdm.put("shortName", "PDM");
		pdm.put("longName", "Programação em Dispositivos Móveis");
		
		Hashtable<String,String> mpd = new Hashtable<String,String>();
		mpd.put("shortName", "MPD");
		mpd.put("longName", "Modelação e Padrões de Desenho");
		
		List<Hashtable<String,String>> courses = 
				new ArrayList<Hashtable<String,String>>();
		courses.add(pdm);
		courses.add(mpd);
		
		SimpleAdapter adapter = new SimpleAdapter(
				this, courses,
				R.layout.item_template,				
				new String[]{
					"longName",
					"shortName",
				},
				new int[]{
					R.id.longNameTextView1,
					R.id.shortNameTextView1,					
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

}
