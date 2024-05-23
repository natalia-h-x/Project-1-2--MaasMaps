package manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import core.models.Location;
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
            Location location = db.getLocation("6211AB");
            assertEquals(50.857758901804, location.getLongitude(), 0.1);
            assertEquals(5.6909697778482, location.getLatitude(), 0.1);
        } catch (Exception e) {
            assert(true);
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
}