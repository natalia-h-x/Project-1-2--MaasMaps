package ui.map.geometry;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import core.managers.MapManager;
import models.Location;

/**
 * This class represents icons that are different types of markers in the map.
 * @author Alexandra Plishkin Islamgulova
 * @author Meriç Uruş
 */
public class Marker extends Component implements MapIcon {
    private transient int markerOffsetY = 10;
    private transient int innerSize = 7;
    private transient Location location;
    private transient int size = 5;

    public Marker(Location location) {
        this.location = location;
    }

    public Location getMarkerLocation(){
        return location;
    }

    public Point getIconLocation(){
        Point point = MapManager.locationToPoint(location);
        double redCenterX = point.x;
        double redCenterY = point.y - markerOffsetY;
        return new Point((int)redCenterX, (int)redCenterY);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Point point = MapManager.locationToPoint(location);
        Point iconPoint = getIconLocation();

        double topLeftX = iconPoint.getX() - size / 2.0;
        double topLeftY = iconPoint.getY() - size / 2.0;
        Ellipse2D redEllipse = new Ellipse2D.Double(topLeftX, topLeftY, size, size);

        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(size));
        g2.draw(redEllipse);

        int[] xPoints = {(int) iconPoint.getX(), (int) (iconPoint.getX() - 4), (int) (iconPoint.getX() + 4)};
        int[] yPoints = {point.y, (int)  iconPoint.getY(),(int) iconPoint.getY()};

        Polygon triangle = new Polygon(xPoints, yPoints, 3);
        g2.setColor(Color.RED);
        g2.fillPolygon(triangle);

        double smallEllipseX = iconPoint.getX() - innerSize / 2.0;
        double smallEllipseY = iconPoint.getY() - innerSize / 2.0;
        Ellipse2D whiteEllipse = new Ellipse2D.Double(smallEllipseX, smallEllipseY, innerSize, innerSize);

        g2.setColor(Color.WHITE);
        g2.fill(whiteEllipse);
    }
}