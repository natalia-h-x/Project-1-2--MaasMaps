package fileaccess;

import models.Location;
import java.awt.Point;

public class Converter {
    private static final double MIN_LONGITUDE_MAASTRICHT = 5.64213;
    private static final double MAX_LATITUDE_MAASTRICHT = 50.90074;
    private static final int SCALE = 10000/2;

    public static Point convertedLocation(Location location) {
        /* int x =  (int) ((MAP_WIDTH/360.0) * (180 + lon));
        int y =  (int) ((MAP_HEIGHT/180.0) * (90 - lat)); */
        int latitude = (int) ((map.getWidth()/360.0) * (180 + location.getLongitude()));
        int longitude = (int) ((map.getHeight()/180.0) * (90 - location.getLatitude()));


        return new Point(latitude, longitude);
    }
}