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
import ui.map.interfaces.TranslateableComponent;

public class Map extends JPanel implements TranslateableComponent {
    private transient BufferedImage mapImage;
    private transient int mapWidth;
    private transient int mapHeight;
    @SuppressWarnings("unused")
    private transient TranslationListener translationListener = new TranslationListener(this);
    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<Marker> markers = new ArrayList<>();

    /** Variables for translating this Map */
    private double scale;
    private Point translation;

    public Map() {
        Context.getContext().setMap(this);

        scale = 1;
        translation = new Point(0, 0);

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

        Graphics2D g2 = new ProxyTranslateableGraphics2D((Graphics2D) g, scale, translation);

        applyRenderingHints(g2);

        TexturePaint paint = new TexturePaint(mapImage, new Rectangle2D.Double(0, 0, mapWidth, mapHeight));
        g2.setPaint(paint);
        g2.fill(new Rectangle2D.Double(0, 0, mapWidth, mapHeight));

        LineDraw(g2);
        DrawMarker(g2);
    }

    private void applyRenderingHints(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
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

        repaint();
    }

    public void addMarker(Marker marker) {
        markers.add(marker);

        repaint();
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
        return translation;
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;

        repaint();
    }

    @Override
    public void setTranslation(Point translation) {
        this.translation = translation;

        repaint();
    }
}
