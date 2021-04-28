package com.sonu.androidarchitecture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sonu.androidarchitecture.db.ToDoListDbAdapter;
import com.sonu.androidarchitecture.model.ToDo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ToDoListDbAdapter adapter;
    List<ToDo> list;

    private EditText newTodo, todoId, updateTodo  ,placeToDo;
    private TextView textView;
    private static final String TAG = "MainActivity";
    private Button addTodoBtn, updateTodoBtn, deleteToDoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = ToDoListDbAdapter.getAdapter(this);
        list = new ArrayList<>();

        newTodo = findViewById(R.id.editTextNewToDoString);
        placeToDo = findViewById(R.id.editTextPlace);
        todoId = findViewById(R.id.editTextToDoId);
        updateTodo = findViewById(R.id.editTextNewToDo);

        addTodoBtn = findViewById(R.id.buttonAddToDo);
        deleteToDoBtn = findViewById(R.id.buttonRemoveToDo);
        updateTodoBtn = findViewById(R.id.buttonModifyToDo);

        textView = findViewById(R.id.textViewToDos);

        addTodoBtn.setOnClickListener(this);
        updateTodoBtn.setOnClickListener(this);
        deleteToDoBtn.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        textView.setText(getAllTodos());

    }

    private void addItem() {
        Log.d(TAG, "addItem: ");
        adapter.insert(newTodo.getText().toString() , placeToDo.getText().toString());
        textView.setText(getAllTodos());


    }

    private void updateItem() {
        Log.d(TAG, "updateItem: ");
        adapter.update(Integer.parseInt(todoId.getText().toString()), updateTodo.getText().toString());
        textView.setText(getAllTodos());
    }

    private void removeItem() {
        Log.d(TAG, "removeItem: ");
        adapter.delete(Integer.parseInt(todoId.getText().toString()));

        textView.setText(getAllTodos());

    }

    private String getAllTodos() {
        Log.d(TAG, "getAllTodos: ");
        List<ToDo> toDos = adapter.getAlltoDos();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < toDos.size(); i++) {

            ToDo toDo = toDos.get(i);
            stringBuilder.append(toDo.getId() + "  " + toDo.getName() + " "+toDo.getPlace() +"\n");

        }
        if (toDos.size() == 0) {
            return "no todos found";

        }

        return stringBuilder.toString();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddToDo:
                addItem();
                break;
            case R.id.buttonRemoveToDo:
                removeItem();
                break;
            case R.id.buttonModifyToDo:
                updateItem();
                break;
        }
    }
}