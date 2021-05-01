package com.sonu.androidarchitecture.view;

import com.sonu.androidarchitecture.model.beans.ToDo;

public interface MVCDataManipulatorView extends MVCView {

    public void showSelectedToDo();
    public void updateViewOnRemove();
    public void updateViewOnModify(ToDo toDo);
    public void showErrorToast(String errorMessage);

}
