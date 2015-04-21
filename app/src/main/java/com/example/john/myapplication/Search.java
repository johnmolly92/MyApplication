package com.example.john.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Calendar;



public class Search extends Activity implements AdapterView.OnItemSelectedListener{

    private String age,type,music_genre,name,venue,priceString;
    private long price;
    private String date = "-1";

    private EditText txtName;
    private EditText txtVenue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        txtName = (EditText)findViewById(R.id.txtEventName);
        txtVenue = (EditText)findViewById(R.id.txtVenue);

        Spinner typeSpinner = (Spinner) findViewById(R.id.event_type_spinner);
        Spinner ageSpinner = (Spinner) findViewById(R.id.event_age_spinner);
        Spinner musicSpinner = (Spinner) findViewById(R.id.music_genre_spinner);
        Spinner priceSpinner = (Spinner) findViewById(R.id.event_price_spinner);

        typeSpinner.setOnItemSelectedListener(this);
        ageSpinner.setOnItemSelectedListener(this);
        musicSpinner.setOnItemSelectedListener(this);
        priceSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.event_type_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> ageAdapter = ArrayAdapter.createFromResource(this,
                R.array.event_age_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> musicAdapter = ArrayAdapter.createFromResource(this,
                R.array.event_music_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> priceAdapter = ArrayAdapter.createFromResource(this,
                R.array.event_price_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        musicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(adapter);
        ageSpinner.setAdapter(ageAdapter);
        musicSpinner.setAdapter(musicAdapter);
        priceSpinner.setAdapter(priceAdapter);


    }

    public void submitSearch(View v){
        name = txtName.getText().toString();
        venue = txtVenue.getText().toString();

        Intent i = new Intent(Search.this, SearchList.class);

        if(age.equals("All ages"))
            age = "-1";

        System.out.println("SEARCH, before pass to intent: Type: "+type+" Genre: "+music_genre+" Age: "+age+" Date: "+date+" Name: "+name+" Venue: "+venue+" Price: "+priceString);

        i.putExtra("event_type", type);
        i.putExtra("music_genre", music_genre);
        i.putExtra("age", age);
        i.putExtra("date", date);
        i.putExtra("name", name);
        i.putExtra("venue", venue);
        i.putExtra("price", priceString);

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
                break;
            case R.id.event_price_spinner:
                price = parent.getItemIdAtPosition(position);
                priceString = price+"";
                System.out.println("SELECTED: " + priceString);
                break;
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
        //do nothing
    }

    public void openHomeActivity(View v)
    {
        Intent i = new Intent(Search.this, Home.class);
        startActivity(i);
    }

    //TODO take out the buttons for the screen your already on?
    public void openSearchActivity(View v)
    {
        Intent i = new Intent(Search.this, Search.class);
        startActivity(i);
    }

    public void openWelcomeActivity(View v)
    {
        Intent i = new Intent(Search.this, Welcome.class);
        startActivity(i);
    }

    public void openNearMeActivity(View v)
    {
        Intent i = new Intent(Search.this, NearMe.class);
        startActivity(i);
    }
}
