package ui.map.geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import core.algorithms.datastructures.AdjacencyListGraph;
import core.managers.MapManager;
import core.models.Location;

public class Network extends Component implements MapIcon {
    private AdjacencyListGraph<Location> graph;

    public Network(AdjacencyListGraph<Location> graph){
        this.graph = graph;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        // Get the each location to draw the lines
        g2.setPaint(new Color(001, 010, 100));
        BasicStroke bs = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);
        g2.setStroke(bs);

        for (Location loc1: graph) {
            if (loc1 == null) continue;
            for (Location loc2 : graph.neighbors(loc1)) {
                if (loc2 == null) continue;
    
                Point p1 = MapManager.locationToPoint(loc1);
                Point p2 = MapManager.locationToPoint(loc2);
    
                // Set paint color to blue for the line
                g2.setPaint(new Color(1, 10, 100));
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
        for (Location loc1:graph){
            if (loc1 == null) continue;
            MarkerFactory.createBusImageMarker(loc1).paint(g2);
        }
    }
}
