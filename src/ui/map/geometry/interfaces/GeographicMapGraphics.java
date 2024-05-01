package ui.map.geometry.interfaces;

import java.awt.Graphics2D;

import models.Location;

public interface GeographicMapGraphics {
    double getTotalDistance();
    void drawDistance(Graphics2D g2, Location loc1, Location loc2);
}