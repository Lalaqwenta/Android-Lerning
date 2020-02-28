package com.lalaqwenta.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean isPushed = false;

    SeekBar seekBar;

    int oneSec = 1000;

    TextView timeSetTextView,
            timeLeftTextView;

    CountDownTimer timer;

    MediaPlayer mediaPlayer;

    public void onClick (View view) {
        Log.i("Info", "Egg was pushed.");
        if (isPushed) {
            isPushed = false;
            timer.cancel();

        }
        else {
            ImageButton imageButton = findViewById(R.id.imageButton);
            imageButton.setImageResource(R.drawable.egg_without_crakc);
            isPushed = true;
            timer.start();
        }
    }

    void changeTime(long timeLeft)
    {
            timeLeftTextView.setText(timeString(timeLeft));
    }

    void updateTimer(long progress) {
        timer = new CountDownTimer((progress)*oneSec, oneSec)
        {
            public void onTick(long millisUntilFinished) {
                Log.i("Info", String.format("%d left", millisUntilFinished/oneSec));
                changeTime(millisUntilFinished/oneSec);
            }
            public void onFinish() {
                mediaPlayer.start();
                changeTime(0);
                isPushed = false;
                ImageButton imageButton = findViewById(R.id.imageButton);
                imageButton.setImageResource(R.drawable.egg_with_crack);
            }
        };
    }

    String timeString (int timeVal) {
        if (timeVal%60 < 10) {
            return String.format("%d:0%d", timeVal/60, timeVal%60);
        }
        else {
            return String.format("%d:%d", timeVal/60, timeVal%60);
        }
    }

    String timeString (long timeVal) {
        if (timeVal%60 < 10) {
            return String.format("%d:0%d", timeVal/60, timeVal%60);
        }
        else {
            return String.format("%d:%d", timeVal/60, timeVal%60);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);

        timeLeftTextView = findViewById(R.id.timeLeftTextView);
        timeSetTextView = findViewById(R.id.timeSetTextView);

        final int progress = seekBar.getProgress();

        timeSetTextView.setText(timeString(progress+1));

        changeTime(progress+1);
        updateTimer(progress+1);

        mediaPlayer = MediaPlayer.create(this, R.raw.beeps1);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeTime(progress + 1);
                timeSetTextView.setText(timeString(progress+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                timer.cancel();
                isPushed = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateTimer(seekBar.getProgress()+1);
            }
        });

    }
}
