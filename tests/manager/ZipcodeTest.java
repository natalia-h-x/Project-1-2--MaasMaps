package manager;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import core.models.Location;
import core.models.ZipCode;
import core.zipcode.APIRequest;
import core.zipcode.ZipCodeDatabase;

public class ZipcodeTest {
    ZipCodeDatabase db;

    public ZipcodeTest() {
        db = new ZipCodeDatabase();
    }

    @Test
    public void gettingLocationCSVTest() {
        Location location = db.getLocation("6227XB");
        assertEquals(50.839116, location.getLatitude());
        assertEquals(5.734282, location.getLongitude());
    }

    @Test
    public void gettingLocationAPITest() {
        try {
            Location location = new APIRequest().getLocation("6229EN");
            assertEquals(50.857758901804, location.getLatitude(), 0.1);
            assertEquals(5.6909697778482, location.getLongitude(), 0.1);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void gettingLocationFromInvalidZipcode() {
        try {
            Location location = db.getLocation("232FG2");
        } catch (IllegalArgumentException e) {
            assert(true);
        }
    }

    @Test
    public void zipCodeTest() {
        ZipCode zipCode = new ZipCode("6227XBA", new Location(50.839116, 5.734282));

        assertFalse(ZipCode.isValid(zipCode.getCode()));
        zipCode.setCode("62D7XB");
        assertFalse(ZipCode.isValid(zipCode.getCode()));
        zipCode.setCode("62276B");
        assertFalse(ZipCode.isValid(zipCode.getCode()));
        zipCode.setCode("62276B");
        assertFalse(ZipCode.isValid(zipCode.getCode()));
        zipCode.setCode("622!FX");
        assertFalse(ZipCode.isValid(zipCode.getCode()));
        zipCode.setCode("6227{B");
        assertFalse(ZipCode.isValid(zipCode.getCode()));
        zipCode.setCode("6227XB");
        zipCode.setLocation(new Location(50.839116, 5.734282));
        assertEquals(-412058659, zipCode.hashCode());
        ZipCode zipCode1 = new ZipCode("6227XB", new Location(50.839116, 5.734282));
        assertTrue(zipCode1.equals(zipCode));
        assertTrue(zipCode.toString().equals(zipCode.toString()));
        zipCode1.setCode("6226AB");
        assertFalse(zipCode.equals(zipCode1));
    }
}