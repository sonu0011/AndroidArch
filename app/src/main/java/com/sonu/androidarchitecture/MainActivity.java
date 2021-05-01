package com.sonu.androidarchitecture;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sonu.androidarchitecture.R;
import com.sonu.androidarchitecture.model.db.ToDoListDbAdapter;
import com.sonu.androidarchitecture.model.beans.ToDo;
import com.sonu.androidarchitecture.view.MVCMainActivityView;
import com.sonu.androidarchitecture.view.MVCView;
import com.sonu.androidarchitecture.view.MVCViewFactory;
import com.sonu.androidarchitecture.view.MainActivityViewImplementor;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MVCView mvcView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvcView = MVCViewFactory.getMVCView(MVCViewFactory.VIEW_TYPE.MAIN_VIEW_TYPE, MainActivity.this, null, null);
        setContentView(mvcView.getRootView());
        mvcView.initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvcView.bindDataToView();
    }
}
