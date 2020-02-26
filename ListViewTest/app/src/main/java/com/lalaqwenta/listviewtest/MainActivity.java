package com.lalaqwenta.listviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<String> numsArray;

    ArrayAdapter<String> arrayAdapter;

    SeekBar seekBar;

    TextView currentNumberTextView;

    public void correctProgress() {
        currentNumberTextView.setText(String.valueOf(seekBar.getProgress() + 1));
        correctList();
    }

    void correctList() {
        listView = findViewById(R.id.listView);
        numsArray = new ArrayList<String>();
        for (int i = 0; i < 10; i++)
        {
            numsArray.add(String.valueOf((i+1)*(seekBar.getProgress()+1)));
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, numsArray);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        seekBar = (SeekBar) findViewById(R.id.seekBar);
        currentNumberTextView = (TextView) findViewById(R.id.currentNumberTextView);

        correctProgress();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                correctProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
