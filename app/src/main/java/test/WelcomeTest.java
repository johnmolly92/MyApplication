package test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.john.myapplication.Home;
import com.example.john.myapplication.NearMe;
import com.example.john.myapplication.R;
import com.example.john.myapplication.Search;
import com.example.john.myapplication.Welcome;

/**
 * Created by John on 22/04/2015.
 */
public class WelcomeTest extends ActivityInstrumentationTestCase2<Welcome> {
    private final int SLEEP_TIME = 500;
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

    public void testSearchButton(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(Search.class.getName(), null, false);

        clickButton(searchButton, mWelcome);

        Activity mSearch = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mSearch);
        mSearch.finish();
    }

    public void testHomeButton(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(Home.class.getName(), null, false);

        clickButton(homeButton, mWelcome);

        Activity mHome = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mHome);
        mHome.finish();
    }

    public void testNearMeButton(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(NearMe.class.getName(), null, false);

        clickButton(nearMeButton, mWelcome);

        Activity mNearMe = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mNearMe);
        mNearMe.finish();
    }

//    public void testNavHomeButton(){
//        navBarTest.testHomeActivity(this, mWelcome);
//    }

    public void testNavSearchButton() {
        navBarTest.testSearchActivity(this, mWelcome);
    }

    public void testNavNearMeButton(){
        navBarTest.testNearMeActivity(this, mWelcome);
    }

    public void testNavWelcomeButton(){
        navBarTest.testWelcomeActivity(this, mWelcome);
    }

    public void clickButton (final ImageButton  imageButton, Activity activity){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageButton.performClick();
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
