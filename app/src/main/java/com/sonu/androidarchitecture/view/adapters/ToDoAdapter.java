package com.sonu.androidarchitecture.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sonu.androidarchitecture.R;
import com.sonu.androidarchitecture.model.beans.ToDo;
import com.sonu.androidarchitecture.view.MVCListItemView;
import com.sonu.androidarchitecture.view.ToDoListItemMVCImpl;

import java.util.List;


public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewViewHolder> implements MVCListItemView.ListItemClickListener {

    private Context context;
    private List<ToDo> todos;
    MVCListItemView.ListItemClickListener listItemClickListener;

    public ToDoAdapter(Context context, List<ToDo> toDos, MVCListItemView.ListItemClickListener listItemClickListener) {
        this.context = context;
        this.todos = toDos;
        this.listItemClickListener = listItemClickListener;
    }

    @Override
    public ToDoAdapter.ToDoViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ToDoListItemMVCImpl toDoListItemMVC = new ToDoListItemMVCImpl((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), parent);
        toDoListItemMVC.initViews();
        toDoListItemMVC.setListItemClickListener(this);
        return new ToDoViewViewHolder(toDoListItemMVC);

    }

    @Override
    public void onBindViewHolder(ToDoAdapter.ToDoViewViewHolder holder, final int position) {
        final ToDo toDo = todos.get(position);
        holder.listItemMVC.bindDataToView(toDo);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.todo_row_item;
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    @Override
    public void onItemClicked(long position) {
        listItemClickListener.onItemClicked(position);
    }

    static class ToDoViewViewHolder extends RecyclerView.ViewHolder {

        private ToDoListItemMVCImpl listItemMVC;

        public ToDoViewViewHolder(ToDoListItemMVCImpl view) {
            super(view.getRootView());
            listItemMVC = view;
        }

    }
}