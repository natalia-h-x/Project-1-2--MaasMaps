package ui.map.geometry;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import core.managers.ExceptionManager;
import core.managers.FileManager;
import lombok.Getter;
import ui.map.geometry.interfaces.MapGraphics;

public class MapBackground implements MapGraphics {
    private transient BufferedImage mapImage;
    @Getter private transient int mapWidth;
    @Getter private transient int mapHeight;
    private float alpha = 1;

    public MapBackground() {
        try {
            loadMap(FileManager.getMapImage());
        }
        catch (IOException e) {
            ExceptionManager.handle(e);
        }
    }

    public MapBackground(BufferedImage image) {
        loadMap(image);
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    
    private void loadMap(BufferedImage image) {
        mapImage = image;

        mapWidth = image.getWidth();
        mapHeight = image.getHeight();
    }

    @Override
    public void paint(Graphics2D g2) {
        if (mapImage == null)
            return;

        Composite old = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        
        TexturePaint paint = new TexturePaint(mapImage, new Rectangle2D.Double(0, 0, mapWidth, mapHeight));
        g2.setPaint(paint);
        g2.fill(new Rectangle2D.Double(0, 0, mapWidth, mapHeight));

        g2.setComposite(old);
    }
}
