package com.example.john.myapplication;

import android.app.ListActivity;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchList extends ListActivity {

    private String SEARCH_URL;

    private static final String TAG_POSTS = "posts";
    private static final String TAG_NAME = "name";
    private static final String TAG_DATE = "date";
    private static final String TAG_VENUE = "venue";
    private static final String TAG_TYPE = "type";
    private static final String TAG_ID = "event_id";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    private JSONArray mComments = null;

    private ArrayList<HashMap<String, String>> mCommentList;

    private String age,type,music_genre,name,date;

    private int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        SEARCH_URL= getString(R.string.url_start) + "eventsearch.php";

        Intent i = getIntent();
        type = i.getStringExtra("event_type");
        music_genre = i.getStringExtra("music_genre");
        age = i.getStringExtra("age");
        date = i.getStringExtra("date");
        name = i.getStringExtra("name");

        System.out.println("Type: "+type+" Genre: "+music_genre+" Age: "+age+" Date: "+date+" Name: "+name);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //loading the comments via AsyncTask
        new LoadSearchResults().execute();
    }

    public class LoadSearchResults extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchList.this);
            pDialog.setMessage("Loading Search Results...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            //we will develop this method in version 2

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("event_type", type));
                params.add(new BasicNameValuePair("music_genre", music_genre));
                params.add(new BasicNameValuePair("age", age));
                params.add(new BasicNameValuePair("date", date));
                params.add(new BasicNameValuePair("name", name));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(SEARCH_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Search Successful!", json.toString());

                    /*SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username", username);
                    edit.commit();*/

                    /*Intent i = new Intent(MainActivity.this, Home.class);
                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);*/
                    mComments = json.getJSONArray(TAG_POSTS);
                    updateJSONdata();
                }else{
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    //return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            //we will develop this method in version 2
            updateList();
        }
    }

    public void updateJSONdata() {
        System.out.println("Got to updateJSONdata method.");
        // Instantiate the arraylist to contain all the JSON data.
        // we are going to use a bunch of key-value pairs, referring
        // to the json element name, and the content, for example,
        // message it the tag, and "I'm awesome" as the content..

        mCommentList = new ArrayList<HashMap<String, String>>();

        // Bro, it's time to power up the J parser

        //JSONParser jParser = new JSONParser();
        // Feed the beast our comments url, and it spits us
        //back a JSON object.  Boo-yeah Jerome.

        //JSONObject json = jParser.getJSONFromUrl(SEARCH_URL);

        //when parsing JSON stuff, we should probably
        //try to catch any exceptions:
        try {

            //I know I said we would check if "Posts were Avail." (success==1)
            //before we tried to read the individual posts, but I lied...
            //mComments will tell us how many "posts" or comments are
            //available
            //mComments = json.getJSONArray(TAG_POSTS);

            // looping through all posts according to the json object returned
            for (int i = 0; i < mComments.length(); i++) {
                JSONObject c = mComments.getJSONObject(i);

                //gets the content of each tag
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String date = c.getString(TAG_DATE);
                String venue = c.getString(TAG_VENUE);

                System.out.println("GOT ARRAY: Title: " + name + " Date: " + date + " Venue: " + venue + " ID: " + id);


                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(TAG_ID, id);
                map.put(TAG_NAME, name);
                map.put(TAG_DATE, date);
                map.put(TAG_VENUE, venue);

                // adding HashList to ArrayList
                mCommentList.add(map);

                //annndddd, our JSON data is up to date same with our array list
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Collections.sort(mCommentList, new MapComparator("date"));
        System.out.println("Out of updateJSONdata method.");

    }

    //To compare map values when sorting
    class MapComparator implements Comparator<Map<String,String>> {
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

    private void updateList() {
        System.out.println("Got to updateList method.");
        // For a ListActivity we need to set the List Adapter, and in order to do
        //that, we need to create a ListAdapter.  This SimpleAdapter,
        //will utilize our updated Hashmapped ArrayList,
        //use our single_post xml template for each item in our list,
        //and place the appropriate info from the list to the
        //correct GUI id.  Order is important here.
        if(success ==1) {
            ListAdapter adapter = new SimpleAdapter(this, mCommentList,
                    R.layout.single_post, new String[]{TAG_NAME,
                    TAG_VENUE, TAG_DATE}, new int[]{R.id.title, R.id.message,
                    R.id.username});
            System.out.println("Created adapter.");
            // I shouldn't have to comment on this one:
            try {
                setListAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Set adapter.");

            // Optional: when the user clicks a list item we
            //could do something.  However, we will choose
            //to do nothing...
            ListView lv = getListView();
            System.out.println("Init list view.");
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // This method is triggered if an item is click within our
                    // list. For our example we won't be using this, but
                    // it is useful to know in real life applications.
                    Integer e_id = (int) (long) id;
                    HashMap<String, String> test = new HashMap<>(mCommentList.get(e_id));
                    System.out.println("You clicked me!" + " Event ID: " + test.get(TAG_ID));


                    Intent i = new Intent(SearchList.this, SingleEvent.class);

                    i.putExtra("event_id", test.get(TAG_ID));
                    startActivity(i);


                }
            });
        }
        else{}
        System.out.println("Out of updateList method.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_list, menu);
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
