package ui.map.geometry;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import lombok.Data;
import ui.map.geometry.interfaces.MapGraphics;

@Data
public class Radius implements MapGraphics {
    private int x;
    private int y;
    private int r;

    public Radius(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        Ellipse2D radius = new Ellipse2D.Double(x, y, r, r);
        g2.fill(radius);
    }
}
