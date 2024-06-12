package ui.map.geometry;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;

import lombok.Data;

@Data
public class ArrowStroke implements Stroke {
    private int numArr;
    private int len;
    private int width;
    private BasicStroke stroke;

    public ArrowStroke(int numArr, int len, int width) {
        this.numArr = numArr;
        this.len = len;
        this.width = width;
        stroke = new BasicStroke();
    }

    public Shape createStrokedShape(Shape s) {
        // Use the first stroke to create an outline of the shape
        return stroke.createStrokedShape(s);
    }
}
