package com.example.contentproviderclientexample;

import android.net.Uri;

public final class ToDoContract {
	
	public static final String AUTHORITY = "pt.isel.pdm.todos";
	public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/todos");
	
	public static final String _ID = "_id";
	public static final String DESCRIPTION = "desc";

}
