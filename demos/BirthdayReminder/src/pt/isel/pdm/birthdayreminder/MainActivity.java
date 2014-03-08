package pt.isel.pdm.birthdayreminder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private static final String TAG = "BirthdayReminder";

	private static void d(String msg) {
		Log.d(TAG, msg);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ArrayList<HashMap<String, String>> contacts = getContactInfoWithNearbyBirthday();
		bindValuesToListView(contacts);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private ArrayList<HashMap<String, String>> getContactInfoWithNearbyBirthday() {

		ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();

		Uri contactsUri = ContactsContract.Data.CONTENT_URI;		
		String eventContentType = ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE;
		String mimeTypeCol = ContactsContract.Data.MIMETYPE;
		String eventTypeCol = ContactsContract.CommonDataKinds.Event.TYPE;
		int birthdayType = ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;
		
		d("contactsUri = " + contactsUri);
		d("eventContentType = " + eventContentType);		
		d("mimetypeCol = " + mimeTypeCol);		
		d("eventTypeCol = " + eventTypeCol);		

		String selection = String.format("%s = '%s' and %s = %s", 
				mimeTypeCol, eventContentType, 
				eventTypeCol, birthdayType);

		ContentResolver cr = this.getContentResolver();
		Cursor cursor = cr.query(contactsUri, null, selection, null, null);

		try {

			int idIx = cursor
					.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);
			int nameIx = cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			int birthdayIx = cursor
					.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE);
			int photoUriIx = cursor
					.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);

			DateHandler dateHandler = new DateHandler();			
			while (cursor.moveToNext()) {
				try {
					Date bday = dateHandler.parse(cursor.getString(birthdayIx));
					if(!dateHandler.shouldShow(bday)){
						continue;
					}					

					String lkey = cursor.getString(idIx);
					String name = cursor.getString(nameIx);
					String bdayString = dateHandler.format(bday);
					String photoUri = cursor.getString(photoUriIx);

					HashMap<String, String> map = new HashMap<String, String>();
					map.put(NAME_KEY, name);
					map.put(BDAY_KEY, bdayString);
					map.put(PHOTO_URI_KEY, photoUri);
					Uri.Builder builder = ContactsContract.Contacts.CONTENT_LOOKUP_URI
							.buildUpon();
					builder.appendPath(lkey);
					map.put(CONTACT_URI_KEY,builder.build().toString());
					values.add(map);
					
					d("name = " + name + ", id = " + lkey + ", birthday = "
							+ bdayString + ", photoUri = " + photoUri);
				} catch (ParseException e) {
					Log.w(TAG, "parsing date, ignoring this entry", e);
				}
			}
		} finally {
			cursor.close();
		}
		return values;
	}
	
	// Keys for the values dictionary
	private static final String NAME_KEY = "name";
	private static final String BDAY_KEY = "bday";
	private static final String PHOTO_URI_KEY = "photoUri";
	private static final String CONTACT_URI_KEY = "contactUri";
	
	public void bindValuesToListView(final ArrayList<HashMap<String, String>> values) {

		ListView lv = (ListView) this.findViewById(R.id.listView1);

		SimpleAdapter adapter = new SimpleAdapter(this, values,
				R.layout.templ_item1,
				new String[] { NAME_KEY, BDAY_KEY, PHOTO_URI_KEY }, new int[] {
						R.id.itemName, R.id.itemBd, R.id.photoView }) {

			// TODO Not sure if this is the best way to handle it
			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				View v = super.getView(position, convertView, parent);
				v.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						Uri uri = Uri.parse(values.get(position).get(CONTACT_URI_KEY));						
						intent.setData(uri);
						MainActivity.this.startActivity(intent);
					}
				});	
				return v;
			}
		};
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				Uri uri = Uri.parse(values.get(position).get(CONTACT_URI_KEY));						
				intent.setData(uri);
				MainActivity.this.startActivity(intent);				
			}
			
		});
	}
}
