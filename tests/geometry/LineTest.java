package geometry;

import org.junit.jupiter.api.Test;
import ui.map.geometry.Line;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LineTest {

    @Test
    public void testLineConstructorAndGetters() {
        Point2D p1 = new Point.Double(100, 100);
        Point2D p2 = new Point.Double(200, 200);
        Point2D p3 = new Point.Double(300, 300);

        Line line = new Line(Color.RED, new BasicStroke(3), p1, p2, p3);

        assertEquals(Color.RED, line.getPaint());
        assertEquals(new BasicStroke(3), line.getStroke());

        assertEquals(3, line.getLocations().size());
        assertEquals(p1, line.getLocations().get(0));
        assertEquals(p2, line.getLocations().get(1));
        assertEquals(p3, line.getLocations().get(2));
    }

    @Test
    public void testLineAddLocation() {
        Point2D p1 = new Point.Double(100, 100);
        Point2D p2 = new Point.Double(200, 200);

        Line line = new Line(Color.BLUE, new BasicStroke(2), p1);
        line.addLocation(p2);

        assertEquals(2, line.getLocations().size());
        assertEquals(p2, line.getLocations().get(1));
    }

    @Test
    public void testLineIterator() {
        Point2D p1 = new Point.Double(100, 100);
        Point2D p2 = new Point.Double(200, 200);
        Point2D p3 = new Point.Double(300, 300);

        Line line = new Line(p1, p2, p3);

        Line.Segment[] segments = line.getLineIterator();
        assertEquals(2, segments.length);

        assertEquals(p1, segments[0].getStart());
        assertEquals(p2, segments[0].getEnd());
        assertEquals(p2, segments[1].getStart());
        assertEquals(p3, segments[1].getEnd());
    }

    @Test
    public void testLinePaint() {
        Point2D p1 = new Point.Double(100, 100);
        Point2D p2 = new Point.Double(200, 200);
        Point2D p3 = new Point.Double(300, 300);

        Line line = new Line(Color.GREEN, new BasicStroke(4), p1, p2, p3);
    }
}