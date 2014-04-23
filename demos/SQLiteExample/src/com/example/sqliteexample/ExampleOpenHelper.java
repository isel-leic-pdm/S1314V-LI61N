package com.example.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExampleOpenHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "students.sqlite";
	private static final int VERSION = 1;
	
	
	public ExampleOpenHelper(Context ctx){
		super(ctx, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table students (_id integer primary key, name text)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// nothing to do		
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// nothing to do
	}
	
	public void insertStudent(int number, String name){
		ContentValues cv = new ContentValues();
		cv.put("_id", number);
		cv.put("name", name);
		getWritableDatabase().insert("students", null, cv);		
	}
	
	public StudentCursor queryAll(){
		Cursor wrapped = getReadableDatabase()
				.query("students",
                null, null, null, null, null, null);
		return new StudentCursor(wrapped);
	}
}
