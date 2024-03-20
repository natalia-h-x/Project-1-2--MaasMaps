package ui.map.geometry;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import core.managers.MapManager;
import models.Location;

public class Marker extends Component implements MapIcon {
    private Location location;

    public Marker(Location location) {
        this.location = location;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Point point = MapManager.locationToPoint(location);
        double centerX = point.getX() - 2.5;
        double centerY = point.getY() - 2.5;

        Ellipse2D redEllipse = new Ellipse2D.Double(centerX, centerY, 5, 5);


        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(5.0f));
        g2.draw(redEllipse);


        double redCenterX = centerX + 2.5;
        double redCenterY = centerY + 2.5;


        int[] xPoints = {(int) redCenterX, (int) (redCenterX - 4), (int) (redCenterX + 4)};
        int[] yPoints = {(int) redCenterY + 9, (int) redCenterY, (int) redCenterY};

        Polygon triangle = new Polygon(xPoints, yPoints, 3);
        g2.setColor(Color.RED);
        g2.fillPolygon(triangle);


        double smallEllipseX = redCenterX - 2.5;
        double smallEllipseY = redCenterY - 2.5;

        Ellipse2D whiteEllipse = new Ellipse2D.Double(smallEllipseX, smallEllipseY, 5, 5);


        g2.setColor(Color.WHITE);
        g2.fill(whiteEllipse);


    }
}