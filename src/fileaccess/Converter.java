package fileaccess;

import models.Location;
import java.awt.Point;

public class Converter {
    private static final double MIN_LONGITUDE_MAASTRICHT = 5.64213;
    private static final double MAX_LATITUDE_MAASTRICHT = 50.90074;
    private static final int SCALE = 10;

    public static Point convertedLocation(Location location) {
        int latitude = (int) ((MAX_LATITUDE_MAASTRICHT - location.getLatitude()) * SCALE);
        int longitude = (int) ((location.getLongitude() - MIN_LONGITUDE_MAASTRICHT) * SCALE);

        return new Point(latitude, longitude);
    }
}