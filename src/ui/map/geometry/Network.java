package ui.map.geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.algorithms.datastructures.EdgeNode;
import core.algorithms.datastructures.Graph;
import lombok.Getter;
import lombok.Setter;
import ui.map.geometry.factories.MarkerFactory;
import ui.map.geometry.interfaces.AbstractMarkerFactory;
import ui.map.geometry.interfaces.MapGraphics;

@Setter
@Getter
public class Network implements MapGraphics {
    private AbstractMarkerFactory factory = new MarkerFactory();
    private List<MapGraphics> mapGraphics = new ArrayList<>();
    private Paint paint = new Color(001, 010, 100);
    private Stroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);

    public <P extends Point2D> Network(Graph<P> graph, AbstractMarkerFactory factory) {
        this(graph);
        this.factory = factory;
    }

    public <P extends Point2D> Network(Graph<P> graph) {
        createLines(graph);

        for (P p1 : graph) {
            if (p1 == null)
                continue;

            addGraphic(createMarker(p1));
        }
    }

    public <P extends Point2D> void createLines(Graph<P> graph) {
        for (P p1 : graph) {
            if (p1 == null)
                continue;

            for (EdgeNode<P> e : graph.neighbors(p1)) {
                P p2 = e.getElement();

                if (p2 == null)
                    continue;

                Line lineSegment = createLineSegment(p1, p2);

                if (lineSegment != null)
                    addGraphic(lineSegment);
            }
        }
    }

    public void clear() {
        mapGraphics.clear();
    }

    public void addGraphic(MapGraphics... graphic) {
        mapGraphics.addAll(Arrays.asList(graphic));
    }

    public Marker createMarker(Point2D p1) {
        return factory.createMarker(p1);
    }

    public Line createLineSegment(Point2D... points) {
        Line segment = new Line(points);
        segment.setPaint(paint);
        segment.setStroke(stroke);

        return segment;
    }

    @Override
    public void paint(Graphics2D g2) {
        for (MapGraphics graphic : mapGraphics) {
            graphic.paint(g2);
        }
    }
}
