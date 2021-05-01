package com.sonu.androidarchitecture.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sonu.androidarchitecture.R;
import com.sonu.androidarchitecture.model.beans.ToDo;

public class ToDoListItemMVCImpl implements MVCListItemView<ToDo> {

    View rootView;
    ToDo toDo;

    public LinearLayout layoutContainer;
    public TextView textViewId, textViewToDo, textViewPlace;

    ListItemClickListener listItemClickListener;


    public ToDoListItemMVCImpl(LayoutInflater layoutInflater, ViewGroup parent) {
        rootView = layoutInflater.inflate(R.layout.todo_row_item, parent, false);
    }

    @Override
    public void setListItemClickListener(ListItemClickListener listener) {
        this.listItemClickListener = listener;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void initViews() {
        layoutContainer = rootView.findViewById(R.id.layoutContainer);
        textViewId = rootView.findViewById(R.id.textViewId);
        textViewToDo = rootView.findViewById(R.id.textViewToDo);
        textViewPlace = rootView.findViewById(R.id.textViewPlace);
    }

    @Override
    public void bindDataToView(ToDo object) {
        this.toDo = object;
        textViewId.setText("Id: " + this.toDo.getId());
        textViewToDo.setText("To Do: " + this.toDo.getName());
        textViewPlace.setText("Place: " + this.toDo.getPlace());
        layoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listItemClickListener != null) {
                    listItemClickListener.onItemClicked(toDo.getId());
                }
            }
        });
    }
}
