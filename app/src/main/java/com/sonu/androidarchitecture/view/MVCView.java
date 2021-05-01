package com.sonu.androidarchitecture.view;

import android.view.View;

public interface MVCView {
    public View getRootView();
    public void bindDataToView();
    public void initViews();
}
