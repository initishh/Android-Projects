package com.example.initish.eggtimerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Button button;
    Boolean counterisactive=false;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        textView.setText("0:30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        button.setText("GO");
        counterisactive=false;
        seekBar.setEnabled(true);
    }

    public void controlTimer(View view) {

        if (counterisactive == false) {
            counterisactive = true;
            seekBar.setEnabled(false);
            button.setText("STOP");

            countDownTimer=new CountDownTimer(seekBar.getProgress() * 1000+100, 1000) {

                @Override
                public void onTick(long l) {

                    updateTimer((int) l / 1000);

                }

                @Override
                public void onFinish() {

                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bong);
                    mediaPlayer.start();
                }
            }.start();
        }
        else{
            resetTimer();
        }
    }

    public void updateTimer(int sec){

        int min=sec/60;
        int seconds=sec-min*60;
        String second=Integer.toString(seconds);
        String minute=Integer.toString(min);
        if(min<=9)
            minute="0"+minute;
        if(seconds<=9)
            second="0"+second;
        textView.setText(minute+":"+second);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar=(SeekBar)findViewById(R.id.seekBar);
        textView=(TextView)findViewById(R.id.textView);
        button=(Button)findViewById(R.id.button);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b){
                updateTimer(i);
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
