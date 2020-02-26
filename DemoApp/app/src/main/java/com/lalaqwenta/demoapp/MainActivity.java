package com.lalaqwenta.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view) {

        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);

        Log.i("Info", "It is pushed!");

        Log.i("Values", nameEditText.getText().toString());

        String string = nameEditText.getText().toString();

        string.

        Toast.makeText(this, "Hi, " + string + "!", Toast.LENGTH_LONG).show();

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.cat);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
