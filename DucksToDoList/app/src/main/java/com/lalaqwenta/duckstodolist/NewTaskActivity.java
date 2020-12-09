package com.lalaqwenta.duckstodolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class NewTaskActivity extends AppCompatActivity {

    int numOfFields = 5;

    public void onSave(View view){
        Intent main = new Intent();

        String[] data;

        data = new String[numOfFields];

        LinearLayout vertical = findViewById(R.id.newTaskSettingsLinearLayout);

        for (int i = 0; i < numOfFields; i++)
        {
            EditText result = (EditText) ((LinearLayout) vertical.getChildAt(i) ).getChildAt(1);
            data[i] = "fuuuuck" + i +result.getText().toString();
        }

        main.putExtra("New_task", data);
        setResult(RESULT_OK, main);

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_task);
    }
}