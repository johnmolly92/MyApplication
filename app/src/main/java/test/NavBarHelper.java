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
 * Created by John on 11/04/2015.
 */
public class NavBarHelper extends TestCase {
    private final int SLEEP_TIME = 1000;
    private final int TIMEOUT_TIME = 20000;

    public void testHomeActivity(ActivityTestCase atc, Activity currentActivity){
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = atc.getInstrumentation().addMonitor(Home.class.getName(), null, false);

        final ImageButton homeButton = (ImageButton)currentActivity.findViewById(R.id.btnHome);

        clickButton(homeButton,currentActivity);

        Activity mHome = atc.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mHome);
        mHome.finish();
    }

    public void testSearchActivity(ActivityTestCase atc, Activity currentActivity){
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = atc.getInstrumentation().addMonitor(Search.class.getName(), null, false);

        final ImageButton searchButton = (ImageButton)currentActivity.findViewById(R.id.btnSearch);

        clickButton(searchButton,currentActivity);

        Activity mSearch = atc.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mSearch);
        mSearch.finish();
    }

    public void testNearMeActivity(ActivityTestCase atc, Activity currentActivity){
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = atc.getInstrumentation().addMonitor(NearMe.class.getName(), null, false);

        final ImageButton nearMeButton = (ImageButton)currentActivity.findViewById(R.id.btnNearMe);

        clickButton(nearMeButton,currentActivity);

        Activity mNearMe = atc.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mNearMe);
        mNearMe.finish();
    }

    public void testWelcomeActivity(ActivityTestCase atc, Activity currentActivity){
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = atc.getInstrumentation().addMonitor(Welcome.class.getName(), null, false);

        final ImageButton welcomeButton = (ImageButton)currentActivity.findViewById(R.id.btnWelcome);

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
