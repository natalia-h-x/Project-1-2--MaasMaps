package ui.map;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import javax.swing.*;

import ui.map.interfaces.Moveable;
import ui.map.managers.MapManager;

public class Map extends JPanel implements Moveable {
    private transient BufferedImage mapImage;
    private transient int mapWidth;
    private transient int mapHeight;
    private double scale;
    private Point offset;

    public Map() {
        scale = 1;
        offset = new Point(0, 0);

        try {
            loadMap(MapManager.getMapData());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (mapImage == null)
            return;

        Graphics2D g2 = (Graphics2D) g;

        TexturePaint paint = new TexturePaint(mapImage, new Rectangle2D.Double(offset.x, offset.y, mapWidth * scale, mapHeight * scale));
        g2.setPaint(paint);
        g2.fill(new Rectangle2D.Double(offset.x, offset.y, mapWidth * scale, mapHeight * scale));
    }

    private void loadMap(BufferedImage image){
        mapImage = image;

        mapWidth = image.getWidth();
        mapHeight = image.getHeight();

        repaint();
    }

    @Override
    public void setScale(int scale) {
        this.scale = scale;

        repaint();
    }

    @Override
    public void setTranslation(Point offset) {
        this.offset = offset;

        repaint();
    }
}
