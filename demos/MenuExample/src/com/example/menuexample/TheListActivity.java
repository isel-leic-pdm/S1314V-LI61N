package com.example.menuexample;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TheListActivity extends Activity {

	private ListView _lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_the_list);
		
		_lv = (ListView) findViewById(R.id.listView1);
		_lv.setAdapter(new ArrayAdapter<String>(this, R.layout.templ_list_item, new String[]{
				"item 1", "item 2", "item 3"
		}));
		
		//registerForContextMenu(_lv);
		
		final ActionMode.Callback amc = new ActionMode.Callback(){

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater inflater = mode.getMenuInflater();
		        inflater.inflate(R.menu.action_context, menu);
		        return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch(item.getItemId()){
					case R.id.action_menu_item1:
						Toast.makeText(TheListActivity.this, "item 1 selected", Toast.LENGTH_SHORT).show();
						mode.finish();
			            return true;
			            
					case R.id.action_menu_item2:
						Toast.makeText(TheListActivity.this, "item 1 selected", Toast.LENGTH_SHORT).show();
						mode.finish();
			            return true; 						
				}
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				_actionMode = null;
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}			
		};
		
		_lv.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
				Log.d("MENU_EXAMPLE","onLongClick");
				if(_actionMode != null) return false;
				_actionMode = startActionMode(amc);
				return true;
			}						
		});		
	}
	
	private ActionMode _actionMode;
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,  ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_context, menu);
	}	
	
	public boolean onContextItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.list_menu_item1:
	            Toast.makeText(this, "item 1 selected", Toast.LENGTH_SHORT).show();
	            return true;
	        case R.id.list_menu_item2:
	        	Toast.makeText(this, "item 2 selected", Toast.LENGTH_SHORT).show();
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.the_list, menu);
		return true;
	}

}
