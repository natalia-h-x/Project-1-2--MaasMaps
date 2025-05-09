package ui;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Paint;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.Timer;

import org.junit.jupiter.api.Test;

import algorithms.datastructures.AdjacencyListTest;
import core.datastructures.graph.Graph;
import core.models.BusStop;
import ui.map.geometry.ArrowStroke;
import ui.map.geometry.GeographicLine;
import ui.map.geometry.Line;
import ui.map.geometry.Line.Segment;
import ui.map.geometry.factories.LineFactory;

public class LineTest {
        private Graph<BusStop> adjacencyListGraph;

    public LineTest() {
        adjacencyListGraph = AdjacencyListTest.createTestGraph();
    }

    @Test
    public void createMarkerTest() {
        LineFactory.createArrowLine();
    }

    @Test
    public void lineTest() {
        points(adjacencyListGraph);
    }

    public <P extends Point2D> void points(Graph<P> adjacencyListGraph) {
        Line line = LineFactory.createLine(new ArrowStroke(0, 0, 0));
        GeographicLine geographicLine = LineFactory.createGeographicLine(new ArrowStroke(0, 0, 0));
        Paint paint = null;
        GeographicLine geographicLine2 = LineFactory.createGeographicLine(paint);

        line.setLocations(new ArrayList<>());
        line.setAnimatorTimer(new Timer(0, null));
        line.setAnimatedSegments(0);

        Segment segment = new Segment(null, null);
        Point2D point = null;

        for (P p1 : adjacencyListGraph) {
            point = p1;
            segment.setStart(p1);
            segment.setEnd(p1);
        }

        assertEquals(point, segment.getStart());
        assertEquals(point, segment.getEnd());

        line.iterator();

        Line line2 = line;
        line.hashCode();
        assertTrue(line.equals(line2));
    }
}
