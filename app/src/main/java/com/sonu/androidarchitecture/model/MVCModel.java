package com.sonu.androidarchitecture.model;

import com.sonu.androidarchitecture.model.beans.ToDo;

import java.util.List;

public interface MVCModel {

    public List<ToDo> getAllToDos() throws Exception;

    public boolean addToDoItem(String todoItem, String place) throws Exception;

    public boolean removeToDoItem(int id) throws Exception;

    public boolean updateToDoItem(int id, String newToDoValue) throws Exception;
}
