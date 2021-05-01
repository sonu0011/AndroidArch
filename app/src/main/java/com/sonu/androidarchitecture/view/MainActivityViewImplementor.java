package com.sonu.androidarchitecture.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sonu.androidarchitecture.DataManipulationActivity;
import com.sonu.androidarchitecture.MyApplication;
import com.sonu.androidarchitecture.R;
import com.sonu.androidarchitecture.controller.MVCMainActivityController;
import com.sonu.androidarchitecture.model.MVCModelImplementor;
import com.sonu.androidarchitecture.model.Observer;
import com.sonu.androidarchitecture.model.beans.ToDo;
import com.sonu.androidarchitecture.view.adapters.ToDoAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewImplementor implements MVCMainActivityView, MVCListItemView.ListItemClickListener {
    private final View rootView;

    private final MVCModelImplementor mvcModel;
    private ToDoAdapter toDoAdapter;

    private final MVCMainActivityController mainActivityController;

    private EditText editTextNewToDoString, editTextPlace;
    private RecyclerView recyclerView;
    private Button buttonAddToDo;


    public MainActivityViewImplementor(Context context, ViewGroup viewGroup) {
        rootView = LayoutInflater.from(context).inflate(R.layout.activity_main, viewGroup);
        mvcModel = new MVCModelImplementor(MyApplication.getToDoListDBAdapter());
        mainActivityController = new MVCMainActivityController(mvcModel, this);
    }

    @Override
    public void initViews() {

        editTextNewToDoString = rootView.findViewById(R.id.editTextNewToDoString);
        editTextPlace = rootView.findViewById(R.id.editTextPlace);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView = rootView.findViewById(R.id.recyclerListViewToDos);
        recyclerView.setLayoutManager(linearLayoutManager);

        buttonAddToDo = rootView.findViewById(R.id.buttonAddToDo);
        buttonAddToDo.setOnClickListener(v -> mainActivityController.onAddButtonClicked(editTextNewToDoString.getText().toString(), editTextPlace.getText().toString()));
    }


    @Override
    public void bindDataToView() {
        mainActivityController.onViewLoaded();

    }


    @Override
    public void showAllToDos(List<ToDo> toDoList) {
        toDoAdapter = new ToDoAdapter(rootView.getContext(), toDoList, this);
        recyclerView.setAdapter(toDoAdapter);

    }

    @Override
    public void updateViewonAdd(List<ToDo> toDoList) {
        this.showAllToDos(toDoList);
        clearEditText();
    }


    @Override
    public void showErrorToast(String errorMessage) {
        Toast.makeText(rootView.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        if (errorMessage.equals("Empty to do list")) {
            clearListView();
        }
    }

    @Override
    public View getRootView() {
        return rootView;
    }


    private void clearEditText() {
        editTextNewToDoString.setText("");
        editTextPlace.setText("");
    }


    private void clearListView() {
        toDoAdapter = new ToDoAdapter(rootView.getContext(), new ArrayList<>(), this);
        recyclerView.setAdapter(toDoAdapter);
    }

    @Override
    public void onItemClicked(long position) {
        mainActivityController.onToDoItemSelected(position);
    }

    @Override
    public void navigateToDataManipulationActivity(long id) {
        Intent intent = new Intent(rootView.getContext(), DataManipulationActivity.class);
        intent.putExtra("todoId", id);
        rootView.getContext().startActivity(intent);
    }
}
