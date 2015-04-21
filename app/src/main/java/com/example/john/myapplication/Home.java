package com.example.john.myapplication;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.SimpleAdapter;



public class Home extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    //php read comments script

    //localhost :
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
    //TODO Change this ip to events.php
    private String GET_EVENTS_URL;

    //testing on Emulator:
    //private static final String GET_EVENTS_URL = "http://10.0.2.2:1234/webservice/comments.php";

    //testing from a real server:
    //private static final String GET_EVENTS_URL = "http://www.mybringback.com/webservice/comments.php";

    //JSON IDS:
    private static final String TAG_POSTS = "posts";
    private static final String TAG_NAME = "name";
    private static final String TAG_DATE = "date";
    private static final String TAG_VENUE = "venue";
    private static final String TAG_ID = "event_id";
    private static final String TAG_TYPE = "type";
    //it's important to note that the message is both in the parent branch of
    //our JSON tree that displays a "Post Available" or a "No Post Available" message,
    //and there is also a message for each individual post, listed under the "posts"
    //category, that displays what the user typed as their message.


    //An array of all of our comments
    private JSONArray events = null;
    //manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> eventList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GET_EVENTS_URL = getString(R.string.url_start) + "eventstwo.php";
    }

    @Override
    protected void onResume() {
        super.onResume();
        //loading the comments via AsyncTask
        new LoadEvents().execute();
    }

    public void openHomeActivity(View v)
    {
        Intent i = new Intent(Home.this, Home.class);
        startActivity(i);
    }

    public void openSearchActivity(View v)
    {
        Intent i = new Intent(Home.this, Search.class);
        startActivity(i);
    }

    public void openWelcomeActivity(View v)
    {
        Intent i = new Intent(Home.this, Welcome.class);
        startActivity(i);
    }

    public void openNearMeActivity(View v)
    {
        Intent i = new Intent(Home.this, NearMe.class);
        startActivity(i);
    }

    /**
     * Retrieves json data of comments
     */
    public void updateJSONdata() {
        // Instantiate the arraylist to contain all the JSON data.
        // we are going to use a bunch of key-value pairs, referring
        // to the json element name, and the content, for example,
        // message it the tag, and "I'm awesome" as the content..

        eventList = new ArrayList<HashMap<String, String>>();

        JSONParser jParser = new JSONParser();
        // Feed the beast our comments url, and it spits us
        //back a JSON object.  Boo-yeah Jerome.
        JSONObject json = jParser.getJSONFromUrl(GET_EVENTS_URL);

        //when parsing JSON stuff, we should probably
        //try to catch any exceptions:
        try {

            //I know I said we would check if "Posts were Avail." (success==1)
            //before we tried to read the individual posts, but I lied...
            //events will tell us how many "posts" or comments are
            //available
            events = json.getJSONArray(TAG_POSTS);

            // looping through all posts according to the json object returned
            for (int i = 0; i < events.length(); i++) {
                JSONObject c = events.getJSONObject(i);

                //gets the content of each tag
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String date = c.getString(TAG_DATE);
                String venue = c.getString(TAG_VENUE);
                String type = c.getString(TAG_TYPE);

                System.out.println("Title: " + name + " Date: " + date + " Venue: "+ venue + " ID: " + id);


                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(TAG_ID, id);
                map.put(TAG_NAME, name);
                map.put(TAG_DATE, date);
                map.put(TAG_VENUE, venue);
                map.put(TAG_TYPE,type);

                // adding HashList to ArrayList
                eventList.add(map);

                //annndddd, our JSON data is up to date same with our array list
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Collections.sort(eventList, new MapComparator("date"));
        System.out.println("Out of updateJSONdata method.");

    }

    //To compare map values when sorting
    class MapComparator implements Comparator<Map<String,String>>{
        private final String key;

        public MapComparator(String key){
            this.key = key;
        }

        @Override
        public int compare(Map<String, String> lhs, Map<String, String> rhs) {
            String first = lhs.get(key);
            String second = rhs.get(key);
            return first.compareTo(second);
        }
    }

    /**
     * Inserts the parsed data into our listview
     */
    private void updateList() {
        // For a ListActivity we need to set the List Adapter, and in order to do
        //that, we need to create a ListAdapter.  This SimpleAdapter,
        //will utilize our updated Hashmapped ArrayList,
        //use our single_post xml template for each item in our list,
        //and place the appropriate info from the list to the
        //correct GUI id.  Order is important here.
        ListAdapter adapter = new SimpleAdapter(this, eventList,
                R.layout.single_post, new String[] { TAG_NAME,
                TAG_TYPE , TAG_DATE}, new int[] { R.id.name, R.id.venue,
                R.id.date });

        // I shouldn't have to comment on this one:
        setListAdapter(adapter);

        // Optional: when the user clicks a list item we
        //could do something.  However, we will choose
        //to do nothing...
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // This method is triggered if an item is click within our
                // list. For our example we won't be using this, but
                // it is useful to know in real life applications.
                Integer e_id = (int) (long) id;
                HashMap<String,String> clickedEvent = new HashMap<>(eventList.get(e_id));

                Intent i = new Intent(Home.this, SingleEvent.class);

                i.putExtra("event_id", clickedEvent.get(TAG_ID));
                startActivity(i);
            }
        });
    }

    public class LoadEvents extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Home.this);
            pDialog.setMessage("Loading Events...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            updateJSONdata();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            updateList();
        }
    }
}
