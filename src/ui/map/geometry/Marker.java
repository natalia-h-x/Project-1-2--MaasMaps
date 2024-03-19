package ui.map.geometry;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.BasicStroke;
import java.awt.geom.Ellipse2D;

import core.managers.MapManager;
import models.Location;

public class Marker extends Component {
    private Location location;

    public Marker(Location location) {
        this.location = location;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Point point = MapManager.locationToPoint(location);
        Ellipse2D ellipse2d = new Ellipse2D.Double(point.getX(), point.getY(), 10, 10);

        System.out.println("Point " + point.x + ", " + point.y + " placed!");

        g2.setStroke(new BasicStroke(10.0f));
        g2.draw(ellipse2d);
    }
}
