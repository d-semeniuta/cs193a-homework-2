package com.example.danny.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    private ArrayList<String> toDoItems;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoItems = new ArrayList<>();
        createAdapter();
    }

    public void addToDo(View view) {
        EditText toDoItem = (EditText) findViewById(R.id.to_do_item);
        toDoItems.add(toDoItem.getText().toString());
        adapter.notifyDataSetChanged();
    }


    //Listens for clicks on the list items. If an item has a long hold on it, it gets deleted.
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ListView toDoList = (ListView) findViewById(R.id.to_do_list);
        String task = toDoList.getItemAtPosition(position).toString();
        toDoItems.remove(task);

        Toast.makeText(this, "Congrats! You finished your task! Or gave up on it.", Toast.LENGTH_SHORT).show();

        adapter.notifyDataSetChanged();
        return false;
    }

    private void createAdapter(){
        ListView toDoList = (ListView) findViewById(R.id.to_do_list);
        toDoList.setOnItemLongClickListener(this);
        adapter = new ArrayAdapter<>(
                this,
                R.layout.todolist_layout,
                R.id.list_item_text,
                toDoItems
        );
        toDoList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle", "onResume was called");
        createAdapter();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putStringArrayList("toDoItems", toDoItems);
    }


    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        toDoItems = bundle.getStringArrayList("toDoItems");
        adapter.notifyDataSetChanged();
    }


}
