package pt.isel.pdm.contentproviderimplexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "todos.db";
	public static final String TABLE_NAME = "todos";
		
	public DbHelper(Context context) {
		super(context, DB_NAME, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(String.format("create table %s (%s integer primary key autoincrement, %s text)", 
				TABLE_NAME, ToDoContract._ID, ToDoContract.DESCRIPTION));
		ContentValues values = new ContentValues();
		values.put(ToDoContract.DESCRIPTION, "learn Android");		
		db.insert(TABLE_NAME, null, values);
		values.put(ToDoContract.DESCRIPTION, "learn content providers");		
		db.insert(TABLE_NAME, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// nothing to do		
	}
}
