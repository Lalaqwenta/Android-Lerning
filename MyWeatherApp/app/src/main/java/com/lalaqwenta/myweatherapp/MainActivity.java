package com.lalaqwenta.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String[] cityArray = {"Moscow", "London", "Paris", "Berlin", "Kiev", "Toronto", "Oslo",
            "Vienna", "York", "Warsaw", "Cairo"};

    //Timer used to refresh data every ten minutes.
    CountDownTimer timerForUpdate;

    String APIKey = "a4dd5ae7b770b36d999ffbd29f0ecd80";

    //Buttons to switch temperature metrics.
    int[] metricButtons = {R.id.metricButtonF, R.id.metricButtonC, R.id.metricButtonK};

    //Variable for currently chosen metric: 0 for F, 1 C, 2 K.
    int metric = 1;

    //Variable for currently chosen city. Moscow by default.
    int currentCity = 0;

    //Temperature for each city in K.
    double[] dataArray = new double[cityArray.length];

    //View with temperature in the chosen city using chosen metric.
    TextView weatherInfoTextView;

    double changeMetricToC (double t)
    {
        return t-273.15;
    }

    double changeMetricToF (double t)
    {
        return changeMetricToC(t)*1.8 + 32;
    }

    @SuppressLint("SetTextI18n")
    public void changeMetric(View view) {
        int id = view.getId();
        if (id == metricButtons[0]) {
            metric = 0;
            weatherInfoTextView.setText(Double.toString(changeMetricToF(dataArray[currentCity])));
        } else if (id == metricButtons[1]) {
            metric = 1;
            weatherInfoTextView.setText(Double.toString(changeMetricToC(dataArray[currentCity])));
        } else if (id == metricButtons[2]){
            metric = 2;
            weatherInfoTextView.setText(Double.toString(dataArray[currentCity]));
        } else {
            Log.e("Error", "Something went wrong!");
        }
    }

    //Through this class I am getting JSON object with all the data for one city.
    public static class DownloadTask extends AsyncTask<String, Void, Double> {

        @Override
        protected Double doInBackground(String... urls) {
            Log.i("Info.", "Passed tp doInBackground.");
            try {
                URL url = new URL(urls[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String input;
                StringBuilder stringBuilder = new StringBuilder();

                while ((input = in.readLine()) != null) {
                    stringBuilder.append(input);
                }

                in.close();
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(stringBuilder.toString());
                    Log.i("Request Result", jsonObject.toString());
                    return Double.valueOf(jsonObject.getJSONObject("main").getString("temp"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return (double) -1;
        }
    }

    //OnClick function for all city buttons, which shows weather in tapped city using chosen metric.
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            ToggleButton button = findViewById(currentCity);
            button.setChecked(false);
            ((ToggleButton) v).setChecked(true);

            currentCity = v.getId();
            double data = dataArray[currentCity];
            if (metric == 1) data = changeMetricToC(data);
            if (metric == 0) data = changeMetricToF(data);
            weatherInfoTextView.setText(Double.toString(data));
        }
    };

    //Adding city buttons programmatically.
    void addButtons() {

        LinearLayout linearLayout = findViewById(R.id.cityLayout);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < cityArray.length; i++) {
            ToggleButton newButton = new ToggleButton(this);
            newButton.setTextOn(cityArray[i]);
            newButton.setTextOff(cityArray[i]);
            newButton.setId(i);
            newButton.setOnClickListener(onClickListener);
            newButton.setChecked(!(i>0));

            linearLayout.addView(newButton, layoutParams);
        }
    }

    //Collecting data for each city.
    void collectData() {

        String urlAdress = "https://api.openweathermap.org/data/2.5/weather?";
        for (int i = 0; i < cityArray.length; i++) {

            String url = urlAdress + "q=" + cityArray[i] + "&appid=" + APIKey;
            DownloadTask downloadTask = new DownloadTask();

            try {
                dataArray[i] = downloadTask.execute(url).get();
                Log.i("Result", String.valueOf(dataArray[i]));
            } catch (Exception e) {
                Log.i("Info.", "Not done.");
                e.printStackTrace();
            }
        }
        createTimer();
    }

    void createTimer() {
        timerForUpdate = new CountDownTimer(60*10*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                collectData();
            }
        }.start();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherInfoTextView = findViewById(R.id.weatherInfoTextView);

        addButtons();
        collectData();

        weatherInfoTextView.setText(Double.toString(dataArray[currentCity]-273.15));
    }
}
