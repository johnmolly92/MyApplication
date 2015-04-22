package test;

import android.test.ActivityInstrumentationTestCase2;

import com.example.john.myapplication.SingleEvent;

/**
 * Created by John on 22/04/2015.
 */
public class SingleEventTest extends ActivityInstrumentationTestCase2<SingleEvent> {

    private MapHelper mapHelper;
    private SingleEvent mSingleEvent;

    public SingleEventTest(){
        super(SingleEvent.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        mapHelper = new MapHelper();
        mSingleEvent = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("mSingleEvent is null.", mSingleEvent);
        assertNotNull("mapHelper is null", mapHelper);
    }

    public void testMapExists(){
        mapHelper.testMapExists(mSingleEvent);
    }

    public void testMapVisibility(){
        mapHelper.testMapVisibility(mSingleEvent);
    }
}
