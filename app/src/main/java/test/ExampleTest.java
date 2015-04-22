package test;

/**
 * Created by John on 21/04/2015.
 */
import android.test.InstrumentationTestCase;

public class ExampleTest  extends InstrumentationTestCase{
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected, reality);
    }
}