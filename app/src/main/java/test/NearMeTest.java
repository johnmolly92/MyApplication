package test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;

import com.example.john.myapplication.NearMe;
import com.example.john.myapplication.R;
import com.example.john.myapplication.Welcome;
import com.google.android.gms.maps.MapFragment;

/**
 * Created by John on 15/04/2015.
 */
public class NearMeTest extends ActivityInstrumentationTestCase2<NearMe> {
    private NavBarTest navBarTest;
    private NearMe mNearMe;

    private MapFragment map;

    public NearMeTest(){
        super(NearMe.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        navBarTest = new NavBarTest();
        mNearMe = getActivity();

        map = (MapFragment)mNearMe.getFragmentManager().findFragmentById(R.id.map);
    }

    public void testPreconditions() {
        assertNotNull("mNearMe is null.", mNearMe);
        assertNotNull("navBarTest is null", navBarTest);
        assertNotNull("map is null",map);
    }

    public void testMapExists(){
        assertNotNull(map);
    }

    public void testMapVisibility(){
        assertTrue(map.isVisible());
    }

    //public void testNavHomeButton(){
//        navBarTest.testHomeActivity(this, mNearMe);
//    }

    public void testNavSearchButton() {
        navBarTest.testSearchActivity(this, mNearMe);
    }

    public void testNavNearMeButton(){
        navBarTest.testNearMeActivity(this, mNearMe);
    }

    public void testNavWelcomeButton(){
        navBarTest.testWelcomeActivity(this, mNearMe);
    }
}
