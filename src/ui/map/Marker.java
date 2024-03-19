package ui.map;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.Ellipse2D;


public class Marker extends Component {
    private int y = 10;
    private int x = 10;

    public Marker(int y, int x) {
        this.y = y;
        this.x = x;
    }
    
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override 
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Ellipse2D ellipse2d = new Ellipse2D.Float(x, y, 10, 10);
        g2.setStroke(new BasicStroke(10.0f));
        g2.draw(ellipse2d);
    }
}
