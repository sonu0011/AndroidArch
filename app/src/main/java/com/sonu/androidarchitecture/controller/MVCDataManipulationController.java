package com.sonu.androidarchitecture.controller;

import com.sonu.androidarchitecture.model.MVCModelImplementor;
import com.sonu.androidarchitecture.view.DataManipulatorViewImplementor;

public class MVCDataManipulationController implements MVCController {

    MVCModelImplementor mvcModel;
    DataManipulatorViewImplementor mvcView;

    public MVCDataManipulationController(MVCModelImplementor mvcModel, DataManipulatorViewImplementor mvcView) {
        this.mvcModel = mvcModel;
        this.mvcView = mvcView;
    }

    @Override
    public void onViewLoaded() {
        mvcView.showSelectedToDo();
    }

    public void onRemoveBottonClicked(long id) {
        try {
            boolean success = mvcModel.removeToDoItem((int) id);
            if (success) {
                mvcView.updateViewOnRemove();
            }
        } catch (Exception e) {
            mvcView.showErrorToast(e.getMessage());
        }

    }

    public void onModifyButtonClicked(int id, String newValue) {
        try {
            boolean success = mvcModel.updateToDoItem(id, newValue);
            if (success) {
                mvcView.updateViewOnModify(mvcModel.getToDo(id));
            }
        } catch (Exception e) {
            mvcView.showErrorToast(e.getMessage());
        }
    }
}
