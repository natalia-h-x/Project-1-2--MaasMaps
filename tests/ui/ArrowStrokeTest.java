package ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import ui.map.geometry.ArrowStroke;

public class ArrowStrokeTest {
    @Test
    public void arrowStrokeTest() {
        ArrowStroke stroke = new ArrowStroke(0, 0, 0);
        stroke.createStrokedShape(null);

        stroke.setNumArr(2);
        stroke.setLen(3);
        stroke.setWidth(1);

        assertEquals(2, stroke.getNumArr());
        assertEquals(3, stroke.getLen());
        assertEquals(1, stroke.getWidth());
        stroke.hashCode();

        ArrowStroke stroke2 = stroke;
        ArrowStroke stroke3 = new ArrowStroke(0, 0, 0);
        assertTrue(stroke.equals(stroke2));
        assertTrue(stroke.toString().equals(stroke2.toString()));
        assertFalse(stroke2.equals(stroke3));
        assertFalse(stroke2.toString().equals(stroke3.toString()));
        assertNotEquals(2, stroke3.getNumArr());
        assertNotEquals(3, stroke3.getLen());
        assertNotEquals(1, stroke3.getWidth());
        
        assertNotEquals(stroke2.hashCode(), stroke3.hashCode());
        assertEquals(stroke2.hashCode(), stroke.hashCode());
    }
}
