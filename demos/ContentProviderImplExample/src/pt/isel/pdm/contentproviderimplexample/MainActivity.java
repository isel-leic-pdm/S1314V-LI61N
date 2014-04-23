package pt.isel.pdm.contentproviderimplexample;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {

	private ListView _lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		MyLog.d("MainActivity.onCreate");
		
		_lv = (ListView) findViewById(R.id.listView1);
		Cursor c = getContentResolver().query(ToDoContract.CONTENT_URI, null, null, null, null);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.templ_todo_item, c,
				new String[]{ToDoContract.DESCRIPTION}, new int[]{R.id.textView1},0);
		_lv.setAdapter(adapter); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
