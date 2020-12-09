package com.lalaqwenta.duckstodolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String[]> notPerformedTasks,
    performedTasks;

    LinearLayout toDoLayout;
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
            (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoLayout = findViewById(R.id.toDoLayout);

        performedTasks = new ArrayList<>();
        notPerformedTasks = new ArrayList<>();

        for (int i = 0; i < 5; i++)
        {
            onTest();
        }
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //setContentView(R.layout.activity_main);
        try {
            assert data != null;
            String[] newTask = data.getStringArrayExtra("New_task");
            if (newTask == null) throw new Exception("Intent was not generated!");
            if (newTask.length == 0) throw new Exception("No data was proceeded!");
            notPerformedTasks.add(newTask);

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LargeTextViewWithMargins newNumberTextView = new
                    LargeTextViewWithMargins(this, 15);
            newNumberTextView.setText(Integer.toString(notPerformedTasks.size()));
            linearLayout.addView(newNumberTextView);
            for (String a : newTask
            ) {
                LargeTextViewWithMargins newTextView = new
                        LargeTextViewWithMargins(this, a.length()*10);
                newTextView.setText(a);
                linearLayout.addView(newTextView);
            }

            toDoLayout.addView(linearLayout, layoutParams);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    public void onTest()
    {
        String[] newTask = new String[]{"aaa", "bbb", "cccc"};
        notPerformedTasks.add(newTask);
        LinearLayout linearLayout = findViewById(R.id.toDoLayout);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);

        LargeTextViewWithMargins newNumberTextView = new LargeTextViewWithMargins(this, 15);
        newNumberTextView.setText(Integer.toString(notPerformedTasks.size()));
        newLayout.addView(newNumberTextView);
        for (String a : newTask
        ) {
            LargeTextViewWithMargins newTextView = new LargeTextViewWithMargins(this, a.length()*10);
            newTextView.setText(a);
            newLayout.addView(newTextView);
        }

        linearLayout.addView(newLayout, layoutParams);
    }

    public void onNewTask(View view)
    {
        Intent newTask = new Intent(MainActivity.this, NewTaskActivity.class);

        MainActivity.this.startActivityForResult(newTask, 0);
    }
}