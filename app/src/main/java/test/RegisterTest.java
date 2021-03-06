package test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.example.john.myapplication.MainActivity;
import com.example.john.myapplication.R;
import com.example.john.myapplication.Register;

/**
 * Created by John on 11/04/2015.
 */
public class RegisterTest extends ActivityInstrumentationTestCase2<Register> {

    private Register mRegister;
    private EditText username, password;
    private Button registerButton;

    private final int SLEEP_TIME = 1000;
    private final int TIMEOUT_TIME = 10000;

    public RegisterTest(){
        super(Register.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        mRegister = getActivity();

        username = (EditText)mRegister.findViewById(R.id.username);
        password = (EditText)mRegister.findViewById(R.id.password);

        registerButton = (Button)mRegister.findViewById(R.id.register);
    }

    public void testPreconditions() {
        assertNotNull("mRegister is null.", mRegister);
        assertNotNull("username is null", username);
        assertNotNull("password is null", password);
        assertNotNull("registerButton is null", registerButton);
    }

    public void testRegisterEmpty(){
        clickButton(registerButton,mRegister);

        Activity activityAfterClick = getActivity();
        assertEquals(mRegister,activityAfterClick);
    }

    public void testRegisterInputTaken(){
        mRegister.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText("test");
                password.setText("test");
            }
        });

        clickButton(registerButton, mRegister);

        Activity activityAfterClick = getActivity();
        assertEquals(mRegister,activityAfterClick);
    }

    //This username/password must be removed from db after test
    public void testRegister(){
        mRegister.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText("registertestusername");
                password.setText("registertestpassword");
            }
        });

       // Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

        clickButton(registerButton, mRegister);

        Activity activityAfterClick = getActivity();
       // Activity mMainActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(activityAfterClick);
        activityAfterClick.finish();
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
