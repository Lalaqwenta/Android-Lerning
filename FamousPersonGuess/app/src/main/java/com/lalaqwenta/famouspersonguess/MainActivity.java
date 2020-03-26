package com.lalaqwenta.famouspersonguess;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    int correctAnswer = 0, correctName = 0;

    int score = 0, tries = 0;

    List<String> nameStrings = new ArrayList<String>();

    String html = "";

    String url = "https://www.therichest.com/top-lists/top-100-richest-celebrities/";

    List<String> imageURLS = new ArrayList<String>();

    TextView scoreTextView;

    int[] buttons = {R.id.answerButton, R.id.answerButton2, R.id.answerButton3, R.id.answerButton4};

    String getRandomString() {

        int newRandom = (int) (Math.random()*nameStrings.size());

        while (newRandom == correctName){
            newRandom = (int) (Math.random()*nameStrings.size());
        }

        return nameStrings.get(newRandom);
    }

    @SuppressLint("DefaultLocale")
    void updateScoreTextView() {
        scoreTextView.setText(String.format("%d/%d", score, tries));
    }

    public static class DowloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            Log.i("Info.", "Passed tp doInBackground.");
            try {
                URL google = new URL(urls[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(google.openStream()));
                String input;
                StringBuilder stringBuffer = new StringBuilder();
                while ((input = in.readLine()) != null) {
                    stringBuffer.append(input);
                }
                in.close();
                return stringBuffer.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return "Failed.";
        }

    }

    public void onClick(View view) throws ExecutionException, InterruptedException {
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

    void setRandomImage() throws ExecutionException, InterruptedException {
        correctName = (int) (Math.random()*imageURLS.size());
        ImageDownloader task = new ImageDownloader();
        Bitmap bitmap = task.execute(imageURLS.get(correctName)).get();
        imageView.setImageBitmap(bitmap);
    }

    void newRound() throws ExecutionException, InterruptedException {

        correctAnswer = (int) (Math.random()*4 );

        setRandomImage();

        for (int i : buttons) {
            Button button = findViewById(i);
            button.setText(getRandomString());
        }

        Button button = findViewById(buttons[correctAnswer]);
        button.setText(nameStrings.get(correctName));
    }

    void processHTML() {
        Pattern pattern = Pattern.compile("<tr>(.*?)</tr>");
        Matcher matcher = pattern.matcher(html);

        matcher.find();
        while (matcher.find()) {
            processPerson(matcher.group(1));
        }
    }

    void processPerson(String person) {

        Log.i("Process info", person);

        Pattern imgPattern = Pattern.compile("data-srcset=\"(.*?)\\?q=");
        Pattern namePattern = Pattern.compile("</div></div></div>(.*?)</a></td>");

        Matcher imgMatcher = imgPattern.matcher(person);
        Matcher nameMatcher = namePattern.matcher(person);

        imgMatcher.find();
        nameMatcher.find();

        imageURLS.add(imgMatcher.group(1));
        nameStrings.add(nameMatcher.group(1));

        Log.i("Results", imgMatcher.group(1) + ", " + nameMatcher.group(1));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        scoreTextView = findViewById(R.id.scoreTextView);

        DowloadTask dowloadTask = new DowloadTask();

        try {
            html = dowloadTask.execute(url).get();
        } catch (Exception e) {
            Log.i("Info.", "Not done.");
            e.printStackTrace();
        }

        processHTML();

    }

    public static class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream in = connection.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(in);

                return bitmap;

            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        }
    }

}