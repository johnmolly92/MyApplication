package test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.john.myapplication.R;
import com.example.john.myapplication.Search;
import com.example.john.myapplication.SearchList;

/**
 * Created by John on 22/04/2015.
 */
public class SearchTest extends ActivityInstrumentationTestCase2<Search> {

    private NavBarHelper navBarHelper;
    private Search mSearch;

    private Button searchButton;
    private EditText name, venue;
    private Spinner type, price, age, music;

    private final String TEST_VALUE = "test";
    private final int SPINNER_TEST_VALUE = 1;

    private final int SLEEP_TIME = 1000;
    private final int TIMEOUT_TIME = 20000;

    public SearchTest(){
        super(Search.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        navBarHelper = new NavBarHelper();
        mSearch = getActivity();

        searchButton = (Button)mSearch.findViewById(R.id.btnSubmitSearch);

        name = (EditText)mSearch.findViewById(R.id.txtEventName);
        venue = (EditText)mSearch.findViewById(R.id.txtVenue);

        type = (Spinner)mSearch.findViewById(R.id.event_type_spinner);
        price = (Spinner)mSearch.findViewById(R.id.event_price_spinner);
        age = (Spinner)mSearch.findViewById(R.id.event_age_spinner);
        music = (Spinner)mSearch.findViewById(R.id.music_genre_spinner);
    }



    public void testPreconditions() {
        assertNotNull("mWelcome is null.", mSearch);
        assertNotNull("navBarTest is null", navBarHelper);
        assertNotNull("searchButton is null", searchButton);
        assertNotNull("name is null", name);
        assertNotNull("venue is null", venue);
        assertNotNull("type is null", type);
        assertNotNull("price is null", price);
        assertNotNull("age is null", age);
        assertNotNull("music is null", music);
    }

    public void testSearchSubmit(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(SearchList.class.getName(), null, false);

        clickButton(searchButton,mSearch);

        Activity mSearchList = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIMEOUT_TIME);
        // next activity is opened and captured.
        assertNotNull(mSearchList);
        mSearchList.finish();
    }

    @UiThreadTest
    public void testNameInput(){
        name.setText(TEST_VALUE);
        String expectedValue = name.getText().toString();
        assertEquals(TEST_VALUE, expectedValue);
    }

    @UiThreadTest
    public void testVenueInput(){
        venue.setText(TEST_VALUE);
        String expectedValue = venue.getText().toString();
        assertEquals(TEST_VALUE,expectedValue);
    }

    @UiThreadTest
    public void testTypeSpinner(){
        type.setSelection(SPINNER_TEST_VALUE,true);
        int currentPosition = type.getSelectedItemPosition();
        assertEquals(SPINNER_TEST_VALUE, currentPosition);
    }

    @UiThreadTest
    public void testPriceSpinner(){
        price.setSelection(SPINNER_TEST_VALUE,true);
        int currentPosition = price.getSelectedItemPosition();
        assertEquals(SPINNER_TEST_VALUE, currentPosition);
    }

    @UiThreadTest
    public void testAgeSpinner(){
        age.setSelection(SPINNER_TEST_VALUE,true);
        int currentPosition = age.getSelectedItemPosition();
        assertEquals(SPINNER_TEST_VALUE, currentPosition);
    }

    @UiThreadTest
    public void testMusicSpinner(){
        music.setSelection(SPINNER_TEST_VALUE,true);
        int currentPosition = music.getSelectedItemPosition();
        assertEquals(SPINNER_TEST_VALUE, currentPosition);
    }

//
//    public void testNavHomeButton(){
//        navBarTest.testHomeActivity(this, mSearch);
//    }
//
//    public void testNavSearchButton() {
//        navBarTest.testSearchActivity(this, mSearch);
//    }
//
//    public void testNavNearMeButton(){
//        navBarTest.testNearMeActivity(this, mSearch);
//    }
//
//    public void testNavWelcomeButton(){
//        navBarTest.testWelcomeActivity(this, mSearch);
//    }

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
