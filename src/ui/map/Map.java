package ui.map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import lombok.Getter;
import ui.map.geometry.MapBackground;
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
    @Getter private transient TranslationListener translationListener = new TranslationListener(this);
    @Getter private transient ArrayList<MapGraphics> icons = new ArrayList<>();
    @Getter
    private transient MapBackground mapBackground;
    @Getter
    private transient java.util.Map<String, MapGraphics> topGraphics = new HashMap<>();

    /** Variables for translating this Map */
    private double scale;
    private double radius = 1000;
    private Point translation;

    public Map(MapBackground mapBackground) {
        this.mapBackground = mapBackground;
        scale = 1;
        translation = new Point(0, 0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = new ProxyTranslatableGraphics2D((Graphics2D) g, scale, translation);

        applyFastRenderingHints(g2);

        if (mapBackground != null) {
            mapBackground.paint(g2);
        }

        for (MapGraphics mapGraphics : topGraphics.values()) {
            mapGraphics.paint(g2);
        }

        drawMapIcon(g2);
    }

    @SuppressWarnings("unchecked")
    private void drawMapIcon(Graphics2D g2) {
        g2.setPaint(Color.black);

        for (MapGraphics icon : (Iterable<MapGraphics>) icons.clone()) {
            if (icon != null)
                icon.paint(g2);
        }
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

    public void addMapGraphics(MapGraphics icon) {
        icons.add(icon);

        repaint();
    }

    public void clear() {
        icons.clear();

        repaint();
    }

    public double getScale() {
        return scale;
    }

    public int getMapWidth() {
        return mapBackground.getMapWidth();
    }

    public int getMapHeight() {
        return mapBackground.getMapHeight();
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

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setTranslationListener(TranslationListener translationListener) {
        this.translationListener = translationListener;
    }

    public void setIcons(ArrayList<MapGraphics> icons) {
        this.icons = icons;
    }

    public void setMapBackground(MapBackground mapBackground) {
        this.mapBackground = mapBackground;
    }
}
