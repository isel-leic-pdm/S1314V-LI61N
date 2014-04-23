package com.example.sqliteexample;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {

	private ListView _lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ExampleOpenHelper h = new ExampleOpenHelper(this);
						
		Cursor c = h.queryAll();
		if(!c.moveToNext()){
			h.insertStudent(12345, "Alice");
			h.insertStudent(12346, "Bob");
		}		
				
		_lv = (ListView) findViewById(R.id.listView1);
		_lv.setAdapter(new SimpleCursorAdapter(this, R.layout.templ_student_item, 
				h.queryAll(),
				new String[]{"_id","name"},
				new int[]{R.id.textView1, R.id.textView2},0));		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
