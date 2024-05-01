package ui.map.geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import core.algorithms.datastructures.AdjacencyListGraph;
import ui.map.geometry.interfaces.MapGraphics;

public class Network extends Component implements MapGraphics {
    private AdjacencyListGraph<Point2D> graph;

    public Network(AdjacencyListGraph<Point2D> graph){
        this.graph = graph;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        // Get the each location to draw the lines
        g2.setPaint(new Color(001, 010, 100));
        BasicStroke bs = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);
        g2.setStroke(bs);

        for (Point2D p1 : graph) {
            if (p1 == null) continue;

            for (Point2D p2 : graph.neighbors(p1)) {
                if (p2 == null) continue;

                // Set paint color to blue for the line
                g2.setPaint(new Color(1, 10, 100));
                g2.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
            }
        }

        for (Point2D p1 : graph){
            if (p1 == null) continue;
            MarkerFactory.createBusImageMarker(p1).paint(g2);
        }
    }
}
