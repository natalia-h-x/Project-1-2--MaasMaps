package ui.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import models.Location;
import fileaccess.Converter;

public class Line extends Component{
    private transient List<Location> locations = new ArrayList<>();

    //take the locations as parameter
    public Line(Location... locations){
        this.locations.addAll(Arrays.asList(locations));
    }

    public void addLocation(Location loc) {
        locations.add(loc);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Get the each location to draw the lines
        g2.setPaint(new Color(001, 010, 100));
        float[] fa = {10, 10, 10};
        BasicStroke bs = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, fa, 10);
        g2.setStroke(bs);
        for (int i = 0; i < locations.size() - 1; i++) {
            Point p1 = Converter.convertedLocation(locations.get(i));
            Point p2 = Converter.convertedLocation(locations.get(i + 1));
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}

