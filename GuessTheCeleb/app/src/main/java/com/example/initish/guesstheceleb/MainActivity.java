package com.example.initish.guesstheceleb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> celeburls=new ArrayList<String>();
    ArrayList<String> celebnames=new ArrayList<String>();
    int chosenCeleb=0,locationOfCorrectAnswer=0;

    String[] answers=new String[4];

    Button button1,button2,button3,button4;
    ImageView imageView;

    public void celebChosen(View view) throws ExecutionException, InterruptedException {
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){

            Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Wrong! It was "+celebnames.get(chosenCeleb),Toast.LENGTH_LONG).show();
        }
        newQuestion();
    }

    public class ImageDownloader extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url=new URL(urls[0]);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.connect();

                InputStream inputStream=connection.getInputStream();
                Bitmap myBitmap=BitmapFactory.decodeStream(inputStream);

                return myBitmap;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {

            String result="";
            URL url;
            HttpURLConnection urlConnection=null;

            try{

                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);

                int data=reader.read();

                while(data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
                return result;
            }
            catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }
    }

    public void newQuestion() throws ExecutionException, InterruptedException {

        Random random=new Random();
        chosenCeleb=random.nextInt(celeburls.size());

        ImageDownloader imageTask=new ImageDownloader();

        Bitmap celebImage;
        celebImage=imageTask.execute(celeburls.get(chosenCeleb)).get();

        imageView.setImageBitmap(celebImage);

        locationOfCorrectAnswer=random.nextInt(4);

        int incorrect=0;
        for(int i=0;i<4;i++){
            if(i==locationOfCorrectAnswer){
                answers[i]=celebnames.get(chosenCeleb);
            }
            else{
                incorrect=random.nextInt(celeburls.size());

                while(incorrect==chosenCeleb)
                    incorrect=random.nextInt(celeburls.size());
                answers[i]=celebnames.get(incorrect);
            }
        }
        button1.setText(answers[0]);
        button2.setText(answers[1]);
        button3.setText(answers[2]);
        button4.setText(answers[3]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);
        imageView=(ImageView)findViewById(R.id.imageView);

        DownloadTask task=new DownloadTask();
        String result=null;

        try {
            result=task.execute("http://posh24.se/kandisar").get();

            String[] splitresult=result.split("<div class=\"sidebarInnerContainer\">");

            Pattern p=Pattern.compile("img src=\"(.*?)\"");
            Matcher m=p.matcher(splitresult[0]);

            while(m.find()){
                celeburls.add(m.group(1));
            }

            p=Pattern.compile("alt=\"(.*?)\"");
            m=p.matcher(splitresult[0]);

            while(m.find()){

            celebnames.add(m.group(1));

            }

          newQuestion();

        }
        catch (ExecutionException e) {

            e.printStackTrace();

        }
        catch (InterruptedException e) {

            e.printStackTrace();

        }

    }
}
