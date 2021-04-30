package com.sonu.androidarchitecture.controller;

import com.sonu.androidarchitecture.model.MVCModelImplementor;
import com.sonu.androidarchitecture.view.MVCView;
import com.sonu.androidarchitecture.view.MainActivityViewImplementor;

public class MVCController {
    private final MVCModelImplementor mvcModel;

    private final MainActivityViewImplementor mvcView;

    public MVCController(MVCModelImplementor mvcModelImplementor, MainActivityViewImplementor mvcView) {
        this.mvcModel = mvcModelImplementor;
        this.mvcView = mvcView;
    }

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
              //  mvcView.updateViewOnAdd(mvcModel.getAllToDos());
            }
        } catch (Exception e) {
            mvcView.showErrorToast(e.getMessage());
        }
    }

    public void onRemoveButtonClicked(int id) {
        try {
            boolean success = mvcModel.removeToDoItem(id);
            if (success) {
               // mvcView.updateViewOnRemove(mvcModel.getAllToDos());
            }
        } catch (Exception e) {
            mvcView.showErrorToast(e.getMessage());
        }

    }

    public void onModifyButtonClicked(int id, String newValue) {
        try {
            boolean success = mvcModel.updateToDoItem(id, newValue);
            if (success) {
              //  mvcView.updateViewOnModified(mvcModel.getAllToDos());
            }
        } catch (Exception e) {
            mvcView.showErrorToast(e.getMessage());
        }
    }

}
