package com.example.listadapterexample;

import java.util.ArrayList;
import java.util.List;

import com.example.listadapterexample.ExampleAdapter.ViewHelper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static class ItemViewHolder{
		public final TextView name;
		public final TextView number;
		public ItemViewHolder(TextView name, TextView number){
			this.name = name;
			this.number = number;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LayoutInflater layoutInflater = (LayoutInflater)getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		
		ListView _listView = (ListView) findViewById(R.id.listView1);
		
		_listView.addFooterView(layoutInflater.inflate(R.layout.list_footer,null));
		
		
		ArrayList<Model> students = new ArrayList<Model>();
		for(int i = 0 ; i<20 ; ++i){
			students.add(new Model("name"+i,i));
		}
		_listView.setAdapter(new ExampleAdapter<ItemViewHolder,Model>(
			this, students, R.layout.item_template,
			new ExampleAdapter.ViewHolder<ItemViewHolder>() {
	
				@Override
				public ItemViewHolder build(View view) {
					return new ItemViewHolder(
							(TextView)view.findViewById(R.id.textView1),
							(TextView)view.findViewById(R.id.textView2)
							);
				}
			},
			new ExampleAdapter.Binder<ItemViewHolder,Model>() {			
				@Override
				public void bind(ItemViewHolder view, Model model) {
					view.name.setText(model.getName());
					view.number.setText(Long.toString(model.getNumber()));
				}
			},
			new ExampleAdapter.Appender<ItemViewHolder,Model>(){
				
				private boolean _working = false;
				@Override
				public void AppendTo(final ExampleAdapter<ItemViewHolder,Model> adapter) {
					if(_working == true) return;
					_working = true;
					new AsyncTask<Integer,Void,List<Model>>(){
						
						@Override
						protected List<Model> doInBackground(Integer... params) {
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// ignore it
							}
							List<Model> newElems = new ArrayList<Model>();
							for(int i = params[0].intValue() ; i<params[1].intValue() ; ++i){
								newElems.add(new Model("name-"+i,i));
							}
							return newElems;
						}				
						
						@Override
						protected void onPostExecute(List<Model> delta){
							adapter.append(delta);
							_working = false;
						}					
					}.execute(adapter.getCount(), adapter.getCount()+10);				
				}			
			}));		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
