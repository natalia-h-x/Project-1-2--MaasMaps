package core.managers;

import models.Location;
import java.awt.Point;

import core.Context;

public class MapManager {
    private static final int SCALE = 1000;
    private static final Location MAP_TOP_LEFT_LOCATION = new Location(50.90074, 5.64213);
    private static final Point MAP_TOP_LEFT_GLOBAL_XY = getGlobalXY(MAP_TOP_LEFT_LOCATION);
    private static final Location MAP_BOTTOM_RIGHT_LOCATION = new Location(50.815816, 5.753384);
    private static final Point MAP_BOTTOM_RIGHT_GLOBAL_XY = getGlobalXY(MAP_BOTTOM_RIGHT_LOCATION);

    private static final double CENTER_LATITUDE_MAASTRICHT = 50.8506844;
    private static final double RADIUS_MAASTRICHT_EARTH = 6365.368;

    public static Point locationToPoint(Location location) {
        Point world = getGlobalXY(location);

        int x = ((world.x - MAP_TOP_LEFT_GLOBAL_XY.x) / (MAP_BOTTOM_RIGHT_GLOBAL_XY.x - MAP_TOP_LEFT_GLOBAL_XY.x));
        int y = ((world.y - MAP_TOP_LEFT_GLOBAL_XY.x) / (MAP_BOTTOM_RIGHT_GLOBAL_XY.y - MAP_TOP_LEFT_GLOBAL_XY.y));

        System.out.println(x + ", " + y);

        return new Point(
            Context.getContext().getMap().getWidth() * x,
            Context.getContext().getMap().getHeight() * y
        );
    }

    private static Point getGlobalXY(Location location) {
        double x = (RADIUS_MAASTRICHT_EARTH * (location.getLongitude()) * Math.cos(CENTER_LATITUDE_MAASTRICHT));
        double y = (RADIUS_MAASTRICHT_EARTH * (location.getLatitude()));
        return new Point((int) x, (int) y);
    }
}