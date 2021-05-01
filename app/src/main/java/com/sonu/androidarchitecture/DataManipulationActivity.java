package com.sonu.androidarchitecture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sonu.androidarchitecture.view.MVCView;
import com.sonu.androidarchitecture.view.MVCViewFactory;

public class DataManipulationActivity extends AppCompatActivity {
    MVCView mvcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvcView = MVCViewFactory.getMVCView(MVCViewFactory.VIEW_TYPE.MANIPULATION_VIEW_TYPE, DataManipulationActivity.this, null, getIntent());
        setContentView(mvcView.getRootView());
        mvcView.initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvcView.bindDataToView();
    }
}