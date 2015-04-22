package test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.example.john.myapplication.MainActivity;
import com.example.john.myapplication.R;

/**
 * Created by John on 21/04/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private MainActivity mMainActivity;
    private EditText username, password;
    private Button loginButton, registerButton;

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

    public void testMyTest(){
        assertEquals(1,1);
    }

    public void testMainActivityTest(){
        assertEquals(1,1);
    }
}
