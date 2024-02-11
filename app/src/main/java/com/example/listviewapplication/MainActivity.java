package com.example.listviewapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

//for snackbar and floating action button
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.content.Intent;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;
    Button btn_add;
   // String list[] = {"item1", "item2", "item3", "item4","item5"};

    //for long-press on item
    int currentlyEditingPosition = -1;

    //for floating action button
    FloatingActionButton fab_add;

    static final int ADD_TASK_REQUEST = 1;

    //to replace startActivityForResult with ActivityResultLauncher, startActivityForResult is deprecated apparently
    private ActivityResultLauncher<Intent> addTaskActivityResultLauncher;

    ArrayList<String> arrayList;

    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

//        editText =findViewById(R.id.editTextText);
//        btn_add = findViewById(R.id.button);

        fab_add = findViewById(R.id.fab_add);

        //arrayList.add("Data1");
        //arrayList.add("Data2");
        //arrayList.add("Data3");
        arrayList = FileHandler.readData(MainActivity.this);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);



        addTaskActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                String task = data.getStringExtra("task");
                                if (data.hasExtra("position")) {
                                    int position = data.getIntExtra("position", -1);
                                    if (position != -1) {
                                        arrayList.set(position, task);
                                    }
                                } else {
                                    arrayList.add(task);
                                }
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                });



        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                addTaskActivityResultLauncher.launch(intent);
            }
        });


//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String itemText = editText.getText().toString();
//                if (!itemText.isEmpty()) {
//                    if (currentlyEditingPosition== -1) {
//                        arrayList.add(itemText);
//                    } else {
//                        arrayList.set(currentlyEditingPosition, itemText);
//                        currentlyEditingPosition = -1;
//                    }
//                    FileHandler.writeData(arrayList, getApplicationContext());
//                    arrayAdapter.notifyDataSetChanged();
//                    editText.setText("");
//                } else {
//                    Snackbar.make(view, "Cannot add empty item!", Snackbar.LENGTH_LONG).show();
//                }
//            }
//        });


//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
////                arrayList.remove(i);
////                arrayAdapter.notifyDataSetChanged();
////                FileHandler.writeData(arrayList, getApplicationContext());
//                editText.setText(arrayList.get(position));
//                currentlyEditingPosition = position;
//                return true;
//            }
//        });

        //copied this from above, modifying it for part V of the assignment
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                String taskToEdit = arrayList.get(position);
                intent.putExtra("task", taskToEdit);
                intent.putExtra("position", position);
                addTaskActivityResultLauncher.launch(intent);
//                arrayList.remove(i);
//                arrayAdapter.notifyDataSetChanged();
//                FileHandler.writeData(arrayList, getApplicationContext());
//                editText.setText(arrayList.get(position));
//                currentlyEditingPosition = position;
                return true;
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            String task = data.getStringExtra("task");
            arrayList.add(task);
            arrayAdapter.notifyDataSetChanged();
        }
    }

}