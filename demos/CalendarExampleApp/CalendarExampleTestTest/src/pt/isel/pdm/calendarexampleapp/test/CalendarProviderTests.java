package pt.isel.pdm.calendarexampleapp.test;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.test.*;
import android.util.Log;

public class CalendarProviderTests extends AndroidTestCase{

	public void test_can_list_calendars(){
		ContentResolver cr = getContext().getContentResolver();
		Uri calendarsUri = CalendarContract.Calendars.CONTENT_URI;
		Cursor c = cr.query(calendarsUri, null, null, null, null);
		int idIdx = c.getColumnIndex(CalendarContract.Calendars._ID);
		int accountNameIdx = c.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME);
		int displayNameIdx = c.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME);
		while(c.moveToNext()){
			int id = c.getInt(idIdx);
			String accountName = c.getString(accountNameIdx);
			String displayName = c.getString(displayNameIdx);
			d("%d, %s, %s",id, accountName, displayName);
		}
	}
	
	public void test_can_list_all_events(){
		d("test_can_list_all_event");
		ContentResolver cr = getContext().getContentResolver();
		Uri eventsUri = CalendarContract.Events.CONTENT_URI;
		Cursor c = cr.query(eventsUri, null, null, null, null);
		int idIdx = c.getColumnIndex(CalendarContract.Events._ID);
		int titleIdx = c.getColumnIndex(CalendarContract.Events.TITLE);
		int dtStartIdx = c.getColumnIndex(CalendarContract.Events.DTSTART);
		int dtEndIdx = c.getColumnIndex(CalendarContract.Events.DTEND);
		int locationIdx = c.getColumnIndex(CalendarContract.Events.EVENT_LOCATION);
		while(c.moveToNext()){
			int id = c.getInt(idIdx);
			String title = c.getString(titleIdx);
			String start = c.getString(dtStartIdx);
			String end = c.getString(dtEndIdx);
			String location = c.getString(locationIdx);
			d("%d, %s, %s, %s, %s",id, title, start, end, location);
		}
	}
	
	public void test_can_list_one_event(){
		d("test_can_list_one_event");
		ContentResolver cr = getContext().getContentResolver();
		Uri eventsUri = CalendarContract.Events.CONTENT_URI;
		Cursor c = cr.query(ContentUris.withAppendedId(eventsUri, 4), null, null, null, null);
		int idIdx = c.getColumnIndex(CalendarContract.Events._ID);
		int titleIdx = c.getColumnIndex(CalendarContract.Events.TITLE);
		int dtStartIdx = c.getColumnIndex(CalendarContract.Events.DTSTART);
		int dtEndIdx = c.getColumnIndex(CalendarContract.Events.DTEND);
		int locationIdx = c.getColumnIndex(CalendarContract.Events.EVENT_LOCATION);
		while(c.moveToNext()){
			int id = c.getInt(idIdx);
			String title = c.getString(titleIdx);
			String start = c.getString(dtStartIdx);
			String end = c.getString(dtEndIdx);
			String location = c.getString(locationIdx);
			d("%d, %s, %s, %s, %s",id, title, start, end, location);
		}
	}
	
	public void test_can_list_all_reminders_for_one_event(){
		d("test_can_list_all_reminders_for_one_event");
		ContentResolver cr = getContext().getContentResolver();
		Uri remindersUri = CalendarContract.Reminders.CONTENT_URI;
		Cursor c = cr.query(remindersUri, 
				null, 
				CalendarContract.Reminders.EVENT_ID+" = ?", new String[]{"1"}, 
				null);
		int idIdx = c.getColumnIndex(CalendarContract.Reminders._ID);
		int eventIdIdx = c.getColumnIndex(CalendarContract.Reminders.EVENT_ID);
		int minutesIdx = c.getColumnIndex(CalendarContract.Reminders.MINUTES);
		int methodIdx = c.getColumnIndex(CalendarContract.Reminders.METHOD);		
		while(c.moveToNext()){
			int id = c.getInt(idIdx);
			int eventId = c.getInt(eventIdIdx);
			int minutes = c.getInt(minutesIdx);
			int method = c.getInt(methodIdx);
			d("%d, %d, %d, %d",id, eventId, minutes, method);
		}
	}
	
	public void test_get_type(){
		d("test_can_list_one_event");
		ContentResolver cr = getContext().getContentResolver();
		assertEquals("vnd.android.cursor.dir/event",cr.getType(CalendarContract.Events.CONTENT_URI));
		assertEquals("vnd.android.cursor.item/event",cr.getType(
				ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, 1)));
	}
	
	
	private static void d(String fmt, Object ... args){
		Log.d("CALENDAR_PROVIDER_TESTS", String.format(fmt, args));
	}
	
}
