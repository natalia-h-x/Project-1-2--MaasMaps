package core.managers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import models.Location;
import core.Context;

/**
 * This class represents the map manager that converts real world coordinates to x/y points in the map image.
 *
 * @author Sian Lodde
 * @author Alexandra Plishkin Islamgulova
 */
public class MapManager {
    private MapManager() {}

    private static final Location MAP_TOP_LEFT_LOCATION = new Location(50.90074, 5.64213);
    private static final Point MAP_TOP_LEFT_GLOBAL_XY = getGlobalXY(MAP_TOP_LEFT_LOCATION);
    private static final Location MAP_BOTTOM_RIGHT_LOCATION = new Location(50.815816, 5.753384);
    private static final Point MAP_BOTTOM_RIGHT_GLOBAL_XY = getGlobalXY(MAP_BOTTOM_RIGHT_LOCATION);

    private static final double CENTER_LATITUDE_MAASTRICHT = 50.8506844;
    private static final double RADIUS_MAASTRICHT_EARTH = 6365.368;

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

    public static void drawString(Graphics2D g2, String distance, Point2D center) {
        int offset = 1;
        g2.setPaint(Color.BLACK);
        g2.drawString(distance, (int) center.getX() - offset, (int) center.getY() - offset);
        g2.drawString(distance, (int) center.getX() - offset, (int) center.getY()         );
        g2.drawString(distance, (int) center.getX() - offset, (int) center.getY() + offset);
        g2.drawString(distance, (int) center.getX() + offset, (int) center.getY()         );
        g2.drawString(distance, (int) center.getX() + offset, (int) center.getY() - offset);
        g2.drawString(distance, (int) center.getX()         , (int) center.getY() - offset);
        g2.drawString(distance, (int) center.getX() + offset, (int) center.getY() + offset);
        g2.drawString(distance, (int) center.getX()         , (int) center.getY() + offset);

        // Then draw the text in white at the original position
        g2.setPaint(Color.WHITE);
        g2.drawString(distance, (int) center.getX(), (int) center.getY());
    }

}