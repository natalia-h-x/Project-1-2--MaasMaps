package ui.map.geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import core.algorithms.datastructures.EdgeNode;
import core.algorithms.datastructures.Graph;
import lombok.Getter;
import lombok.Setter;
import ui.map.geometry.interfaces.AbstractMarkerFactory;
import ui.map.geometry.interfaces.MapGraphics;

@Setter
@Getter
public class Network extends Component implements MapGraphics {
    private transient AbstractMarkerFactory factory = new MarkerFactory();
    private transient List<MapGraphics> mapGraphics = new ArrayList<>();
    private transient Paint paint = new Color(001, 010, 100);
    private transient Stroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);

    public Network(Graph<Point2D> graph, AbstractMarkerFactory factory) {
        this(graph);
        this.factory = factory;
    }

    public Network(Graph<Point2D> graph) {
        for (Point2D p1 : graph) {
            if (p1 == null) continue;

            for (EdgeNode<Point2D> e : graph.neighbors(p1)) {
                Point2D p2 = e.getElement();

                if (p2 == null) continue;

                addLineSegment(p1, p2);
            }
        }

        for (Point2D p1 : graph) {
            if (p1 == null) continue;

            mapGraphics.add(factory.createMarker(p1));
        }
    }

    public void addLineSegment(Point2D p1, Point2D p2) {
        mapGraphics.add(new Line(p1, p2));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (MapGraphics graphic : mapGraphics) {
            graphic.paint(g2);
        }
    }
}
