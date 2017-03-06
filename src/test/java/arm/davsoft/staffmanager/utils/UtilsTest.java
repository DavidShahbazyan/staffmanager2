package arm.davsoft.staffmanager.utils;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by david on 8/22/16.
 */
public class UtilsTest {
    @Test
    @Ignore("DOES NOT NEED IN TESTING, YET.")
    public void concatStrings() throws Exception {

    }

    @Test
    @Ignore("DOES NOT NEED IN TESTING, YET.")
    public void concatStrings1() throws Exception {

    }

    @Test
    @Ignore("DOES NOT NEED IN TESTING, YET.")
    public void joinIntegers() throws Exception {
        assertEquals("1,2,3", Utils.joinIntegers(Arrays.asList(1, 2, 3)));
    }

    @Test
    @Ignore("DOES NOT NEED IN TESTING, YET.")
    public void toJsonArray() throws Exception {

    }

    @Test
    @Ignore("DOES NOT NEED IN TESTING, YET.")
    public void byteArrayToFile() throws Exception {
        String fileName = "/images/splashScreen.png";
        String fileExt = ".png";
        Utils.byteArrayToFile(Utils.inputStreamToByteArray(UtilsTest.class.getResourceAsStream(fileName)), fileExt);
        System.out.println("Exiting the application...");
    }

    @Test
    @Ignore("DOES NOT NEED IN TESTING, YET.")
    public void fileToByteArray() throws Exception {

    }

    @Test
    @Ignore("DOES NOT NEED IN TESTING, YET.")
    public void inputStreamToByteArray() throws Exception {

    }

}