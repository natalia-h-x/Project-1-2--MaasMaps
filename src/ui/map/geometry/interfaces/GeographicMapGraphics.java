package ui.map.geometry.interfaces;

import java.awt.Graphics2D;

import core.models.Location;

public interface GeographicMapGraphics extends MapGraphics {
    public double getTotalDistance();
    public void drawDistance(Graphics2D g2, Location loc1, Location loc2);
}