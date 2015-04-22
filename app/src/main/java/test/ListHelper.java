package test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityTestCase;
import android.widget.ListView;

import com.example.john.myapplication.SingleEvent;

import junit.framework.TestCase;

/**
 * Created by John on 22/04/2015.
 */
public class ListHelper extends TestCase {

    private final int SLEEP_TIME = 1000;
    private final int TIMEOUT_TIME = 20000;

    public void testClick(ActivityTestCase atc, Activity currentActivity){
        Instrumentation.ActivityMonitor activityMonitor = atc.getInstrumentation().addMonitor(SingleEvent.class.getName(), null, false);

        final ListView eventList = (ListView)currentActivity.findViewById(android.R.id.list);;

        currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                eventList.performItemClick(
                        eventList.getChildAt(0),
                        0,
                        eventList.getAdapter().getItemId(0));
            }
        });
        try{
            Thread.sleep(SLEEP_TIME);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        Activity mSingleEvent = atc.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mSingleEvent);
        mSingleEvent.finish();
    }

    public void testListNotNull(Activity currentActivity){
        ListView eventList = (ListView)currentActivity.findViewById(android.R.id.list);
        assertNotNull(eventList);

    }
}
