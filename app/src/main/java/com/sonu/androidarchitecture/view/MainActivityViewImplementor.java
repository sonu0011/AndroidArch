package com.sonu.androidarchitecture.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sonu.androidarchitecture.MyApplication;
import com.sonu.androidarchitecture.R;
import com.sonu.androidarchitecture.controller.MVCController;
import com.sonu.androidarchitecture.model.MVCModelImplementor;
import com.sonu.androidarchitecture.model.Observer;
import com.sonu.androidarchitecture.model.beans.ToDo;

import java.util.List;

public class MainActivityViewImplementor implements MVCMainActivityView, Observer {
    private View rootView;

    private MVCModelImplementor mvcModel;

    private MVCController mvcController;


    private EditText newTodo, todoId, updateTodo, placeToDo;
    private TextView textView;
    private Button addTodoBtn, updateTodoBtn, deleteToDoBtn;


    public MainActivityViewImplementor(Context context, ViewGroup viewGroup) {
        rootView = LayoutInflater.from(context).inflate(R.layout.activity_main, viewGroup);
        mvcModel = new MVCModelImplementor(MyApplication.getToDoListDBAdapter());
        mvcController = new MVCController(mvcModel, this);
        mvcModel.registerObserver(this);
    }

    @Override
    public void initViews() {

        newTodo = rootView.findViewById(R.id.editTextNewToDoString);
        placeToDo = rootView.findViewById(R.id.editTextPlace);
        todoId = rootView.findViewById(R.id.editTextToDoId);
        updateTodo = rootView.findViewById(R.id.editTextNewToDo);

        addTodoBtn = rootView.findViewById(R.id.buttonAddToDo);
        deleteToDoBtn = rootView.findViewById(R.id.buttonRemoveToDo);
        updateTodoBtn = rootView.findViewById(R.id.buttonModifyToDo);

        textView = rootView.findViewById(R.id.textViewToDos);

        addTodoBtn.setOnClickListener(v -> {
            mvcController.onAddButtonClicked(newTodo.getText().toString(), placeToDo.getText().toString());
        });

        deleteToDoBtn.setOnClickListener(v -> {
            mvcController.onRemoveButtonClicked(Integer.parseInt(todoId.getText().toString()));

        });
        updateTodoBtn.setOnClickListener(v -> {
            mvcController.onModifyButtonClicked(Integer.parseInt(todoId.getText().toString()), updateTodo.getText().toString());
        });
    }


    @Override
    public void bindDataToView() {
        mvcController.onViewLoaded();
    }

    @Override
    public void showAllToDos(List<ToDo> toDoList) {
        textView.setText(toDoList.toString());
        clearEditText();

    }

    @Override
    public void updateViewOnAdd(List<ToDo> toDoList) {
        this.showAllToDos(toDoList);
        clearEditText();
    }


    @Override
    public void updateViewOnRemove(List<ToDo> toDoList) {
        this.showAllToDos(toDoList);
        clearEditText();
    }

    @Override
    public void updateViewOnModified(List<ToDo> toDoList) {
        this.showAllToDos(toDoList);
        clearEditText();
    }

    @Override
    public void showErrorToast(String errorMessage) {
        Toast.makeText(rootView.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        if (errorMessage.equals("Empty to do list")) {
            textView.setText("");
            clearEditText();
        }
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    private void clearEditText() {
        newTodo.setText("");
        placeToDo.setText("");
        todoId.setText("");
        updateTodo.setText("");
    }


    @Override
    public void update() {

        try {
            textView.setText(mvcModel.getAllToDos().toString());
        } catch (Exception e) {
            showErrorToast(e.getMessage());
        }
    }
}
