package ui.map.geometry;

import java.awt.Shape;
import java.awt.Stroke;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArrowStroke implements Stroke {
    private int numArr;
    private int len;
    private int width;

    public Shape createStrokedShape(Shape s) {
        // Use the first stroke to create an outline of the shape
        return s;
    }
}
