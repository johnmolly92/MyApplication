package test;

import android.test.ActivityInstrumentationTestCase2;

import com.example.john.myapplication.NearMe;
import com.example.john.myapplication.R;
import com.google.android.gms.maps.MapFragment;

/**
 * Created by John on 15/04/2015.
 */
public class NearMeTest extends ActivityInstrumentationTestCase2<NearMe> {
    private NavBarHelper navBarHelper;
    private MapHelper mapHelper;
    private NearMe mNearMe;

    public NearMeTest(){
        super(NearMe.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        navBarHelper = new NavBarHelper();
        mapHelper = new MapHelper();
        mNearMe = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("mNearMe is null.", mNearMe);
        assertNotNull("navBarTest is null", navBarHelper);
    }

    public void testMapExists(){
        mapHelper.testMapExists(mNearMe);
    }

    public void testMapVisibility(){
        mapHelper.testMapVisibility(mNearMe);
    }

    //public void testNavHomeButton(){
//        navBarTest.testHomeActivity(this, mNearMe);
//    }

    public void testNavSearchButton() {
        navBarHelper.testSearchActivity(this, mNearMe);
    }

    public void testNavNearMeButton(){
        navBarHelper.testNearMeActivity(this, mNearMe);
    }

    public void testNavWelcomeButton(){
        navBarHelper.testWelcomeActivity(this, mNearMe);
    }
}
