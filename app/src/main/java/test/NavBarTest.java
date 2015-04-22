package test;

import android.app.Activity;
import android.app.Instrumentation;
import android.media.Image;
import android.test.ActivityTestCase;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.john.myapplication.Home;
import com.example.john.myapplication.NearMe;
import com.example.john.myapplication.R;
import com.example.john.myapplication.Register;
import com.example.john.myapplication.Search;
import com.example.john.myapplication.Welcome;

import junit.framework.TestCase;

/**
 * Created by John on 22/04/2015.
 */
public class NavBarTest extends TestCase {
    private int SLEEP_TIME = 500;
    private final int TIMEOUT_TIME = 10000;

    public void testHomeActivity(ActivityTestCase atc, Activity currentActivity, ImageButton homeButton){
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = atc.getInstrumentation().addMonitor(Home.class.getName(), null, false);

       // final ImageButton homeButton = (ImageButton)currentActivity.findViewById(R.id.btnHome);

        clickButton(homeButton,currentActivity);

        Activity mHome = atc.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mHome);
        mHome.finish();
    }

    public void testSearchActivity(ActivityTestCase atc, Activity currentActivity, ImageButton searchButton){
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = atc.getInstrumentation().addMonitor(Search.class.getName(), null, false);

        //final ImageButton searchButton = (ImageButton)currentActivity.findViewById(R.id.btnSearch);

        clickButton(searchButton,currentActivity);

        Activity mSearch = atc.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mSearch);
        mSearch.finish();
    }

    public void testNearMeActivity(ActivityTestCase atc, Activity currentActivity, ImageButton nearMeButton){
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = atc.getInstrumentation().addMonitor(NearMe.class.getName(), null, false);

      //  final ImageButton searchButton = (ImageButton)currentActivity.findViewById(R.id.btnNearMe);

        clickButton(nearMeButton,currentActivity);

        Activity mNearMe = atc.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mNearMe);
        mNearMe.finish();
    }

    public void testWelcomeActivity(ActivityTestCase atc, Activity currentActivity, ImageButton welcomeButton){
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = atc.getInstrumentation().addMonitor(Welcome.class.getName(), null, false);

        //  final ImageButton searchButton = (ImageButton)currentActivity.findViewById(R.id.btnNearMe);

        clickButton(welcomeButton,currentActivity);

        Activity mWelcome = atc.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mWelcome);
        mWelcome.finish();
    }

    public void clickButton (final ImageButton button, Activity activity){
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
