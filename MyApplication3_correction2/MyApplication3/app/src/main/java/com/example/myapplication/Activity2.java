package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity2 extends AppCompatActivity {


    boolean threadIsRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_2);

        Log.e("DEVE0304", "Activity2:onCreate()");

        Intent myIntent = getIntent(); // gets the previously created intent
        String firstKeyName = myIntent.getStringExtra("Nom de l'utilisateur");
        Log.e("DEVE0304", "Activity2:onCreate() : Intent key value : " + firstKeyName);

        threadIsRunning = false;

        final MediaPlayer kawaii = MediaPlayer.create(this, R.raw.o_kawaii_koto);

        Button playKawaii = (Button) this.findViewById(R.id.button_play);
        Button buttonParse = (Button) this.findViewById(R.id.button_Parse);

        playKawaii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kawaii.start();
            }
        });

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        String url = "https://jsoneditoronline.org/#left=cloud.b85b29d3b54f4df6a83ea18a255d1821";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("location");

                            for(int i = 0; i <  jsonArray.length(); i++){
                                JSONObject results = jsonArray.getJSONObject(i);

                                int id = results.getInt("id");
                                String Ville = results.getString("Ville");
                                String Lat = results.getString("Lat");
                                String Longi = results.getString("Longi");

                                Log.e("DEVE0304", "" +String.valueOf(id) + ", " + Ville + ", " + Lat + ", " + Longi + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }


    public void runThread(View view){

        Log.e("DEVE0304", "Button clicked : runThread");

        threadIsRunning = true;

        new Thread(new Runnable() {
            public void run() {

                while (threadIsRunning)
                {
                    // a potentially time consuming task
                    Log.e("DEVE0304", "Thread 1 : click");
                    SystemClock.sleep(2000);
                }
            }
        }).start();

    };


    public void stopThread(View view){

        Log.e("DEVE0304", "Button clicked : stopThread");

        threadIsRunning = false;
    };




    public void saveData(View view){

        Log.e("DEVE0304", "Button clicked : saveData");

        EditText myDataField = findViewById(R.id.myDataField);


        String value = myDataField.getText().toString();
        int finalValue = Integer.parseInt(value);

        Log.e("DEVE0304", "Data read from field " + finalValue);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = myPreferences.edit();
        editor.putInt("storedNumber", finalValue); // value to store
        editor.apply();

    };


    public void loadData(View view){

        Log.e("DEVE0304", "Button clicked : loadData");

        SharedPreferences myPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        int retrievedInt = myPreferences.getInt("storedNumber", -1);

        Log.e("DEVE0304", "Button clicked : loadData " + retrievedInt);


        EditText myDataField = findViewById(R.id.myDataField);
        //myDataField.setText(retrievedInt);
        myDataField.setText(Integer.toString(retrievedInt));

        Toast.makeText(getApplicationContext(), "Retrieved value : " + Integer.toString(retrievedInt), Toast.LENGTH_LONG).show();
    };

    public void hideButton(View view){

        Log.e("DEVE0304", "Button clicked : hide other button");

        Button myButton = findViewById(R.id.buttonDisappearing);

        if (myButton.getVisibility() == View.INVISIBLE)
        {
            myButton.setVisibility(View.VISIBLE);
            Button myOtherButton = findViewById(R.id.buttonDisappear);
            myOtherButton.setText("Hide button");

        }else if (myButton.getVisibility() == View.VISIBLE)
        {
            myButton.setVisibility(View.INVISIBLE);

            Button myOtherButton = findViewById(R.id.buttonDisappear);
            myOtherButton.setText("Show button");
        }
    };

    public void generateException(View view){

        Log.e("DEVE0304", "Button clicked : hide other button");

        Button myButton = findViewById(R.id.buttonException);
        myButton = null;

        //try {
            String array[] = new String[] {"Toyota", "Mercedes", "BMW", "Volkswagen", "Skoda" };
            Log.e("DEVE0304", "Error " + array[5]);
        /*}
        catch (Exception e) {
            // This will catch any exception
            System.out.println("Error " + e.getMessage());
            Log.e("DEVE0304", "Error " + e.getMessage());
        }

         */
    };

}