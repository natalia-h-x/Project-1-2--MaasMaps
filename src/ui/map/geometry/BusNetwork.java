package ui.map.geometry;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import core.algorithms.datastructures.Graph;
import ui.map.geometry.interfaces.AbstractMarkerFactory;
import ui.map.geometry.interfaces.MapGraphics;

public class BusNetwork extends Component implements MapGraphics {
    private AbstractMarkerFactory factory = new MarkerFactory();
    private List<MapGraphics> graphics = new ArrayList<>();

    public BusNetwork(Graph<Point2D> graph, AbstractMarkerFactory factory) {
        this(graph);
        this.factory = factory;
    }

    public BusNetwork(Graph<Point2D> graph) {
        for (Point2D p1 : graph) {
            if (p1 == null) continue;

            for (Point2D p2 : graph.neighbors(p1)) {
                if (p2 == null) continue;

                // Set paint color to blue for the line
                addLineSegment(p1, p2);
            }
        }

        for (Point2D p1 : graph) {
            if (p1 == null) continue;
            graphics.add(factory.createMarker(p1));
        }
    }

    public void addLineSegment(Point2D p1, Point2D p2) {
        graphics.add(new Line(p1, p2));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (MapGraphics graphic : graphics) {
            graphic.paint(g2);
        }
    }
}
