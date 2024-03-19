package ui.map;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import javax.swing.*;

import core.Context;
import core.managers.FileManager;
import ui.map.geometry.Line;
import ui.map.geometry.Marker;
import ui.map.interfaces.Translateable;

public class Map extends JPanel implements Translateable {
    private transient BufferedImage mapImage;
    private transient int mapWidth;
    private transient int mapHeight;
    private double scale;
    private Point offset;
    private TranslationListener translationListener = new TranslationListener(this);
    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<Marker> markers = new ArrayList<>();

    public Map() {
        Context.getContext().setMap(this);

        scale = 2;
        offset = new Point(0, 0);

        try {
            loadMap(FileManager.getMapImage());
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

        LineDraw(g2);
        DrawMarker(g2);
    }

    private void LineDraw(Graphics2D g2) {
        for (Line line : lines) {
            if (line != null)
                line.paint(g2);
        }
    }

    private void DrawMarker(Graphics2D g2) {
        for (Marker marker : markers) {
            if (marker != null)
                marker.paint(g2);
        }
    }

    private void loadMap(BufferedImage image){
        mapImage = image;

        mapWidth = image.getWidth();
        mapHeight = image.getHeight();

        repaint();
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public void addMarker(Marker marker) {
        markers.add(marker);
    }

    public double getScale() {
        return scale;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public Point getTranslation() {
        return offset;
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;

        repaint();
    }

    @Override
    public void setTranslation(Point offset) {
        this.offset = offset;

        repaint();
    }
}
