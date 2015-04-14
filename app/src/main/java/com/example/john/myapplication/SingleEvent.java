package com.example.john.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SingleEvent extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();


    private  String GET_DETAILS_URL;

    //JSON IDS:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_POSTS = "posts";
    private static final String TAG_NAME = "name";
    private static final String TAG_DATE = "date";
    private static final String TAG_VENUE = "venue";
    private static final String TAG_TYPE = "type";
    private static final String TAG_ID = "event_id";
    private static final String TAG_PRICE = "price";
    private static final String TAG_AGE = "age";
    private static final String TAG_OPENING_TIMES = "opening_times";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_IMAGE_NAME = "image_name";
    private static final String TAG_MUSIC_GENRE = "music_genre";

    String eventId;
    private String id, name, date, venue, type, price, age, opening_times, description, image_name,music_genre;

    //An array of all of our comments
    private JSONArray mComments = null;
    //manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> mCommentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        GET_DETAILS_URL = getString(R.string.url_start) + "eventdetail.php";

        Intent i = getIntent();
        eventId = i.getStringExtra("event_id");




    }

    @Override
    protected void onResume() {
        super.onResume();
        //loading the comments via AsyncTask
        //new GetDetails().execute();

       // System.out.println("EVENT ID AFTER EXECUTE: " + id);

        //TextView txt = (TextView) findViewById(R.id.event_id);
        //txt.setText(id);
        new GetDetails().execute();
        //setData(mComments);
    }

    public void setData(JSONArray eventDetails){
        System.out.println("In set data");
        try {
            for (int i = 0; i < eventDetails.length(); i++) {
                JSONObject c = eventDetails.getJSONObject(i);

                //gets the content of each tag
                id = c.getString(TAG_ID);
                name = c.getString(TAG_NAME);
                date = c.getString(TAG_DATE);
                venue = c.getString(TAG_VENUE);
                type = c.getString(TAG_TYPE);
                price = c.getString(TAG_PRICE);
                age = c.getString(TAG_AGE);
                opening_times = c.getString(TAG_OPENING_TIMES);
                description = c.getString(TAG_DESCRIPTION);
                image_name = c.getString(TAG_IMAGE_NAME);
                music_genre = c.getString(TAG_MUSIC_GENRE);

                System.out.println("Name: " + name + " Date: " + date + " Venue: " + venue + " ID: " + id + " Type: " + type + " price: " + price + " age: " + age + " time: " + opening_times+ " genre: " + music_genre);


                //annndddd, our JSON data is up to date same with our array list
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("before set text");

        try {
            ImageView img = (ImageView) findViewById(R.id.main_image);
            //img.setImageResource(image_id);

            int id = getResources().getIdentifier("com.example.john.myapplication:drawable/" + image_name, null, null);
            img.setImageResource(id);

           // String variableValue = image_name;
           // img.setImageResource(getResources().getIdentifier(variableValue, "drawable", getPackageName()));

            TextView txt = (TextView) findViewById(R.id.event_name);
            txt.setText(name);

            txt = (TextView) findViewById(R.id.event_date);
            txt.setText(date);

            txt = (TextView) findViewById(R.id.event_venue);
            txt.setText(venue);

            txt = (TextView) findViewById(R.id.event_description);
            txt.setText(description);

            txt = (TextView) findViewById(R.id.event_type);
            txt.setText(type);

            txt = (TextView) findViewById(R.id.event_music_genre);
            txt.setText(music_genre);

            txt = (TextView) findViewById(R.id.event_price);
            txt.setText(price);

            txt = (TextView) findViewById(R.id.event_age);
            txt.setText(age);

            txt = (TextView) findViewById(R.id.event_time);
            txt.setText(opening_times);
        }
        catch (Exception e){
            System.out.println("in exception");
            e.printStackTrace();
        }
        System.out.println("after set text");
        System.out.println("Out of set data");
    }

    /**
     * Retrieves json data of comments
     */
    class GetDetails extends AsyncTask<String, String, String> {

        //three methods get called, first preExecture, then do in background, and once do
        //in back ground is completed, the onPost execture method will be called.

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;


        @Override
        protected void onPreExecute() {
            System.out.println("In pre execute");
            super.onPreExecute();
            pDialog = new ProgressDialog(SingleEvent.this);
            pDialog.setMessage("Getting Event Details...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            System.out.println("Out of pre execute");

        }

        @Override
        protected String doInBackground(String... args) {
            System.out.println("In do in background");

            // Check for success tag
            int success;

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("event_id", eventId));

                Log.d("request!", "starting");
                // getting product details by making HTTP request

                JSONObject json = jsonParser.makeHttpRequest(GET_DETAILS_URL, "POST", params);


                //TODO what does this do?
                // check your log for json response
                Log.d("Login attempt", json.toString());



                //I know I said we would check if "Posts were Avail." (success==1)
                //before we tried to read the individual posts, but I lied...
                //mComments will tell us how many "posts" or comments are
                //available
                mComments = json.getJSONArray(TAG_POSTS);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setData(mComments);

                    }
                });
              //  setData(mComments);
                    // looping through all posts according to the json object returned



            }    catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("Out of do in background");
            return null;
        }

        protected void onPostExecute(String file_url) {
            System.out.println("In post execute");
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(SingleEvent.this, file_url, Toast.LENGTH_LONG).show();
            }
            System.out.println("out of post execute");
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
