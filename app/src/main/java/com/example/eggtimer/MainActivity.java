package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    boolean counterIsActive=false;
    Button controlButton;
    CountDownTimer countDownTimer;

    public void resetTimer()
    {
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controlButton.setText("Go !");
        timerSeekBar.setEnabled(true);
        counterIsActive=false;
    }

    public void updateTimer(int secondsLeft)
    {
        int minutes = (int) secondsLeft /60;
        int seconds= secondsLeft - (minutes*60);

        String secondsString=Integer.toString(seconds);

        if(seconds <=9)
            secondsString= "0" +secondsString;

        timerTextView.setText(Integer.toString(minutes) + " :" + secondsString);
    }

    public  void controlTimer(View view)
    {

        if(counterIsActive==false) {
            counterIsActive = true;
            // now we want to disable the seekbar jaise hi user pressed the button "Go "
            // which on cliked wil call "controlTimer" function
            timerSeekBar.setEnabled(false);
            controlButton.setText("Stop");
            countDownTimer=new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                // pehle tick par delay hota hai so we add 1/10th of a second to make it look accurate
                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {

                   resetTimer();
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    // getApplicationContext ki jagah "this" likhne se we'll get an error
                    // because hum controlTimer ke andar hai
                    mplayer.start();
                }
            }.start();
        }
        else {
           resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar= (SeekBar)findViewById(R.id.timerSeekBar);
        // let's set the max time limit as 10 mins i.e 600 seconds
        timerSeekBar.setMax(600);
        // initially 30 seconds

        controlButton=(Button) findViewById(R.id.controllerButton);

        timerTextView = (TextView)findViewById(R.id.timerTextView);

        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}