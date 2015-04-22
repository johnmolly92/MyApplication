package test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.john.myapplication.R;
import com.example.john.myapplication.Welcome;

/**
 * Created by John on 22/04/2015.
 */
public class WelcomeTest extends ActivityInstrumentationTestCase2<Welcome> {
    private int SLEEP_TIME = 500;
    private final int TIMEOUT_TIME = 10000;

    private NavBarTest navBarTest;

    private Welcome mWelcome;

    private ImageButton homeButton, searchButton, nearMeButton,navHomeButton, navSearchButton, navNearMeButton, navWelcomeButton;

    public WelcomeTest(){
        super(Welcome.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        navBarTest = new NavBarTest();
        mWelcome = getActivity();

        homeButton = (ImageButton)mWelcome.findViewById(R.id.btnGoToHome);
        searchButton = (ImageButton)mWelcome.findViewById(R.id.btnGoToSearch);
        nearMeButton = (ImageButton)mWelcome.findViewById(R.id.btnGoToNearMe);

        navHomeButton = (ImageButton)mWelcome.findViewById(R.id.btnHome);
        navSearchButton = (ImageButton)mWelcome.findViewById(R.id.btnSearch);
        navNearMeButton = (ImageButton)mWelcome.findViewById(R.id.btnNearMe);
        navWelcomeButton = (ImageButton)mWelcome.findViewById(R.id.btnWelcome);
    }

    public void testPreconditions() {
        assertNotNull("mWelcome is null.", mWelcome);
        assertNotNull("navBarTest is null", navBarTest);

        assertNotNull("homeButton is null", homeButton);
        assertNotNull("searchButton is null", searchButton);
        assertNotNull("nearMeButton is null", nearMeButton);

        assertNotNull("navHomeButton is null", navHomeButton);
        assertNotNull("navSearchButton is null", navSearchButton);
        assertNotNull("navNearMeButton is null", navNearMeButton);
        assertNotNull("navWelcomeButton is null", navWelcomeButton);
    }

//    public void testSearchButton(){
//        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(Search.class.getName(), null, false);
//
//        clickButton(registerButton,mMainActivity);
//
//        Activity mRegister = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
//        // next activity is opened and captured.
//        assertNotNull(mRegister);
//        mRegister.finish();
//    }

    public void testNavHomeButton(){
        navBarTest.testHomeActivity(this, mWelcome, navHomeButton);
    }

    public void testNavSearchButton() {
        navBarTest.testSearchActivity(this, mWelcome, navSearchButton);
    }

    public void testNavNearMeButton(){
        navBarTest.testNearMeActivity(this, mWelcome, navNearMeButton);
    }

    public void testNavWelcomeButton(){
        navBarTest.testWelcomeActivity(this, mWelcome, navWelcomeButton);
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
