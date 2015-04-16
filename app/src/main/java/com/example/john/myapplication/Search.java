package com.example.john.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Search extends Activity implements AdapterView.OnItemSelectedListener{

    private String SEARCH_URL;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    private String age,type,music_genre,name;
    private String date = "-1";

    private EditText txtName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SEARCH_URL= getString(R.string.url_start) + "search.php";

        txtName = (EditText)findViewById(R.id.txtEventName);

        Spinner spinner = (Spinner) findViewById(R.id.event_type_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.event_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner ageSpinner = (Spinner) findViewById(R.id.event_age_spinner);
        ageSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> ageAdapter = ArrayAdapter.createFromResource(this,
                R.array.event_age_array, android.R.layout.simple_spinner_item);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageAdapter);

        Spinner musicSpinner = (Spinner) findViewById(R.id.music_genre_spinner);
        musicSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> musicAdapter = ArrayAdapter.createFromResource(this,
                R.array.event_music_array, android.R.layout.simple_spinner_item);
        musicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        musicSpinner.setAdapter(musicAdapter);


    }

    public void submitSearch(View v){
        name = txtName.getText().toString();
        System.out.println("Clicked the search button");

        Intent i = new Intent(Search.this, SearchList.class);

        if(age.equals("All ages"))
            age = "-1";
        System.out.println("Type: "+type+" Genre: "+music_genre+" Age: "+age+" Date: "+date+" Name: "+name);

        i.putExtra("event_type", type);
        i.putExtra("music_genre", music_genre);
        i.putExtra("age", age);
        i.putExtra("date", date);
        i.putExtra("name", name);

        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.event_type_spinner:
                type = parent.getItemAtPosition(position).toString();
                System.out.println("SELECTED: " + type);
                break;
            case R.id.event_age_spinner:
                age = parent.getItemAtPosition(position).toString();
                System.out.println("SELECTED: " + age);
                break;
            case R.id.music_genre_spinner:
                music_genre = parent.getItemAtPosition(position).toString();
                System.out.println("SELECTED: " + music_genre);
        }

    }



    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear +=1;
            String inputDate = year + "-" + checkDate(monthOfYear) + "-" + checkDate(dayOfMonth);
            ((Search)getActivity()).onDateSet(inputDate);
          //  System.out.println("DATE: " + inputDate);
        }
    }

    public void onDateSet(String date){
        this.date = date;
    }

    //Pads day and month with a leading zero for date comparison in database
    public static String checkDate(int num){
        return num <= 9?"0"+num:String.valueOf(num);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void openHomeActivity(View v)
    {
        Intent i = new Intent(Search.this, Home.class);
        startActivity(i);
    }

    public void openSearchActivity(View v)
    {
        Intent i = new Intent(Search.this, Search.class);
        startActivity(i);
    }

    public void openUsersActivity(View v)
    {
        Intent i = new Intent(Search.this, Users.class);
        startActivity(i);
    }

    public void openSettingsActivity(View v)
    {
        Intent i = new Intent(Search.this, Settings.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
