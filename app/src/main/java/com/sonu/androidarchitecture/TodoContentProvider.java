package com.sonu.androidarchitecture;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sonu.androidarchitecture.db.ToDoListDbAdapter;

public class TodoContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.sonu.androidarchitecutre";

    public static final String PATH_TODO_LIST = "TODO_LIST";
    public static final String PATH_TODO_PLACE = "TODO_LIST_FORM_PLACE";
    public static final String PATH_TODO_COUNT = "TODO_COUNT";


    //you get a request from content resolver for the following uris
    public static final Uri CONTENT_URI_1 = Uri.parse("content://" + AUTHORITY + "/" + PATH_TODO_LIST);
    public static final Uri CONTENT_URI_2 = Uri.parse("content://" + AUTHORITY + "/" + PATH_TODO_PLACE);
    public static final Uri CONTENT_URI_3 = Uri.parse("content://" + AUTHORITY + "/" + PATH_TODO_COUNT);

    public static final int TODO_LIST = 1;
    public static final int TODO_FROM_SPECIFIC_PLACE = 2;
    public static final int TODO_COUNT = 3;

    private ToDoListDbAdapter adapter;

    public static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, PATH_TODO_LIST, TODO_LIST);
        MATCHER.addURI(AUTHORITY, PATH_TODO_PLACE, TODO_FROM_SPECIFIC_PLACE);
        MATCHER.addURI(AUTHORITY, PATH_TODO_COUNT, TODO_COUNT);
    }

    @Override
    public boolean onCreate() {
        adapter = ToDoListDbAdapter.getAdapter(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        switch (MATCHER.match(uri)) {
            case TODO_LIST:
                cursor = adapter.getCursorForAllToDodos();
                break;
            case TODO_FROM_SPECIFIC_PLACE:
                cursor = adapter.getCursorForSpecificPlace(selectionArgs[0]);
                break;
            case TODO_COUNT:
                cursor = adapter.getCount();
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) throws UnsupportedOperationException {

        Uri resultUri = null;
        switch (MATCHER.match(uri)) {
            case TODO_LIST:
                resultUri = insertToDo(uri, values);
                break;
            default:
                new UnsupportedOperationException("insert operation is not supported");
        }
        return resultUri;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) throws UnsupportedOperationException {
        int deleteCount = -1;


        switch (MATCHER.match(uri)) {
            case TODO_LIST:
                deleteCount = delete(selection, selectionArgs);
                break;
            default:
                new UnsupportedOperationException("delete operation is not supported");
        }
        return deleteCount;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) throws UnsupportedOperationException {
        int updateCount = -1;


        switch (MATCHER.match(uri)) {
            case TODO_LIST:
                updateCount = update(values, selection, selectionArgs);
                break;
            default:
                new UnsupportedOperationException("update operation is not supported");
        }
        return updateCount;
    }

    private Uri insertToDo(Uri uri, ContentValues values) {
        long id = adapter.insert(values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse("content://" + AUTHORITY + "/" + PATH_TODO_LIST + "/" + id);
    }

    private int delete(String selection, String[] selectionArgs) {
        return adapter.delete(selection, selectionArgs);
    }

    private int update(ContentValues values, String where, String[] whereArgs) {
        return adapter.update(values, where, whereArgs);
    }
}
