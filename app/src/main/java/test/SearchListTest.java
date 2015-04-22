package test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityTestCase;

import com.example.john.myapplication.SearchList;

/**
 * Created by John on 22/04/2015.
 */
public class SearchListTest extends ActivityInstrumentationTestCase2<SearchList> {

    private ListHelper listHelper;
    private SearchList mSearchList;

    public SearchListTest(){
        super(SearchList.class);
    }

    @Override
    public void setUp()throws Exception{
        super.setUp();

        Intent i = new Intent();
        i.putExtra("event_type", "All types");
        i.putExtra("music_genre", "All Music");
        i.putExtra("age", "-1");
        i.putExtra("date", "-1");
        i.putExtra("name", "");
        i.putExtra("venue", "");
        i.putExtra("price", "0");

        setActivityIntent(i);

        listHelper = new ListHelper();
        mSearchList = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("mSearchList is null.", mSearchList);
        assertNotNull("listHelper is null", listHelper);
    }

    public void testEventClick(){
        listHelper.testClick(this,mSearchList);
    }

    public void testEventListNotNull(){
        listHelper.testListNotNull(mSearchList);
    }

}
