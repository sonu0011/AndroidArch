package com.sonu.androidarchitecture.view;

import com.sonu.androidarchitecture.model.beans.ToDo;

import java.util.List;

public interface MVCMainActivityView extends MVCView {
    public void showAllToDos(List<ToDo> toDoList);
    public void updateViewonAdd(List<ToDo> toDoList);
    public void showErrorToast(String errorMessage);
    public void navigateToDataManipulationActivity(long id);
}
