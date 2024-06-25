package ui.map.geometry;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import core.managers.map.MapManager;
import lombok.Data;
import ui.map.geometry.interfaces.MapGraphics;

@Data
public class Radius implements MapGraphics {
    private int x;
    private int y;
    private double r;

    /**
     * Constructs a Radius object.
     *
     * @param x center x position of the Radius
     * @param y center y position of the Radius
     * @param r radius in meters
     */
    public Radius(int x, int y, double r) {
        this.x = x;
        this.y = y;
        this.r = MapManager.meterToPixels(r);
    }

    public void paint(Graphics2D g2) {
        Ellipse2D radius = new Ellipse2D.Double(x - r, y - r, r * 2, r * 2);
        g2.draw(radius);
    }
}
