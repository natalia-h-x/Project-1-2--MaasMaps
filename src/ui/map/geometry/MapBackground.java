package ui.map.geometry;

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

    public MapBackground() {
        try {
            loadMap(FileManager.getMapImage());
        }
        catch (IOException e) {
            ExceptionManager.handle(e);
        }
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

        TexturePaint paint = new TexturePaint(mapImage, new Rectangle2D.Double(0, 0, mapWidth, mapHeight));
        g2.setPaint(paint);
        g2.fill(new Rectangle2D.Double(0, 0, mapWidth, mapHeight));
    }
}
