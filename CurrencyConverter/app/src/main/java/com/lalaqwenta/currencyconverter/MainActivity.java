package com.lalaqwenta.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    float DollarCost = 60;

    public void convertValue(float koef){
        EditText valToConvertEditText = (EditText) findViewById(R.id.currentRequestEditText);
        float valToConvert =
            Float.valueOf(valToConvertEditText.getText().toString().trim()).floatValue()*koef;
        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
        resultTextView.setText(String.format("%.2f",valToConvert));
    }

    public void onClickRubbleToDollar(View view){
        convertValue(1/DollarCost);
    }

    public void onClickDollarToRubble(View view){
        convertValue(DollarCost);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
