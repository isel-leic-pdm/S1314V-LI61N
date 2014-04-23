package com.example.sqliteexample;

import android.database.Cursor;
import android.database.CursorWrapper;

public class StudentCursor extends CursorWrapper{

	public StudentCursor(Cursor cursor) {
		super(cursor);
	}
	
	public Student getStudent(){
		if(isBeforeFirst() || isAfterLast()) return null;
		
		int number = this.getInt(getColumnIndex("_id"));
		String name = this.getString(getColumnIndex("name"));
		return new Student(number, name);				
	}
}
