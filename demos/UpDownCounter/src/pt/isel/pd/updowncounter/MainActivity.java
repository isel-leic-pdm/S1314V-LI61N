package pt.isel.pd.updowncounter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String VALUE_KEY = "VALUE_KEY";
	
	private TextView _text;
	private Button _incBtn;
	private Button _decBtn;
	private int _value;

	private Button _taskBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_text = (TextView) findViewById(R.id.textView1);
		_incBtn = (Button) findViewById(R.id.button1);
		_decBtn = (Button) findViewById(R.id.button2);
		_taskBtn = (Button) findViewById(R.id.task_btn);
		
		if(savedInstanceState != null){
			_value = savedInstanceState.getInt(VALUE_KEY);
		}else{
			_value = 0;
		}
		
		_text.setText(Integer.toString(_value));
		
		_incBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				_value += 1;
				_text.setText(Integer.toString(_value));				
			}			
		});
		
		_decBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				_value -= 1;
				_text.setText(Integer.toString(_value));				
			}			
		});
		
		_taskBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				_taskBtn.setText("doing ...");
				new MyAsyncTask(_taskBtn).execute("done");
			}			
		});		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle state){
		state.putInt(VALUE_KEY, _value);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
