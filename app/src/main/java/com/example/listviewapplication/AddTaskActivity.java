package com.example.listviewapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

//for "Add to the list" button I think
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        final EditText editTextTask = findViewById(R.id.editTextTask);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = editTextTask.getText().toString();
                if (!taskText.isEmpty()) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("task", editTextTask.getText().toString());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    editTextTask.setError("Task cannot be empty");
                }
            }
        });
    }
}