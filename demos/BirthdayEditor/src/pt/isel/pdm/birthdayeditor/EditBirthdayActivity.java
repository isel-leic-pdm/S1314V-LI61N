package pt.isel.pdm.birthdayeditor;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

public class EditBirthdayActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editbirthday);
		
		Intent intent = getIntent();
		Uri uri = Uri.parse(intent.getStringExtra("ContactUri"));
		Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
		
		Cursor c = this.getContentResolver().query(uri, null, null, null, null);
		if(c.moveToFirst()){
			String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			String id = c.getString(c.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
			TextView tv = (TextView)this.findViewById(R.id.contactNameTextView);
			tv.setText(name);
			
			String selection = String.format("%s = '%s' and %s = %s and %s = '%s'", 
					ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
					ContactsContract.CommonDataKinds.Event.TYPE, ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY,
					ContactsContract.Data.LOOKUP_KEY, id);
			
			ContentResolver cr = this.getContentResolver();
			Cursor dcursor = cr.query(ContactsContract.Data.CONTENT_URI, null, selection, null , null);
			tv = (TextView)this.findViewById(R.id.contactBDayTextView);
			if(dcursor.moveToFirst()){
				tv.setText(dcursor.getString(
						dcursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE)));
			}else{
				tv.setText("no birthday defined");
			}
			
			
		}
		
	}	 	
}
