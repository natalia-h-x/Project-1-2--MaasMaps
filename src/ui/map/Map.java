package ui.map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import core.Context;
import core.managers.ExceptionManager;
import core.managers.FileManager;
import ui.map.geometry.interfaces.MapGraphics;
import ui.map.translation.ProxyTranslatableGraphics2D;
import ui.map.translation.TranslateableComponent;
import ui.map.translation.TranslationListener;

/**
 * This class represents the map shown in the GUI
 *
 * @author Sheena Gallagher
 * @author Sian Lodde
 * @author Alexandra Plishkin Islamgulova
 * @author Arda Ayyildizbayraktar
 */
public class Map extends JPanel implements TranslateableComponent {
    private transient BufferedImage mapImage;
    private transient int mapWidth;
    private transient int mapHeight;
    @SuppressWarnings("unused")
    private transient TranslationListener translationListener = new TranslationListener(this);
    private transient ArrayList<MapGraphics> icons = new ArrayList<>();

    /** Variables for translating this Map */
    private double scale;
    private Point translation;

    public Map() {
        ProxyMap proxyMap = new ProxyMap(this);
        Context.getContext().setMap(proxyMap);

        scale = 1;
        translation = new Point(0, 0);

        try {
            loadMap(FileManager.getMapImage());
        }
        catch (IOException e) {
            ExceptionManager.handle(this, e);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = new ProxyTranslatableGraphics2D((Graphics2D) g, scale, translation);

        applyFastRenderingHints(g2);
        drawMapImage(g2);
        drawMapIcon(g2);
    }

    private void drawMapImage(Graphics2D g2) {
        if (mapImage == null)
            return;

        TexturePaint paint = new TexturePaint(mapImage, new Rectangle2D.Double(0, 0, mapWidth, mapHeight));
        g2.setPaint(paint);
        g2.fill(new Rectangle2D.Double(0, 0, mapWidth, mapHeight));
    }

    private void applyFastRenderingHints(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DEFAULT);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
    }

    @SuppressWarnings("unchecked")
    private void drawMapIcon(Graphics2D g2) {
        for (MapGraphics icon : (Iterable<MapGraphics>) icons.clone()) {
            if (icon != null)
                icon.paint(g2);
        }
    }

    private void loadMap(BufferedImage image) {
        mapImage = image;

        mapWidth = image.getWidth();
        mapHeight = image.getHeight();

        repaint();
    }

    public void addMapIcon(MapGraphics icon) {
        icons.add(icon);

        repaint();
    }

    public void clearIcons() {
        icons.clear();

        repaint();
    }

    public double getScale() {
        return scale;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
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
