package test;

import android.app.Activity;

import com.example.john.myapplication.R;
import com.google.android.gms.maps.MapFragment;

import junit.framework.TestCase;

/**
 * Created by John on 22/04/2015.
 */
public class MapHelper extends TestCase {

    private MapFragment map;

    public void testMapExists(Activity currentActivity){
        map = (MapFragment)currentActivity.getFragmentManager().findFragmentById(R.id.map);
        assertNotNull(map);
    }

    public void testMapVisibility(Activity currentActivity){
        map = (MapFragment)currentActivity.getFragmentManager().findFragmentById(R.id.map);
        assertTrue(map.isVisible());
    }
}
