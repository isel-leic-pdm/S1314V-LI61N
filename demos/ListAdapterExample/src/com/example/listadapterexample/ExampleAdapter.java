package com.example.listadapterexample;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

// Adapts to List<T>
public class ExampleAdapter<V,T> implements ListAdapter{
	
	public static class ViewHelper{
		
		private View _view;

		public ViewHelper(View view){
			_view = view;
		}
		
		public TextView TextViewFor(int id){
			return (TextView) _view.findViewById(id);
		}
	}
	
	public static interface ViewHolder<V>{
		public V build(View view);
	}
	
	public static interface Binder<V,T>{		
		public void bind(V view, T model);		
	}
	
	public static interface Appender<V,T>{
		public void AppendTo(ExampleAdapter<V,T> adapter);
	}
	
	public void append(List<T> newElems){
		_elems.addAll(newElems);
		notifyChange();
	}
	
	private void notifyChange(){
		for(DataSetObserver o : _observers){
			o.onChanged();
		}
	}
	
	private final Set<DataSetObserver> _observers = new HashSet<DataSetObserver>();	
	

	private final List<T> _elems;
	private final int _viewLayoutId;
	private final LayoutInflater _layoutInflater;
	private final Binder<V,T> _binder;
	private final Appender<V,T> _appender;
	private final ViewHolder<V> _viewHolder;
	
	public ExampleAdapter(Context context, List<T> elems, int viewLayoutId,
			ViewHolder<V> viewHolder,
			Binder<V,T> binder, 
			Appender<V,T> appender){
		_layoutInflater = (LayoutInflater)context.getSystemService
	      (Context.LAYOUT_INFLATER_SERVICE);		
		_elems = elems;
		_viewLayoutId = viewLayoutId;
		_viewHolder = viewHolder;
		_binder = binder;
		_appender = appender;
	}

	@Override
	public int getCount() {
		d("getCount");
		return _elems.size();
	}

	@Override
	public Object getItem(int position) {
		d("getItem %s",position);
		return _elems.get(position);
	}

	@Override
	public long getItemId(int position) {
		d("getItemId %d",position);
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		d("getItemViewType %d",position);
		return 0;
	}

	private int _nOfViews = 0;
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		d("getView %d %s",position, convertView);
		
		if(convertView == null){
			_nOfViews += 1;
			convertView = _layoutInflater.inflate(_viewLayoutId, null);
			convertView.setTag(_viewHolder.build(convertView));
		}
		_binder.bind((V) convertView.getTag(), _elems.get(position));
		
		d("nOfViews = %d",_nOfViews);
		
		if(position > _elems.size() - 10){
			_appender.AppendTo(this);
		}
		
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		d("getViewTypeCount");
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		d("hasStableIds");
		return false;
	}

	@Override
	public boolean isEmpty() {
		d("isEmpty");
		return _elems.size() != 0;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		d("registerDataSetObserver");
		_observers.add(observer);		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		d("unregisterDataSetObserver");
		_observers.remove(observer);		
	}

	@Override
	public boolean areAllItemsEnabled() {
		d("areAllItemsEnabled");
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		d("isEnabled");
		return true;
	}
	
	private static void d(String format, Object... prms){
		Log.d("EXAMPLE_ADAPTER",String.format(format,prms));
	}
}
