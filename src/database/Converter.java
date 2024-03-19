package database;

import models.Location;

public class Converter {
    private static final double LONGITUDE_MAASTRICHT = 5.64213;
    private static final double LATITUDE_MAASTRICHT = 50.90074;
    private static final int SCALE = 100000;

    public static Location convertedLocation(Location location) {
        
        double latitude =  - ((location.getLatitude() - LATITUDE_MAASTRICHT) * SCALE);
        double longitude =  ((location.getLongitude() - LONGITUDE_MAASTRICHT) * SCALE);

        return new Location(latitude, longitude);
    }
}