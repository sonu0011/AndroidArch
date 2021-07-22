package com.sonu.androidarchitecture.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.sonu.androidarchitecture.model.ToDo;

import java.util.ArrayList;
import java.util.List;

public class ToDoListDbAdapter {

    private static final String TAG = "ToDoListDbAdapter";

    private static final String DB_NAME = "todolist.db";
    private static final int DB_VERSION = 2;

    private static final String TABLE_TODO = "table_todo";
    private static final String COLUMN_TODO_ID = "task_id";
    private static final String COLUMN_TODO = "todo";
    private static final String COLUMN_PLACE = "task_place";

    private static String CREATE_TABLE_TODO = "CREATE TABLE " + TABLE_TODO + "(" + COLUMN_TODO_ID + " INTEGER PRIMARY KEY, " + COLUMN_TODO + " TEXT NOT NULL," + COLUMN_PLACE + " TEXT NOT NULL" + ")";

    private Context context;

    private SQLiteDatabase database;

    private static ToDoListDbAdapter toDoListDbAdapter;

    private ToDoListDbAdapter(Context context) {
        this.context = context;
        database = new ToDoListDBHelper(this.context, DB_NAME, null, DB_VERSION).getWritableDatabase();
    }

    public static ToDoListDbAdapter getAdapter(Context context) {

        if (toDoListDbAdapter == null) {
            toDoListDbAdapter = new ToDoListDbAdapter(context);
        }
        return toDoListDbAdapter;
    }


    public boolean insert(String item, String place) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TODO, item);
        contentValues.put(COLUMN_PLACE, place);
        Log.d(TAG, "insert: " + Thread.currentThread().getName());
        return database.insert(TABLE_TODO, null, contentValues) > 0;

    }

    public boolean update(int id, String newItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TODO, newItem);
        return database.update(TABLE_TODO, contentValues, COLUMN_TODO_ID + "=" + id, null) > 0;
    }

    //used by content provider
    public int update(ContentValues values, String where, String[] whereValues) {
        return database.update(TABLE_TODO, values, where, whereValues);
    }

    //used by content provider
    public long insert(ContentValues values) {
        return database.insert(TABLE_TODO, null, values);
    }

    //used by content provider
    public int delete(String whereClause, String[] whereValues) {
        return database.delete(TABLE_TODO, whereClause, whereValues);
    }

    //used by content provider


    public Cursor getCursorForAllToDodos() {
        return database.query(TABLE_TODO, new String[]{COLUMN_TODO_ID, COLUMN_TODO, COLUMN_PLACE}, null, null, null, null, null);
    }


    public Cursor getCursorForSpecificPlace(String place) {
        return database.query(TABLE_TODO, new String[]{COLUMN_TODO_ID, COLUMN_TODO, COLUMN_PLACE}, COLUMN_PLACE + " LIKE '%" + place + "%'", null, null, null, null);
    }

    public Cursor getCount() {
        return database.rawQuery("SELECT COUNT(*) FROM " + TABLE_TODO, null);
    }
    /*=================================Above methods used by content provider=========================*/

    public boolean delete(int id) {
        return database.delete(TABLE_TODO, COLUMN_TODO_ID + "=" + id, null) > 0;

    }

    public List<ToDo> getAlltoDos() {

        List<ToDo> list = new ArrayList<>();

        Cursor cursor = database.query(TABLE_TODO, new String[]{COLUMN_TODO_ID, COLUMN_TODO, COLUMN_PLACE}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            ToDo toDo = new ToDo(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
            list.add(toDo);
        }

        if (cursor != null) cursor.close();
        return list;


    }


    private static class ToDoListDBHelper extends SQLiteOpenHelper {

        public ToDoListDBHelper(@Nullable Context context, @Nullable String dbName, @Nullable SQLiteDatabase.CursorFactory factory, int dbVersion) {
            super(context, dbName, factory, dbVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_TODO);
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "onUpgrade: ");
            if (oldVersion == 1) {
                db.execSQL("ALTER TABLE " + TABLE_TODO + " ADD COLUMN " + COLUMN_PLACE + " TEXT");
            }

        }
    }


}
