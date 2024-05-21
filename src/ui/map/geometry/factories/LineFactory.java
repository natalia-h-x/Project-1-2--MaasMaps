package ui.map.geometry.factories;
import java.awt.Stroke;

import javax.swing.plaf.ColorUIResource;
import java.awt.BasicStroke;

import core.models.Location;
import ui.map.geometry.Line;

public class LineFactory{
    
    public static Line createResultsLine(Location...locations){
        //change the stroke type here 
        Stroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);
        return new Line(new ColorUIResource(0,0,1), stroke , locations);
    }
}
