package ui.map.geometry.interfaces;

import java.awt.Graphics2D;

import core.models.Location;

public interface GeographicMapGraphics extends MapGraphics {
    double getTotalDistance();
    void drawDistance(Graphics2D g2, Location loc1, Location loc2);
}