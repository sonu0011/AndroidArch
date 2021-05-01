package com.sonu.androidarchitecture.controller;

import com.sonu.androidarchitecture.model.MVCModelImplementor;
import com.sonu.androidarchitecture.view.MVCView;
import com.sonu.androidarchitecture.view.MainActivityViewImplementor;

public class MVCMainActivityController implements MVCController {
    private final MVCModelImplementor mvcModel;

    private final MainActivityViewImplementor mvcView;

    public MVCMainActivityController(MVCModelImplementor mvcModelImplementor, MainActivityViewImplementor mvcView) {
        this.mvcModel = mvcModelImplementor;
        this.mvcView = mvcView;
    }

    @Override
    public void onViewLoaded() {

        try {
            mvcView.showAllToDos(mvcModel.getAllToDos());
        } catch (Exception e) {
            mvcView.showErrorToast(e.getMessage());
        }
    }

    public void onAddButtonClicked(String toDoItem, String place) {
        try {
            boolean addSuccess = mvcModel.addToDoItem(toDoItem, place);
            if (addSuccess) {
                mvcView.updateViewonAdd(mvcModel.getAllToDos());
            }
        } catch (Exception e) {
            mvcView.showErrorToast(e.getMessage());
        }
    }


    public void onToDoItemSelected(long toDoId) {
        mvcView.navigateToDataManipulationActivity(toDoId);
    }

}
