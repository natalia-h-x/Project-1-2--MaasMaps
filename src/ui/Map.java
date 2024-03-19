package ui;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import javax.swing.*;

import models.Location;

public class Map extends JPanel{
    public BufferedImage map;
    public int mapWidth;
    public int mapHeight;
    public double scale;
    private Point offset;
    private double minLon = -180; // Minimum longitude
    private double maxLon = 180;  // Maximum longitude
    private double minLat = -90;  // Minimum latitude
    private double maxLat = 90;   // Maximum latitude

    public Map(){
        scale = 1;
        offset = new Point(0,0);

        try {
            BufferedImage mapImage = MapManager.getMapData();
            loadMap(mapImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (map == null)
            return;

        Graphics2D g2 = (Graphics2D) g;

        TexturePaint paint = new TexturePaint(map, new Rectangle2D.Double(offset.x, offset.y, mapWidth * scale, mapHeight * scale));
        g2.setPaint(paint);
        g2.fill(new Rectangle2D.Double(offset.x, offset.y, mapWidth * scale, mapHeight * scale));
    }

    private void loadMap(BufferedImage mapImage){
        map = mapImage;

        mapWidth = map.getWidth();
        mapHeight = map.getHeight();

        repaint();
    }
    public Point convertGeoToPixel(Location location) {
        double lon = location.getLongitude();
        double lat = location.getLatitude();

        // Convert longitude to X coordinate
        int x = (int) ((lon - minLon) / (maxLon - minLon) * mapWidth);
        // Convert latitude to Y coordinate (Note: Y coordinate is inverted)
        int y = (int) ((maxLat - lat) / (maxLat - minLat) * mapHeight);

        return new Point(x, y);
    }
}
