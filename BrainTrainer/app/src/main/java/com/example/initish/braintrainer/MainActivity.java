package com.example.initish.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    Button start, button0, button1, button2, button3,button;
    TextView textView, scoretext, sumtext, timertext;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationofanswer, score = 0, numberofq = 0;
    Group group;
    public void start(View view) {
        start.setVisibility(View.INVISIBLE);

        button.setVisibility(View.VISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        scoretext.setVisibility(View.VISIBLE);
        sumtext.setVisibility(View.VISIBLE);
        timertext.setVisibility(View.VISIBLE);

        generate();
    }

    public void play(View view){
        generate();
        score=0;
        numberofq=0;
        timertext.setText("30s");
        scoretext.setText("0/0");
        textView.setText("");
        button.setVisibility(View.INVISIBLE);
        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long l) {

                timertext.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                button.setVisibility(View.VISIBLE);
                timertext.setText("0s");
                textView.setText("Your Score: " + Integer.toString(score) + "/" + Integer.toString(numberofq));
            }
        }.start();

    }

    public void generate() {
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        sumtext.setText(Integer.toString(a) + "+" + Integer.toString(b));

        locationofanswer = rand.nextInt(4);
        answers.clear();
        int wronganswer;
        for (int i = 0; i < 4; i++) {
            if (i == locationofanswer)
                answers.add(a + b);
            else {
                wronganswer = rand.nextInt(41);
                while (wronganswer == (a + b))
                    wronganswer = rand.nextInt(41);
                answers.add(wronganswer);
            }
        }
        button3.setText(Integer.toString(answers.get(0)));
        button2.setText(Integer.toString(answers.get(1)));
        button1.setText(Integer.toString(answers.get(2)));
        button0.setText(Integer.toString(answers.get(3)));
    }

    public void click(View view) {

        if (view.getTag().toString().equals(Integer.toString(locationofanswer))) {
            score++;
            textView.setText("Correct!");
        } else {
            textView.setText("Wrong!");
        }
        numberofq++;
        scoretext.setText(Integer.toString(score) + "/" + Integer.toString(numberofq));
        generate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        sumtext = (TextView) findViewById(R.id.sumtext);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button=(Button)findViewById(R.id.button);
        scoretext = (TextView) findViewById(R.id.scoretext);
        textView = (TextView) findViewById(R.id.textView);
        timertext = (TextView) findViewById(R.id.timertext);
        play(findViewById(R.id.button));


    }
}
