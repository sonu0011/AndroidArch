package com.sonu.androidarchitecture.model;

import com.sonu.androidarchitecture.model.beans.ToDo;
import com.sonu.androidarchitecture.model.db.ToDoListDbAdapter;

import java.util.ArrayList;
import java.util.List;

public class MVCModelImplementor implements MVCModel {

    private ToDoListDbAdapter adapter;
    private List<ToDo> toDoList;

    //for active model mvc pattern
    List<Observer> observerList;

    public MVCModelImplementor(ToDoListDbAdapter adapter) {
        this.adapter = adapter;
        this.toDoList = this.adapter.getAlltoDos();
        observerList = new ArrayList<>();
    }

    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    public void notifyObservers() {

        for (Observer observer : observerList) {
            observer.update();
        }

    }


    @Override
    public List<ToDo> getAllToDos() throws Exception {
        if (toDoList != null && toDoList.size() > 0) {
            return toDoList;
        } else {
            throw new Exception("Empty to do list");
        }
    }

    @Override
    public boolean addToDoItem(String todoItem, String place) throws Exception {
        boolean addSuccess = adapter.insert(todoItem, place);
        if (addSuccess) {
            refresh();
            notifyObservers();
        } else {
            throw new Exception("Something wen't wrong");
        }
        return addSuccess;
    }

    @Override
    public boolean removeToDoItem(int id) throws Exception {
        boolean deleteSuccess = adapter.delete(id);
        if (deleteSuccess) {
            refresh();
            notifyObservers();
        } else {
            throw new Exception("Something wen't wrong");
        }
        return deleteSuccess;
    }

    @Override
    public boolean updateToDoItem(int id, String newToDoValue) throws Exception {
        boolean updateSuccess = adapter.update(id, newToDoValue);
        if (updateSuccess) {
            refresh();
            notifyObservers();
        } else {
            throw new Exception("Something wen't wrong");
        }
        return updateSuccess;
    }

    private void refresh() {
        toDoList.clear();
        toDoList = adapter.getAlltoDos();
    }
}
