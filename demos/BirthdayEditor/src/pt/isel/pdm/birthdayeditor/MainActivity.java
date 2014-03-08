package pt.isel.pdm.birthdayeditor;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final static int PICK_CONTACT_REQUEST = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn = (Button) findViewById(R.id.chooseContactBtn);
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
			}			
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {
	    	Uri contactUri = data.getData();
	    	//Toast.makeText(this, contactUri.toString(), Toast.LENGTH_LONG).show();
	    	Intent intent = new Intent(this, EditBirthdayActivity.class);
	    	intent.putExtra("ContactUri",contactUri.toString());
	    	startActivity(intent);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
