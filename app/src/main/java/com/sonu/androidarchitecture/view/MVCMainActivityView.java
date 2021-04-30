package com.sonu.androidarchitecture.view;

import com.sonu.androidarchitecture.model.beans.ToDo;

import java.util.List;

public interface MVCMainActivityView extends MVCView {
    public void bindDataToView();

    public void showAllToDos(List<ToDo> toDoList);

    public void updateViewOnAdd(List<ToDo> toDoList);

    public void updateViewOnRemove(List<ToDo> toDoList);

    public void updateViewOnModified(List<ToDo> toDoList);

    public void showErrorToast(String errorMessage);
}
