package pt.isel.pdm.contentproviderimplexample;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ToDoContentProvider extends ContentProvider {
	
	private DbHelper _dbHelper;
	
	private static final UriMatcher _uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	private static final int TODOS_URI = 1;
	private static final int TODOS_ID_URI = 2;
	
	static{
		_uriMatcher.addURI(ToDoContract.AUTHORITY, "/todos", TODOS_URI);
		_uriMatcher.addURI(ToDoContract.AUTHORITY, "/todos/#", TODOS_ID_URI);
	}

	@Override
	public boolean onCreate() {
		MyLog.d("ToDoContentProvider.onCreate");
		_dbHelper = new DbHelper(getContext());
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, 
			String[] projection, 
			String selection, String[] selectionArgs, 
			String sortOrder) {
		
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		Cursor c;
		switch(_uriMatcher.match(uri)){
			case TODOS_URI:		
				c =  db.query(
						DbHelper.TABLE_NAME, 
						projection, selection, selectionArgs, 
						null, null, sortOrder, null );
				
				c.setNotificationUri(getContext().getContentResolver(), ToDoContract.CONTENT_URI);
				return c;
				
			case TODOS_ID_URI:
				long id = ContentUris.parseId(uri);
				c = db.query(DbHelper.TABLE_NAME, projection, 
						String.format("_id = ? and (%s)", selection),
						append(id,selectionArgs), 
						null, null, sortOrder, null );
				c.setNotificationUri(getContext().getContentResolver(), uri);
				return c;
			
			default:
				throw new IllegalArgumentException("unsupported uri: " + uri);
		}				
	}
	
	private String[] append(long id, String[] args){
		if(args == null){
			return new String[]{Long.toString(id)};
		}
		String[] res = new String[args.length+1];
		res[0] = Long.toString(id);
		System.arraycopy(args,  0, res, 1, args.length);
		return res;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = _dbHelper.getWritableDatabase()
			.insert(DbHelper.TABLE_NAME, null, values);
		
		getContext().getContentResolver().notifyChange(uri, null);
		return ContentUris.withAppendedId(ToDoContract.CONTENT_URI, id);
	}

	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
