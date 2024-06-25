package core.managers.map;

import java.awt.Point;
import java.awt.geom.Point2D;

import core.Context;
import core.datastructures.graph.Graph;
import core.managers.database.gtfs.GTFSManager;
import core.models.Location;

/**
 * This class represents the map manager that converts real world coordinates to x/y points in the map image.
 *
 * @author Sian Lodde
 * @author Alexandra Plishkin Islamgulova
 */
public class MapManager {
    private MapManager() {}

    public static final Location MAP_TOP_LEFT_LOCATION = new Location(50.90074, 5.64213);
    protected static final Point MAP_TOP_LEFT_GLOBAL_XY = getGlobalXY(MAP_TOP_LEFT_LOCATION);
    public static final Location MAP_BOTTOM_RIGHT_LOCATION = new Location(50.815816, 5.753384);
    protected static final Point MAP_BOTTOM_RIGHT_GLOBAL_XY = getGlobalXY(MAP_BOTTOM_RIGHT_LOCATION);
    private   static       Point MAP_TOP_LEFT_XY;
    private   static       Point MAP_BOTTOM_RIGHT_XY;
    private   static      double PIXEL_SIZE_METER = -1;

    private static final double CENTER_LATITUDE_MAASTRICHT = 50.8506844;
    private static final double RADIUS_MAASTRICHT_EARTH = 6365.368;

    public static Point getMapTopLeftXy() {
        if (MAP_TOP_LEFT_XY == null)
            MAP_TOP_LEFT_XY = locationToPoint(MAP_TOP_LEFT_LOCATION);

        return MAP_TOP_LEFT_XY;
    }

    public static Point getMapBottomRightXy() {
        if (MAP_BOTTOM_RIGHT_XY == null)
            MAP_BOTTOM_RIGHT_XY = locationToPoint(MAP_BOTTOM_RIGHT_LOCATION);

        return MAP_BOTTOM_RIGHT_XY;
    }

    public static double getPixelSize() {
        if (PIXEL_SIZE_METER == -1)
            PIXEL_SIZE_METER = Context.getContext().getMap().getMapWidth() / MAP_TOP_LEFT_LOCATION.distance(new Location(MAP_TOP_LEFT_LOCATION.getLatitude(), MAP_BOTTOM_RIGHT_LOCATION.getLongitude()));

        return PIXEL_SIZE_METER;
    }

    public static double meterToPixels(double meter) {
        return meter * getPixelSize();
    }

    public static Point locationToPoint(Location location) {
        Point world = getGlobalXY(location);

        /** Convert world coordinate to range from 0 - 1 as u and v (@see shader uv) */
        double u = ((double) (world.x - MAP_TOP_LEFT_GLOBAL_XY.x) / (MAP_BOTTOM_RIGHT_GLOBAL_XY.x - MAP_TOP_LEFT_GLOBAL_XY.x));
        double v = ((double) (world.y - MAP_TOP_LEFT_GLOBAL_XY.y) / (MAP_BOTTOM_RIGHT_GLOBAL_XY.y - MAP_TOP_LEFT_GLOBAL_XY.y));

        /** Multiply uv with the screen width and height to get screenX and screenY */
        int screenX = (int) (Context.getContext().getMap().getMapWidth() * u);
        int screenY = (int) (Context.getContext().getMap().getMapHeight() * v);

        return new Point(screenX, screenY);
    }

    private static Point getGlobalXY(Location location) {
        double x = (RADIUS_MAASTRICHT_EARTH * (location.getLongitude()) * Math.cos(CENTER_LATITUDE_MAASTRICHT));
        double y = (RADIUS_MAASTRICHT_EARTH * (location.getLatitude()));

        return new Point((int) x, (int) y);
    }

    public static Graph<Point2D> getBusGraph() {
        return GTFSManager.getBusGraph();
    }
}