package com.lalaqwenta.timerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Integer counter = 0;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("Info", String.format("%d seconds have passed!", counter++));
                textView.setText(String.format("%d seconds have passed!", counter));
                handler.postDelayed(this, 1000);

            }
        };

        handler.post(runnable);
    }
}
