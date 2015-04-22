package test;

import android.test.ActivityInstrumentationTestCase2;
import com.example.john.myapplication.Home;

/**
 * Created by John on 14/04/2015.
 */
public class HomeTest extends ActivityInstrumentationTestCase2<Home> {
    private NavBarHelper navBarHelper;
    private ListHelper listHelper;
    private Home mHome;

    public HomeTest(){
        super(Home.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        navBarHelper = new NavBarHelper();
        listHelper = new ListHelper();
        mHome = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("mHome is null.", mHome);
        assertNotNull("navBarTest is null", navBarHelper);
        assertNotNull("listHelper is null", listHelper);
    }

    public void testEventListAvailable(){
        listHelper.testListNotNull(mHome);
    }

    public void testEventListClick(){
        listHelper.testClick(this,mHome);
    }

    public void testNavHomeButton(){
        mHome = getActivity();
        navBarHelper.testHomeActivity(this, mHome);
    }

    public void testNavSearchButton() {
       // mHome = getActivity();
        navBarHelper.testSearchActivity(this, mHome);
    }

    public void testNavNearMeButton(){
       // mHome = getActivity();
        navBarHelper.testNearMeActivity(this, mHome);
    }

    public void testNavWelcomeButton(){
       // mHome = getActivity();
        navBarHelper.testWelcomeActivity(this, mHome);
    }
}
