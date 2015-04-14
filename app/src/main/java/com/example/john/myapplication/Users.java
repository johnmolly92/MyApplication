package com.example.john.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Users extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
    }


    public void openHomeActivity(View v)
    {
        Intent i = new Intent(Users.this, Home.class);
        startActivity(i);
    }

    public void openSearchActivity(View v)
    {
        Intent i = new Intent(Users.this, Search.class);
        startActivity(i);
    }

    public void openUsersActivity(View v)
    {
        Intent i = new Intent(Users.this, Users.class);
        startActivity(i);
    }

    public void openSettingsActivity(View v)
    {
        Intent i = new Intent(Users.this, Settings.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_users, menu);
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
