package ui;

import org.junit.jupiter.api.Test;

import core.Context;
import core.models.gtfs.Time;
import ui.map.geometry.Line;

import java.awt.geom.Point2D;

public class LineVisualTest {

    @Test
    public void testLineDrawing() {
        new MaasMapsUI();

        Point2D p1 = new Point2D.Double(100, 100);
        Point2D p2 = new Point2D.Double(200, 200);
        Point2D p3 = new Point2D.Double(300, 100);
        Point2D p4 = new Point2D.Double(400, 200);
        Point2D p5 = new Point2D.Double(500, 250);
        Point2D p6 = new Point2D.Double(600, 300);
        Line line = new Line(p1, p2, p3, p4, p5, p6);

        Context.getContext().getMap().addMapGraphics(line);
        Context.getContext().getMap().repaint();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
