package pt.isel.pd.updowncounter;

import java.io.IOException;

import android.os.AsyncTask;
import android.widget.Button;

public class MyAsyncTask extends AsyncTask<String, Integer, String>{
	
	private Button _btn;

	public MyAsyncTask(Button btn){
		_btn = btn;
	}
	
	@Override
	protected void onPreExecute(){
		_btn.setText("doing ...");
	}
	
	@Override
	protected String doInBackground(String... params) {		
		try {
			throw new IOException();
			/*for(int i = 0 ; i<10 ; ++i){
				Thread.sleep(1*1000);
				publishProgress(i);
			}*/
			
		} catch (Exception e) {
			throw new UnexpectedException();
		}
		//return params[0].toUpperCase();		
	}
	
	@Override
	protected void onPostExecute(String s){
		_btn.setText(s);
	}
	
	@Override
	protected void onProgressUpdate(Integer...integers){
		_btn.setText(integers[0].toString());
	}
}
