package com.example.notetakingapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class NotesProvider extends ContentProvider{

    private static final String AUTHORITY = "com.example.notetakingapp.notesprovider";
    private static final String BASE_PATH = "notes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int NOTES = 1;
    private static final int NOTES_ID = 2;

     private static final UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    static{
     uriMatcher.addURI(AUTHORITY,BASE_PATH,NOTES);
     uriMatcher.addURI(AUTHORITY,BASE_PATH  +"/#",NOTES_ID);
    }

    private SQLiteDatabase database;
    @Override
    public boolean onCreate()
    {
        DBopener helper=new DBopener(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        return database.query(DBopener.TABLE_NOTES,DBopener.ALL_COLUMNS,selection,null,null,null,null,DBopener.NOTE_CREATED+" DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        long id= database.insert(DBopener.TABLE_NOTES,null,values);
        return Uri.parse(BASE_PATH+"/"+id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return  database.delete(DBopener.TABLE_NOTES,selection,selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(DBopener.TABLE_NOTES,values,selection,selectionArgs);
    }
}
