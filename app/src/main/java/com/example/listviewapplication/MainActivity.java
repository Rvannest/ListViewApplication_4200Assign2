package com.example.listviewapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

//for snackbard


public class MainActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;
    Button btn_add;
   // String list[] = {"item1", "item2", "item3", "item4","item5"};

    //for long-press on item
    int currentlyEditingPosition = -1;

    ArrayList<String> arrayList;


    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        editText =findViewById(R.id.editTextText);
        btn_add = findViewById(R.id.button);

        //arrayList.add("Data1");
        //arrayList.add("Data2");
        //arrayList.add("Data3");
        arrayList = FileHandler.readData(MainActivity.this);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);


//        // Set an OnClickListener to the button 'btn_add'. This means that the code inside onClick will be executed when the button is clicked.
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Retrieve the text entered in the editText field and convert it to a String.
//                String itemText = editText.getText().toString();
//
//                // Check if the retrieved text is not empty.
//                if (!itemText.isEmpty()) {
//                    // If the text is not empty, add it to the 'arrayList'.
//                    arrayList.add(itemText);
//                    // Call the 'writeData' method of 'FileHandler' to save the updated 'arrayList' into a file.
//                    FileHandler.writeData(arrayList, getApplicationContext());
//                    // Notify the 'arrayAdapter' that the data set has changed so it can update the ListView.
//                    arrayAdapter.notifyDataSetChanged();
//                    // Clear the editText field to make it ready for the next item input.
//                    editText.setText("");
//                } else {
//                    // If the text is empty, show a Snackbar message on the screen informing the user that an empty item cannot be added.
//                    // The Snackbar is displayed at the bottom of the screen and lasts for a short duration (LENGTH_LONG).
//                    Snackbar.make(view, "Cannot add empty item!", Snackbar.LENGTH_LONG).show();
//                }
//            }
//        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemText = editText.getText().toString();
                if (!itemText.isEmpty()) {
                    if (currentlyEditingPosition== -1) {
                        arrayList.add(itemText);
                    } else {
                        arrayList.set(currentlyEditingPosition, itemText);
                        currentlyEditingPosition = -1;
                    }
                    FileHandler.writeData(arrayList, getApplicationContext());
                    arrayAdapter.notifyDataSetChanged();
                    editText.setText("");
                } else {
                    Snackbar.make(view, "Cannot add empty item!", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
//                arrayList.remove(i);
//                arrayAdapter.notifyDataSetChanged();
//                FileHandler.writeData(arrayList, getApplicationContext());
                editText.setText(arrayList.get(position));
                currentlyEditingPosition = position;
                return true;
            }
        });


    }
}