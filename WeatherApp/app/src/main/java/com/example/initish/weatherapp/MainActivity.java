package com.example.initish.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText cityName;
    TextView resultText;
    public void findWeather(View view) {

        InputMethodManager mgr=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(),0);
        try {
            String encodedCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");
            Json task = new Json();
            task.execute("https://api.openweathermap.org/data/2.5/weather?q=" + cityName.getText().toString());
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Could not locate weather", Toast.LENGTH_LONG);

        }
    }

    public class Json extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {

                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Could not locate weather", Toast.LENGTH_LONG).show();
                return null;
            }
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {

                String message="";
                JSONObject Jsonobject=new JSONObject(result);

                String weatherInfo=Jsonobject.getString("weather");

                JSONArray arr=new JSONArray(weatherInfo);

                for(int i=0;i<arr.length();i++){

                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main="";
                    String description="";

                    main=jsonPart.getString("main");
                    description=jsonPart.getString("description");

                    if(main!="" &&description!=""){
                        message+=main + ": "+description + "\r\n";
                    }
                }
                if(message!="")
                {
                    resultText.setText(message);
                    Log.i("Result: ",message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName=(EditText) findViewById(R.id.cityName);
        resultText=(TextView) findViewById(R.id.resultText);

    }
}
