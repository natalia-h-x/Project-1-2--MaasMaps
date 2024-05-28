package ui.map.geometry.factories;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import javax.swing.plaf.ColorUIResource;

import core.models.Location;
import core.models.Time;
import ui.map.geometry.ArrowStroke;
import ui.map.geometry.GeographicLine;
import ui.map.geometry.Line;

public class LineFactory {
    private LineFactory() {}

    public static Line createArrowLine(Point2D... points) {
        return new Line(new Color(0, 0, 255), new ArrowStroke(1, 5, 1), points);
    }

    public static GeographicLine createGeographicArrowLine(Time[] times, Location... points) {
        return new GeographicLine(times, new Color(0, 0, 255), new ArrowStroke(1, 5, 1), points);
    }

    public static GeographicLine createGeographicArrowLine(Location... points) {
        return createGeographicArrowLine(new Time[0], points);
    }

    public static Line createResultsLine(Point2D... locations){
        //change the stroke type here
        Stroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);
        return new Line(new ColorUIResource(0,0,1), stroke , locations);
    }
}
