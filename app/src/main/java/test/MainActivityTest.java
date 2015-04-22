package test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.example.john.myapplication.MainActivity;
import com.example.john.myapplication.R;
import com.example.john.myapplication.Register;
import com.example.john.myapplication.Welcome;

/**
 * Created by John on 11/04/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private MainActivity mMainActivity;
    private EditText username, password;
    private Button loginButton, registerButton;

    private final int SLEEP_TIME = 500;
    private final int TIMEOUT_TIME = 10000;

    public MainActivityTest(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        mMainActivity = getActivity();

        username = (EditText)mMainActivity.findViewById(R.id.txtUsername);
        password = (EditText)mMainActivity.findViewById(R.id.txtPassword);

        loginButton = (Button)mMainActivity.findViewById(R.id.btnLogin);
        registerButton = (Button)mMainActivity.findViewById(R.id.btnRegister);
    }

    public void testPreconditions() {
        assertNotNull("mMainActivity is null.", mMainActivity);
        assertNotNull("username is null", username);
        assertNotNull("password is null", password);
        assertNotNull("loginButton is null", loginButton);
        assertNotNull("registerButton is null", registerButton);
    }

    public void testOpenRegisterActivity(){
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(Register.class.getName(), null, false);

        clickButton(registerButton,mMainActivity);

        Activity mRegister = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mRegister);
        mRegister.finish();
    }

    public void testIncorrectLogin(){
        //CLick login button without entering a username or password
        clickButton(loginButton,mMainActivity);

        Activity activityAfterClick = getActivity();
        assertEquals(mMainActivity,activityAfterClick);
    }

    public void testCorrectLogin(){
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText("test");
                password.setText("test");
            }
        });


        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(Welcome.class.getName(), null, false);

        clickButton(loginButton,mMainActivity);

        Activity mWelcome = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mWelcome);
        mWelcome.finish();
    }

    public void clickButton (final Button button, Activity activity){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });
        try{
            Thread.sleep(SLEEP_TIME);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
