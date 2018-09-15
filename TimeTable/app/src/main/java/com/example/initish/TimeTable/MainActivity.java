package com.example.initish.TimeTable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView listView;

    private void generate(int timetable){

        ArrayList<String> timestable=new ArrayList<String>();
        for(int i=1;i<=10;i++)
            timestable.add(Integer.toString(i*timetable));
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,timestable);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.my_list);

        SeekBar seekBar=(SeekBar)findViewById(R.id.seekBar);

        seekBar.setMax(20);
        seekBar.setProgress(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

               int min=1,timetable=(i<min)?min:i;
               if(i<min) seekBar.setProgress(min);

               generate(timetable);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generate(10);
    }
}
