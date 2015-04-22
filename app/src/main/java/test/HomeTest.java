package test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.example.john.myapplication.Home;
import com.example.john.myapplication.R;
import com.example.john.myapplication.SingleEvent;

/**
 * Created by John on 22/04/2015.
 */
public class HomeTest extends ActivityInstrumentationTestCase2<Home> {
    private NavBarTest navBarTest;
    private Home mHome;

    private ListView eventList;

    private final int TIMEOUT_TIME = 10000;

    public HomeTest(){
        super(Home.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        navBarTest = new NavBarTest();
        mHome = getActivity();
        eventList = (ListView)mHome.findViewById(android.R.id.list);
    }

    public void testPreconditions() {
        assertNotNull("mHome is null.", mHome);
        assertNotNull("navBarTest is null", navBarTest);
        assertNotNull("eventList is null", eventList);
    }

    public void testEventListExists(){
        assertNotNull(eventList);
    }

    public void testEventClick(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(SingleEvent.class.getName(), null, false);

        mHome.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                eventList.performItemClick(
                        eventList.getChildAt(0),
                        0,
                        eventList.getAdapter().getItemId(0));
            }
        });

        Activity mSingleEvent = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mSingleEvent);
        mSingleEvent.finish();
    }

//    public void testNavHomeButton(){
//        mHome = getActivity();
//        navBarTest.testHomeActivity(this, mHome);
//    }

//    public void testNavSearchButton() {
//       // mHome = getActivity();
//        navBarTest.testSearchActivity(this, mHome);
//    }

//    public void testNavNearMeButton(){
//       // mHome = getActivity();
//        navBarTest.testNearMeActivity(this, mHome);
//    }

//    public void testNavWelcomeButton(){
//       // mHome = getActivity();
//        navBarTest.testWelcomeActivity(this, mHome);
//    }
}
