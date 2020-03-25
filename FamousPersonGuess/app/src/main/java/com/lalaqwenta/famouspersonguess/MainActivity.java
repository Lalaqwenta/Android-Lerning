package com.lalaqwenta.famouspersonguess;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    int correctAnswer = 0, correctName = 0;

    int score = 0, tries = 0;

    String[] nameStrings = {"Keanu Reeves", "Tobey Maguire", "Not Keanu Reeves or Tobey Maguire"};


    TextView scoreTextView;

    int[] buttons = {R.id.answerButton, R.id.answerButton2, R.id.answerButton3, R.id.answerButton4},
            imagesArray = {R.drawable.keanu, R.drawable.toby, R.drawable.tarantino};

    String getRandomString() {

        int newRandom = (int) (Math.random()*nameStrings.length);

        while (newRandom == correctName){
            newRandom = (int) (Math.random()*nameStrings.length);
        }

        return nameStrings[newRandom];
    }

    @SuppressLint("DefaultLocale")
    void updateScoreTextView() {
        scoreTextView.setText(String.format("%d/%d", score, tries));
    }

    public void onClick(View view) {
        if (Integer.parseInt((String) view.getTag()) == correctAnswer) {
            score += 1;
            Toast.makeText(getApplicationContext(), "Right!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Wrong :(", Toast.LENGTH_SHORT).show();
        }
        tries += 1;
        updateScoreTextView();
        newRound();
    }

    void setRandomImage() {
        correctName = (int) (Math.random()*imagesArray.length);
        imageView.setImageResource(imagesArray[correctName]);
    }

    void newRound() {

        correctAnswer = (int) (Math.random()*4 );

        setRandomImage();

        for (int i : buttons) {
            Button button = findViewById(i);
            button.setText(getRandomString());
        }

        Button button = findViewById(buttons[correctAnswer]);
        button.setText(nameStrings[correctName]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        scoreTextView = findViewById(R.id.scoreTextView);
    }
}
