package com.lalaqwenta.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CountDownTimer timer;

    androidx.gridlayout.widget.GridLayout gridLayout;

    int score = 0;
    int tries = 0;

    boolean isInGame = false;

    int randomSeed = 31;

    int oneTryTime_inSeconds = 30;
    int oneSecond = 1000;

    int correctButton;
    int[] arrOfButtons = {R.id.answerButton, R.id.answerButton2, R.id.answerButton3, R.id.answerButton4, R.id.answerButton5, R.id.answerButton6};

    TextView timerTextView, scoreTextView, taskTextView;

    void updateScoreTextView () {
        scoreTextView.setText(String.format(Locale.ENGLISH,"%d/%d", score, tries));
    }

    void createTimer () {
        timer = new CountDownTimer(oneTryTime_inSeconds*oneSecond, oneSecond) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimerTextView(millisUntilFinished/oneSecond);
            }

            @Override
            public void onFinish() {
                updateTimerTextView(0);
                isInGame = false;
                findViewById(R.id.beginButton).animate().translationXBy(-1000);
                findViewById(R.id.beginButton).setVisibility(View.VISIBLE);
                ((Button)findViewById(R.id.beginButton)).setText("Start a new game?");
            }
        };
    }

    void updateTimerTextView (long timeLeft) {
        if (timeLeft%60 < 10)
            timerTextView.setText(String.format(Locale.ENGLISH,"%d:0%d", timeLeft/60, timeLeft%60));
        else
            timerTextView.setText(String.format(Locale.ENGLISH,"%d:%d", timeLeft/60, timeLeft%60));
    }

    void createNewTask() {
        int taskNum1 = (int) (Math.random()*randomSeed);
        int taskNum2 = (int) (Math.random()*randomSeed);
        int taskRes = taskNum1 + taskNum2;

        taskTextView.setText(String.format(Locale.ENGLISH,"%d + %d", taskNum1, taskNum2));

        correctButton = arrOfButtons[(int) (Math.random()*6)];

        for (int i : arrOfButtons) {
            ((Button) findViewById(i)).setText(String.format(Locale.ENGLISH,"%d",
                    (int) (Math.random()*randomSeed)+ (int) (Math.random()*randomSeed)));
        }

        ((Button) findViewById(correctButton)).setText(String.format(Locale.ENGLISH,"%d", taskRes));
    }

    public void onClickBegin (View view) {
        //Сделали всё видимым.
        timerTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        taskTextView.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);

        //Делаем кнопку невидимой и убираем её.
        view.setVisibility(View.INVISIBLE);
        view.animate().translationXBy(1000);

        beginNewGame();
    }

    void beginNewGame() {
        score = 0;
        tries = 0;
        isInGame = true;

        createTimer();
        timer.start();

        createNewTask();
    }

    public void onClickAnswer (View view) {
        if (isInGame){
            if (view.getId() == correctButton)
            {
                score++;
            }
            tries++;
            updateScoreTextView();
            createNewTask();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        taskTextView = findViewById(R.id.taskTextView);

        gridLayout = findViewById(R.id.gridLayout);
    }
}
