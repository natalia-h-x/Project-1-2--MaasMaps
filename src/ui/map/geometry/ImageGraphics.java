package ui.map.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.TexturePaint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import lombok.Data;
import ui.map.geometry.interfaces.MapGraphics;

@Data
public class ImageGraphics implements MapGraphics {
    private BufferedImage image;
    private int width;
    private int height;
    private double scale = 1;
    private Point2D topLeftPoint;
    private Point offset = new Point(0, 0);

    public ImageGraphics(Point2D topLeftPoint, BufferedImage image) {
        this.topLeftPoint = topLeftPoint;
        setImage(image);
    }

    public ImageGraphics(Point2D topLeftPoint, BufferedImage image, double scale) {
        this(topLeftPoint, image);
        this.scale = scale;
    }

    public void setImage(BufferedImage image) {
        this.image = image;

        width = image.getWidth();
        height = image.getHeight();
    }

    @Override
    public void paint(Graphics2D g2) {
        double x = topLeftPoint.getX() - (width * scale) / 2.0 + offset.getX();
        double y = topLeftPoint.getY() - (height * scale) / 2.0 + offset.getY();

        TexturePaint paint = new TexturePaint(image, new Rectangle2D.Double(x, y, width * scale, height * scale));
        g2.setPaint(paint);
        g2.fill(new Rectangle2D.Double(x, y, width * scale, height * scale));
    }
}
