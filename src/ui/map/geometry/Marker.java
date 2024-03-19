package ui.map.geometry;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


public class Marker extends Component {
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int x = 10;
        int y = 10;
        Ellipse2D ellipse2d = new Ellipse2D.Float(x, y, 10, 10);
        g2.draw(ellipse2d);
    }
}
