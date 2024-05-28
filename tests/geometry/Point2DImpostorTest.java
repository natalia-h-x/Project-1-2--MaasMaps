package geometry;

import org.junit.jupiter.api.Test;

import ui.MaasMapsUI;
import ui.map.geometry.Point2DImpostor;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Point2DImpostorTest {
    public Point2DImpostorTest() {
        new MaasMapsUI();
    }

    @Test
    public void testGetX() {
        Point2D originalPoint = new Point2D.Double(10, 20);
        Point2DImpostor impostor = new Point2DImpostor(originalPoint);

        assertEquals(10.0, impostor.getX());
    }

    @Test
    public void testGetY() {
        Point2D originalPoint = new Point2D.Double(10, 20);
        Point2DImpostor impostor = new Point2DImpostor(originalPoint);

        assertEquals(20.0, impostor.getY());
    }

    @Test
    public void testSetLocation() {
        Point2D originalPoint = new Point2D.Double(10, 20);
        Point2DImpostor impostor = new Point2DImpostor(originalPoint);

        impostor.setLocation(30, 40);
        assertEquals(30.0, originalPoint.getX());
        assertEquals(40.0, originalPoint.getY());

        Point2D newPoint = new Point2D.Double(50, 60);
        impostor.setLocation(newPoint);
        assertEquals(50.0, originalPoint.getX());
        assertEquals(60.0, originalPoint.getY());
    }

    @Test
    public void testDistanceMethods() {
        Point2D originalPoint = new Point2D.Double(10, 20);
        Point2DImpostor impostor = new Point2DImpostor(originalPoint);

        assertEquals(50.0, impostor.distanceSq(15, 25));
        assertEquals(7.0710678118654755, impostor.distance(15, 25));

        Point2D otherPoint = new Point2D.Double(30, 40);
        assertNotEquals(100.0, impostor.distanceSq(otherPoint));
        assertNotEquals(10.0, impostor.distance(otherPoint));
    }

    @Test
    public void testToString() {
        Point2D originalPoint = new Point2D.Double(10, 20);
        Point2DImpostor impostor = new Point2DImpostor(originalPoint);
        assertEquals("Point2D.Double[10.0, 20.0]", impostor.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        Point2D originalPoint1 = new Point2D.Double(10, 20);
        Point2D originalPoint2 = new Point2D.Double(10, 20);

        Point2DImpostor impostor1 = new Point2DImpostor(originalPoint1);
        Point2DImpostor impostor2 = new Point2DImpostor(originalPoint2);

        assertEquals(impostor1, impostor2);
        assertEquals(impostor1.hashCode(), impostor2.hashCode());
    }
}
