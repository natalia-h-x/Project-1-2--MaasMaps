package database;

import models.Location;

public class Converter {
    private static final double MIN_LONGITUDE_MAASTRICHT = 5.64213;
    private static final double MAX_LATITUDE_MAASTRICHT = 50.90074;
    private static final int SCALE = 100000;

    public static Location convertedLocation(Location location) {
        
        double latitude =  ((MAX_LATITUDE_MAASTRICHT - location.getLatitude()) * SCALE);
        double longitude =  ((location.getLongitude() - MIN_LONGITUDE_MAASTRICHT) * SCALE);

        return new Location(latitude, longitude);
    }
}