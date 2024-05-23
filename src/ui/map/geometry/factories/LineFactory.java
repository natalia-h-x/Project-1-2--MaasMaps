package ui.map.geometry.factories;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import javax.swing.plaf.ColorUIResource;

import ui.map.geometry.Line;

public class LineFactory{

    public static Line createResultsLine(Point2D... locations){
        //change the stroke type here
        Stroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);
        return new Line(new ColorUIResource(0,0,1), stroke , locations);
    }
}
