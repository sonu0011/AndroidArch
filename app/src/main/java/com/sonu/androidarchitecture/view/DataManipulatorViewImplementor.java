package com.sonu.androidarchitecture.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sonu.androidarchitecture.MyApplication;
import com.sonu.androidarchitecture.R;
import com.sonu.androidarchitecture.controller.MVCDataManipulationController;
import com.sonu.androidarchitecture.model.MVCModelImplementor;
import com.sonu.androidarchitecture.model.beans.ToDo;


public class DataManipulatorViewImplementor implements MVCDataManipulatorView {


    View rootView;

    MVCDataManipulationController manipulationController;

    private MVCModelImplementor mvcModel;

    TextView textViewToBeModifiedToDoId, textViewToBeModifiedToDo, textViewToBeModifiedToDoPlace;
    Button buttonRemoveToDo, buttonModifyToDo;
    EditText editTextNewToDo;

    ToDo toDo;

    long toDoId;

    public DataManipulatorViewImplementor(Context context, ViewGroup container, Intent intent) {
        rootView = LayoutInflater.from(context).inflate(R.layout.activity_data_manipulation, container);
        mvcModel = new MVCModelImplementor(MyApplication.getToDoListDBAdapter());
        toDoId = intent.getLongExtra("todoId", 1);
        mvcModel = new MVCModelImplementor(MyApplication.getToDoListDBAdapter());
        manipulationController = new MVCDataManipulationController(mvcModel, this);
    }

    @Override
    public void initViews() {
        textViewToBeModifiedToDoId = (TextView) rootView.findViewById(R.id.textViewToBeModifiedToDoId);
        textViewToBeModifiedToDo = (TextView) rootView.findViewById(R.id.textViewToBeModifiedToDo);
        textViewToBeModifiedToDoPlace = (TextView) rootView.findViewById(R.id.textViewToBeModifiedToDoPlace);

        buttonRemoveToDo = (Button) rootView.findViewById(R.id.buttonRemoveToDo);
        buttonModifyToDo = (Button) rootView.findViewById(R.id.buttonModifyToDo);

        editTextNewToDo = (EditText) rootView.findViewById(R.id.editTextNewToDo);

        buttonRemoveToDo.setOnClickListener(v -> manipulationController.onRemoveBottonClicked(toDoId));

        buttonModifyToDo.setOnClickListener(v -> manipulationController.onModifyButtonClicked((int) toDoId, editTextNewToDo.getText().toString()));


    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void bindDataToView() {
        manipulationController.onViewLoaded();

    }

    @Override
    public void showSelectedToDo() {
        try {
            toDo = mvcModel.getToDo(toDoId);
            textViewToBeModifiedToDoId.setText("Id: " + toDo.getId());
            textViewToBeModifiedToDo.setText("ToDo: " + toDo.getName());
            textViewToBeModifiedToDoPlace.setText("Place: " + toDo.getPlace());
        } catch (Exception ex) {
            Toast.makeText(rootView.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void showErrorToast(String errorMessage) {
        Toast.makeText(rootView.getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateViewOnRemove() {
        textViewToBeModifiedToDoId.setText("");
        textViewToBeModifiedToDo.setText("");
        textViewToBeModifiedToDoPlace.setText("");
        Toast.makeText(rootView.getContext(), "Successfully removed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateViewOnModify(ToDo toDo) {
        this.toDo = toDo;
        textViewToBeModifiedToDo.setText(this.toDo.getName());
        Toast.makeText(rootView.getContext(), "Successfully updated", Toast.LENGTH_LONG).show();
    }
}