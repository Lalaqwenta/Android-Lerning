package com.myappcompany.rob.exampleproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    int counter = 0;

    public void clickFunction(View view) {
        if (counter > 0)
            Log.i("Info", "Button pressed " + ++counter + " times!");
        else
            Log.i("Info", "Button pressed " + ++counter + " time!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
