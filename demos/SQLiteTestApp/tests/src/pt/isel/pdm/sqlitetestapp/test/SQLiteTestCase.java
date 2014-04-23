package pt.isel.pdm.sqlitetestapp.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.test.*;
import android.util.Log;

import static pt.isel.pdm.sqlitetestapp.test.Tracer.*;

public class SQLiteTestCase extends AndroidTestCase {

	public void test_can_query(){
		SQLiteDatabase db = new TestOpenHelper(getContext()).getReadableDatabase();
		Cursor c = db.query("students",null, null, null, null, null, null);
		int i = 0;
		int numberIx = c.getColumnIndex("_id");
		while(c.moveToNext()){
			int number = c.getInt(numberIx);
			assertEquals(i, number);
			i += 1;
		}
		c.close();				
	}	
}

class TestOpenHelper extends SQLiteOpenHelper{
	
	public TestOpenHelper(Context ctx){
		super(ctx, "test.sqlite", new TestCursorFactory(), 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table students (_id integer primary key, name text)");
		d("table created");
		db.beginTransaction();
		try{
			for(int i = 0 ; i<10000 ; ++i){
				ContentValues values = new ContentValues();
				values.put("_id", i);
				values.put("name", "name "+i);
				db.insert("students",null,values);				
			}
			d("setTransactionSucessful");
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
		d("sample data inserted");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// nothing to do		
	}
}

class TestCursorFactory implements CursorFactory{

	@Override
	public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
			String editTable, SQLiteQuery query) {
		d("newCursor");
		return new TestCursor(db, driver, editTable, query);		
	}	
}

class TestCursor extends SQLiteCursor{	
	
	private final SQLiteDatabase _db;

	public TestCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
			String editTable, SQLiteQuery query) {
		super(driver, editTable, query);
		_db = db;		
	}
	
	@Override
	public int getCount(){
		d("getCount");
	    d("  inTransaction = " + _db.inTransaction());
		int count =  super.getCount();
		d("  inTransaction = " + _db.inTransaction());
		return count;
	}
	
	@Override
	public boolean onMove(int oldPosition, int newPosition){
		boolean requiresFill = mWindow == null || newPosition < mWindow.getStartPosition() ||
                newPosition >= (mWindow.getStartPosition() + mWindow.getNumRows());
        CursorWindow window = mWindow;
        int sp = mWindow.getStartPosition();
        int gnr = mWindow.getNumRows();
        
        if(requiresFill){       
			d("onMove(%d,%d)",oldPosition, newPosition);
			d("  mWindow.getStartPosition = %d",mWindow.getStartPosition());
			d("  mWindow.getNumRows = %d",mWindow.getNumRows());			
		    d("  requiresFill = "+requiresFill);
		    d("  inTransaction = " + _db.inTransaction());
		    d("  begin super.OnMove");        
			boolean b = super.onMove(oldPosition, newPosition);
			d("  end super.OnMove");
			d("  inTransaction = " + _db.inTransaction());
		
			return b;
        }
        return true;        
	}	
}



class Tracer{
	public static void d(String fmt, Object... args){
		Log.d("SQL_LITE_TEST_CASE",String.format(fmt,args));
	}
}