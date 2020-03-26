package com.lalaqwenta.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String[] cityArray = {"London", "Moscow", "Paris", "Berlin", "Kiev"};

    int[] metricButtons = {R.id.metricButtonF, R.id.metricButtonC, R.id.metricButtonK};

    int metric = 1; //0 for F, 1 C, 2 K.

    int currentCity = -1;

    double[] dataArray = new double[cityArray.length];
    Map<Integer, Integer> cityMap = new HashMap<Integer, Integer>();

    TextView weatherInfoTextView;

    @SuppressLint("SetTextI18n")
    public void changeMetric(View view) {
        int id = view.getId();
        if (id == metricButtons[0]) {
            metric = 0;
            weatherInfoTextView.setText(Double.toString((dataArray[currentCity]-273.15)*1.8 + 32));
        } else if (id == metricButtons[1]) {
            metric = 1;
            weatherInfoTextView.setText(Double.toString(dataArray[currentCity]-273.15));
        } else if (id == metricButtons[2]){
            metric = 2;
            weatherInfoTextView.setText(Double.toString(dataArray[currentCity]));
        } else {
            Log.e("Error", "Something went wrong!");
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            currentCity = cityMap.get(v.getId());
            double data = dataArray[currentCity];
            if (metric < 2) data -= 273.15;
            if (metric < 1) data = (data*1.8) + 32;
            weatherInfoTextView.setText(Double.toString(data));
        }
    };

    void addButtons() {
        for (int i = 0; i < cityArray.length; i++) {
            Button newButton = new Button(this);
            newButton.setText(cityArray[i]);
            cityMap.put(newButton.getId(), i);
            newButton.setOnClickListener(onClickListener);

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.cityListLayout);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.addView(newButton);
        }
    }

    void collectData() {
        for (int i = 0; i < dataArray.length; i++)
            dataArray[i] = 273 + i*15;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherInfoTextView = (TextView) findViewById(R.id.weatherInfoTextView);

        addButtons();
        collectData();
    }
}
