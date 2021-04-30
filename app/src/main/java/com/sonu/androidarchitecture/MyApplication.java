package com.sonu.androidarchitecture;

import android.app.Application;

import com.sonu.androidarchitecture.model.db.ToDoListDbAdapter;

public class MyApplication extends Application {
    static ToDoListDbAdapter toDoListDBAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        toDoListDBAdapter = ToDoListDbAdapter.getAdapter(this);
    }

    public static ToDoListDbAdapter getToDoListDBAdapter() {
        return toDoListDBAdapter;
    }

}
