package com.lalaqwenta.sumandminusgame;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Variable which stores amount of turns.
    private int turnCounter = 0;

    //Sum of absolute values in the cells. If it is zero the game ends. Affects ProgressBar.
    private int absSumOfCells = 0;

    //Approximate maximum of prev value. -1 by default, changes to 30 by now.
    private int maxAbsSumOfCells = -1;

    //Checks if the player has won.
    private void winCheck() {

        if (absSumOfCells == 0)
        {
            Log.i("Info", "absSumOfCell is equal to zero.");
            Toast.makeText(this, "You have won!", Toast.LENGTH_LONG).show();
        }
    }

    private void setTurnCounter(int _turnCounter) {
        turnCounter = _turnCounter;

        Log.i("Info", String.format("turnCounter was set to %d", turnCounter));

        if (turnCounter%10 == 1 && turnCounter != 11)
        {
            TextView textView = (TextView) findViewById(R.id.turnCounterTextView3);

            textView.setText(" turn!");
        }
        else if (turnCounter%10 == 2)
        {
            TextView textView = (TextView) findViewById(R.id.turnCounterTextView3);

            textView.setText(" turns!");
        }

        TextView textView = (TextView) findViewById(R.id.turnCounterTextView2);

        textView.setText(String.format("%d", turnCounter));

    }
    //Ends the turn.
    private void endOfTurn() {

        setTurnCounter(turnCounter+1);

        Log.i("Info", String.format("Turn %d has ended.", turnCounter));

        updateProgress();

        winCheck();
    }

    //Changes row with number n (1-3) by k
    private void changeRow(int n, int k) {

        androidx.gridlayout.widget.GridLayout gridLayout = (androidx.gridlayout.widget.GridLayout) findViewById(R.id.gridLayout);

        for (int i = 1; i <= 3; i++) {

            int currentChild = n - n%5 + i;

            TextView textView = (TextView) gridLayout.getChildAt(currentChild);

            int currentVal = Integer.parseInt(textView.getText().toString());

            absSumOfCells -= Math.abs(currentVal) - Math.abs(currentVal + k);

            textView.setText(String.valueOf(currentVal + k));

            Log.i("Info", String.format("Changed %d cell.", currentChild));

        }

        Log.i("Info", String.format("Changed absSumCells to %d. Changed %d row.", absSumOfCells, n/5));
    }

    //Changes column with number n (1-3) by k
    private void changeColumn(int n, int k) {

        androidx.gridlayout.widget.GridLayout gridLayout = (androidx.gridlayout.widget.GridLayout) findViewById(R.id.gridLayout);

        for (int i = 1; i <= 3; i++) {

            int currentChild = n%5 + i*5;

            TextView textView = (TextView) gridLayout.getChildAt(currentChild);

            int currentVal = Integer.parseInt(textView.getText().toString());

            absSumOfCells -= Math.abs(currentVal) - Math.abs(currentVal + k);

            textView.setText(String.valueOf(currentVal + k));

            Log.i("Info", String.format("Changed %d cell.", currentChild));

        }

        Log.i("Info", String.format("Changed absSumCells to %d. Changed %d column.", absSumOfCells, n%5));
    }

    //Proceeds what button was pressed.
    public void onClick (View view) {

        Log.i("Info", "Button was pushed.");

        ImageButton button = (ImageButton) view;

        int tagVal = Integer.parseInt(button.getTag().toString());

        passTagVal(tagVal);

        endOfTurn();
    }

    private void passTagVal (int tagVal) {

        if (tagVal/5 < 1) {
            //Upper row.
            Log.i("Info", "Upper row button was pushed.");
            changeColumn(tagVal, 1);
        }
        else if (tagVal/5 >= 4)
        {
            //Down row.
            Log.i("Info", "Down row button was pushed.");
            changeColumn(tagVal, -1);
        }
        else if (tagVal%5 == 0)
        {
            //Left column.
            Log.i("Info", "Left column button was pushed.");
            changeRow(tagVal, 1);
        }
        else if (tagVal%5 == 4)
        {
            //Right column.
            Log.i("Info", "Right column button was pushed.");
            changeRow(tagVal, -1);
        }
        else
        {
            //Unexpected.
            Log.e("Info", "Button was pushed and something went wrong.");
        }
    }

    public void onClickNewGame(View view)
    {
        newGame();
    }

    //This function creates a new game board.
    private void newGame (){
        int[] arr = {1, 2, 3, 5, 10, 15, 9, 14, 19, 21, 22, 23};
        for (int i = 0; i < 30; i++)
        {
            int randomTagVal = (int)(Math.random() * 11);
            Log.i("Info.", String.format("%d random button was %d.", i, arr[randomTagVal]));
            passTagVal(arr[randomTagVal]);
        }
        setTurnCounter(0);

        setMaxAbsSumOfCells(absSumOfCells*2);
        updateProgress();
    }

    //This updates progress bar.
    private void updateProgress(){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (maxAbsSumOfCells < 0)
            setMaxAbsSumOfCells(30);
        if (maxAbsSumOfCells < 2*absSumOfCells/3)
            setMaxAbsSumOfCells(absSumOfCells * 2);
        progressBar.setProgress(maxAbsSumOfCells-absSumOfCells);
    }

    private void startMusic(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.snakejazz);

        mediaPlayer.start();
    }

    private void setMaxAbsSumOfCells (int _maxAbsSumOfSells) {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        maxAbsSumOfCells = _maxAbsSumOfSells;
        progressBar.setMax(maxAbsSumOfCells);
        Log.i("Info.", String.format("maxAbsSumOfCells is set to %d.", maxAbsSumOfCells));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startMusic();
        //newGame();
    }
}
