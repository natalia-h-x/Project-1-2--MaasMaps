package ui.map.geometry.factories;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import core.models.Location;
import core.models.gtfs.Time;
import core.models.transport.Transport;
import ui.map.geometry.ArrowStroke;
import ui.map.geometry.GeographicLine;
import ui.map.geometry.Line;

public class LineFactory {
    private LineFactory() {}

    public static Line emptyLine() {
        return createLine();
    }

    public static GeographicLine emptyGeographicLine() {
        return createGeographicLine();
    }

    public static Line createLine(Paint paint, Stroke stroke, Point2D... points) {
        Line line = new Line(points);
        line.setPaint(paint);
        line.setStroke(stroke);

        return line;
    }

    public static Line createLine(Point2D... points) {
        return new Line(points);
    }

    public static Line createLine(Paint paint, Point2D... points) {
        Line line = new Line(points);
        line.setPaint(paint);

        return line;
    }

    public static Line createLine(Stroke stroke, Point2D... points) {
        Line line = new Line(points);
        line.setStroke(stroke);

        return line;
    }

    public static GeographicLine createGeographicLine(Location... locations) {
        return new GeographicLine(locations);
    }

    public static GeographicLine createGeographicLine(Paint paint, Stroke stroke, Time[] times, Location... locations) {
        GeographicLine line = new GeographicLine(locations);
        line.setPaint(paint);
        line.setStroke(stroke);
        line.setTimes(times);

        return line;
    }

    public static GeographicLine createGeographicLine(Paint paint, Stroke stroke, Location... locations) {
        GeographicLine line = new GeographicLine(locations);
        line.setPaint(paint);
        line.setStroke(stroke);

        return line;
    }

    public static GeographicLine createGeographicLine(Time[] times, Location... locations) {
        GeographicLine line = new GeographicLine(locations);
        line.setTimes(times);

        return line;
    }

    public static GeographicLine createGeographicLine(Paint paint, Time[] times, Location... locations) {
        GeographicLine line = new GeographicLine(locations);
        line.setPaint(paint);
        line.setTimes(times);

        return line;
    }

    public static GeographicLine createGeographicLine(Paint paint, Location... locations) {
        GeographicLine line = new GeographicLine(locations);
        line.setPaint(paint);

        return line;
    }

    public static GeographicLine createGeographicLine(Stroke stroke, Time[] times, Location... locations) {
        GeographicLine line = new GeographicLine(locations);
        line.setStroke(stroke);
        line.setTimes(times);

        return line;
    }

    public static GeographicLine createGeographicLine(Stroke stroke, Location... locations) {
        GeographicLine line = new GeographicLine(locations);
        line.setStroke(stroke);

        return line;
    }

    public static Line createArrowLine(Point2D... points) {
        return createLine(new Color(0, 0, 255), new ArrowStroke(1, 5, 1), points);
    }

    public static GeographicLine createGeographicArrowLine(Time[] times, Location... points) {
        return createGeographicLine(new Color(0, 0, 255), new ArrowStroke(1, 5, 1), times, points);
    }

    public static GeographicLine createGeographicArrowLine(Location... points) {
        return createGeographicArrowLine(new Time[0], points);
    }

    public static GeographicLine createResultsLine(Transport... transports) {
        List<Location> locations = new ArrayList<>();
        List<Time> times = new ArrayList<>();

        for (Transport transport : transports) {
            locations.add(transport.getStart());
            times.add(transport.totalTime());
        }

        locations.add(transports[transports.length - 1].getDestination());

        Stroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);
        return createGeographicLine(Color.BLUE, stroke, times.toArray(Time[]::new), locations.toArray(Location[]::new));
    }

    public static Line createResultsLine(Point2D... locations) {
        //change the stroke type here
        Stroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);
        return createLine(Color.BLUE, stroke, locations);
    }

    public static GeographicLine createResultsLine(Time[] times, Location... locations) {
        //change the stroke type here
        Stroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);
        return createGeographicLine(Color.BLUE, stroke, times, locations);
    }
}
