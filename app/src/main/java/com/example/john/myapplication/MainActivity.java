package com.example.john.myapplication;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class MainActivity extends Activity implements OnClickListener{

    private EditText user, pass;
    private Button mSubmit, mRegister;

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    //php login script location:
    private String LOGIN_URL;

    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LOGIN_URL= getString(R.string.url_start) + "login.php";

        //setup input fields
        user = (EditText)findViewById(R.id.txtUsername);
        pass = (EditText)findViewById(R.id.txtPassword);

        //setup buttons
        mSubmit = (Button)findViewById(R.id.btnLogin);
        mRegister = (Button)findViewById(R.id.btnRegister);

        //register listeners
        mSubmit.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // determine which button was pressed:
        switch (v.getId()) {
            case R.id.btnLogin:
                new AttemptLogin().execute();
                break;

            case R.id.btnRegister:
                Intent i = new Intent(this, Register.class);
                startActivity(i);
                break;

            default:
                break;
        }
    }

    //AsyncTask is a seperate thread than the thread that runs the GUI
    //Any type of networking should be done with asynctask.
    class AttemptLogin extends AsyncTask<String, String, String> {

        //three methods get called, first preExecture, then do in background, and once do
        //in back ground is completed, the onPost execture method will be called.

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {

            // Check for success tag
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");
                // getting details by making HTTP request

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());

                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(MainActivity.this);
                    Editor edit = sp.edit();
                    edit.putString("username", username);
                    edit.commit();

                    Intent i = new Intent(MainActivity.this, Welcome.class);
                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
        }

    }

}
