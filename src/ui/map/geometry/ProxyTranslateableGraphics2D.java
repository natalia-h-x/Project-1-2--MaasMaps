package ui.map.geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import ui.map.interfaces.Translateable;

public class ProxyTranslateableGraphics2D extends Graphics2D implements Translateable {
    /** Drawing methods will be forwarded to this object */
    private Graphics2D mGraphics;

    /** Variables for translating this Graphics2D */
    private double scale;
    private Point translation;

    public ProxyTranslateableGraphics2D(Graphics2D g2, double scale, Point translation) {
        mGraphics = g2;
        this.scale = scale;
        this.translation = translation;
    }
    
    public Graphics2D getDelegate() {
        return mGraphics;
    }

    public void setDelegate(Graphics2D g2) {
        mGraphics = g2;
    }

    @Override
    public Color getColor() {
        return mGraphics.getColor();
    }

    @Override
    public void setColor(Color c) {
        mGraphics.setColor(c);
    }

    @Override
    public void setPaintMode() {
        mGraphics.setPaintMode();
    }

    @Override
    public void setXORMode(Color c1) {
        mGraphics.setXORMode(c1);
    }

    @Override
    public Font getFont() {
        return mGraphics.getFont();
    }

    @Override
    public void setFont(Font font) {
        mGraphics.setFont(font);
    }

    @Override
    public FontMetrics getFontMetrics() {
        return mGraphics.getFontMetrics();
    }

    @Override
    public FontMetrics getFontMetrics(Font f) {
        return mGraphics.getFontMetrics(f);
    }

    @Override
    public Rectangle getClipBounds() {
        return mGraphics.getClipBounds();
    }

    @Override
    public void clipRect(int x, int y, int width, int height) {
        mGraphics.clipRect(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale));
    }

    @Override
    public void setClip(int x, int y, int width, int height) {
        mGraphics.setClip(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale));
    }

    @Override
    public Shape getClip() {
        return mGraphics.getClip();
    }

    @Override
    public void setClip(Shape clip) {
        if (clip instanceof Rectangle2D rectangle)
            rectangle.setRect(rectangle.getX() + translation.x, rectangle.getY() + translation.y, rectangle.getWidth() * scale, rectangle.getHeight() * scale);

        mGraphics.setClip(clip);
    }

    @Override
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        mGraphics.copyArea(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale), dx + translation.x, dy + translation.y);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        mGraphics.drawLine(x1 + translation.x, y1 + translation.y, x2 + translation.x, y2 + translation.y);
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        mGraphics.fillRect(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale));
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        mGraphics.drawRect(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale));
    }

    @Override
    public void draw3DRect(int x, int y, int width, int height, boolean raised) {
        mGraphics.draw3DRect(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale), raised);
    }

    @Override
    public void clearRect(int x, int y, int width, int height) {
        mGraphics.clearRect(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale));
    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        mGraphics.drawRoundRect(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale), (int) (arcWidth * scale), (int) (arcHeight * scale));
    }

    @Override
    public void fill3DRect(int x, int y, int width, int height, boolean raised) {
        mGraphics.fill3DRect(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale), raised);
    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        mGraphics.fillRoundRect(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale), (int) (arcWidth * scale), (int) (arcHeight * scale));
    }

    @Override
    public void draw(Shape s) {
        if (s instanceof Rectangle2D rectangle)
            rectangle.setRect(rectangle.getX() + translation.x, rectangle.getY() + translation.y, rectangle.getWidth() * scale, rectangle.getHeight() * scale);

        mGraphics.draw(s);
    }

    @Override
    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        xform.translate(translation.x, translation.y);
        xform.scale(scale, scale);

        return mGraphics.drawImage(img, xform, obs);
    }

    @Override
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
        mGraphics.drawImage(img, op, x + translation.x, y + translation.y);
    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
        mGraphics.drawOval(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale));
    }

    @Override
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
        xform.translate(translation.x, translation.y);
        xform.scale(scale, scale);

        mGraphics.drawRenderedImage(img, xform);
    }

    @Override
    public void fillOval(int x, int y, int width, int height) {
        mGraphics.fillOval(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale));
    }

    @Override
    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
        xform.translate(translation.x, translation.y);
        xform.scale(scale, scale);

        mGraphics.drawRenderableImage(img, xform);
    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        mGraphics.drawArc(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale), startAngle, arcAngle);
    }

    @Override
    public void drawString(String str, int x, int y) {
        mGraphics.drawString(str, x + translation.x, y + translation.y);
    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        mGraphics.fillArc(x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale), startAngle, arcAngle);
    }

    @Override
    public void drawString(String str, float x, float y) {
        mGraphics.drawString(str, x + translation.x, y + translation.y);
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        mGraphics.drawString(iterator, x + translation.x, y + translation.y);
    }

    @Override
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        mGraphics.drawPolyline(xPoints, yPoints, nPoints);
    }

    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        mGraphics.drawPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, float x, float y) {
        mGraphics.drawString(iterator, x + translation.x, y + translation.y);
    }

    @Override
    public void drawPolygon(Polygon p) {
        // Translate Polygon
        p.translate(translation.x, translation.y);
        
        // Scale Polygon
        int[] xpoints = p.xpoints.clone();
        int[] ypoints = p.ypoints.clone();
        
        for (int i = 0; i < xpoints.length; i++)
            p.xpoints[i] = (int) (xpoints[i] * scale);
        
        for (int i = 0; i < ypoints.length; i++)
            p.ypoints[i] = (int) (ypoints[i] * scale);

        // Finally, draw it
        mGraphics.drawPolygon(p);
    }

    @Override
    public void drawGlyphVector(GlyphVector g, float x, float y) {
        mGraphics.drawGlyphVector(g, x + translation.x, y + translation.y);
    }

    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        mGraphics.fillPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public void fill(Shape s) {
        if (s instanceof Rectangle2D rectangle)
            rectangle.setRect(rectangle.getX() + translation.x, rectangle.getY() + translation.y, rectangle.getWidth() * scale, rectangle.getHeight() * scale);

        mGraphics.fill(s);
    }

    @Override
    public void fillPolygon(Polygon p) {
        // Translate Polygon
        p.translate(translation.x, translation.y);
        
        // Scale Polygon
        int[] xpoints = p.xpoints.clone();
        int[] ypoints = p.ypoints.clone();
        
        for (int i = 0; i < xpoints.length; i++)
            p.xpoints[i] = (int) (xpoints[i] * scale);
        
        for (int i = 0; i < ypoints.length; i++)
            p.ypoints[i] = (int) (ypoints[i] * scale);

        // Finally, draw it
        mGraphics.fillPolygon(p);
    }

    @Override
    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        if (s instanceof Rectangle2D rectangle)
            rectangle.setRect(rectangle.getX() + translation.x, rectangle.getY() + translation.y, rectangle.getWidth() * scale, rectangle.getHeight() * scale);

        rect.translate(translation.x, translation.x);
        rect.setSize((int) (rect.getSize().getWidth() * scale), (int) (rect.getSize().getHeight() * scale));
        
        return hit(rect, s, onStroke);
    }
    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return mGraphics.getDeviceConfiguration();
    }

    @Override
    public void setComposite(Composite comp) {
        mGraphics.setComposite(comp);
    }

    @Override
    public void drawChars(char[] data, int offset, int length, int x, int y) {
        mGraphics.drawChars(data, offset, (int) (length * scale), x + translation.x, y + translation.y);
    }

    @Override
    public void drawBytes(byte[] data, int offset, int length, int x, int y) {
        mGraphics.drawBytes(data, offset, (int) (length * scale), x + translation.x, y + translation.y);
    }

    @Override
    public void setPaint(Paint paint) {
        if (paint instanceof TexturePaint texturePaint) {
            Rectangle2D rect = texturePaint.getAnchorRect();

            rect.setRect(rect.getX() + translation.x, rect.getY() + translation.y, rect.getWidth() * scale, rect.getHeight() * scale);

            paint = new TexturePaint(texturePaint.getImage(), rect);
        }

        // Add more implementations of the Paint class here. Still need to find out if
        // MultipleGradientPaint, GradientPaint, LinearGradientPaint, need translation / scaling

        mGraphics.setPaint(paint);
    }

    @Override
    public void setStroke(Stroke s) {
        BasicStroke bs = (BasicStroke) s;

        bs = new BasicStroke((int) (bs.getLineWidth() * scale), bs.getEndCap(), bs.getLineJoin(), bs.getMiterLimit(), bs.getDashArray(), (int) (bs.getDashPhase() * scale));

        mGraphics.setStroke(bs);
    }

    @Override
    public void setRenderingHint(Key hintKey, Object hintValue) {
        mGraphics.setRenderingHint(hintKey, hintValue);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return mGraphics.drawImage(img, x + translation.x, y + translation.y, observer);
    }

    @Override
    public Object getRenderingHint(Key hintKey) {
        return mGraphics.getRenderingHint(hintKey);
    }

    @Override
    public void setRenderingHints(Map<?, ?> hints) {
        mGraphics.setRenderingHints(hints);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return mGraphics.drawImage(img, x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale), observer);
    }

    @Override
    public void addRenderingHints(Map<?, ?> hints) {
        mGraphics.addRenderingHints(hints);
    }

    @Override
    public RenderingHints getRenderingHints() {
        return mGraphics.getRenderingHints();
    }

    @Override
    public void translate(int x, int y) {
        mGraphics.translate(x + translation.x, y + translation.y);
    }

    @Override
    public void translate(double tx, double ty) {
        mGraphics.translate(tx, ty);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return mGraphics.drawImage(img, x + translation.x, y + translation.y, bgcolor, observer);
    }

    @Override
    public void rotate(double theta) {
        mGraphics.rotate(theta);
    }

    @Override
    public void rotate(double theta, double x, double y) {
        mGraphics.rotate(theta, x + translation.x, y + translation.y);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return mGraphics.drawImage(img, x + translation.x, y + translation.y, (int) (width * scale), (int) (height * scale), bgcolor, observer);
    }

    @Override
    public void scale(double sx, double sy) {
        mGraphics.scale(sx, sy);
    }

    @Override
    public void shear(double shx, double shy) {
        mGraphics.shear(shx, shy);
    }

    @Override
    public void transform(AffineTransform Tx) {
        Tx.translate(translation.x, translation.y);
        Tx.scale(scale, scale);

        mGraphics.transform(Tx);
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
            ImageObserver observer) {
        return mGraphics.drawImage(img, dx1 + translation.x, dy1 + translation.y,
                                        dx2 + translation.x, dy2 + translation.y,
                                        sx1 + translation.x, sy1 + translation.y,
                                        sx2 + translation.x, sy2 + translation.y, observer);
    }

    @Override
    public void setTransform(AffineTransform Tx) {
        Tx.translate(translation.x, translation.y);
        Tx.scale(scale, scale);

        mGraphics.setTransform(Tx);
    }

    @Override
    public AffineTransform getTransform() {
        return mGraphics.getTransform();
    }

    @Override
    public Paint getPaint() {
        return mGraphics.getPaint();
    }

    @Override
    public Composite getComposite() {
        return mGraphics.getComposite();
    }

    @Override
    public void setBackground(Color color) {
        mGraphics.setBackground(color);
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
            Color bgcolor, ImageObserver observer) {
        return mGraphics.drawImage(img, dx1 + translation.x, dy1 + translation.y,
                                        dx2 + translation.x, dy2 + translation.y,
                                        sx1 + translation.x, sy1 + translation.y,
                                        sx2 + translation.x, sy2 + translation.y, bgcolor, observer);
    }

    @Override
    public Color getBackground() {
        return mGraphics.getBackground();
    }

    @Override
    public Stroke getStroke() {
        return mGraphics.getStroke();
    }

    @Override
    public void clip(Shape s) {
        if (s instanceof Rectangle2D rectangle)
            rectangle.setRect(rectangle.getX() + translation.x, rectangle.getY() + translation.y, rectangle.getWidth() * scale, rectangle.getHeight() * scale);

        mGraphics.clip(s);
    }

    @Override
    public FontRenderContext getFontRenderContext() {
        return mGraphics.getFontRenderContext();
    }

    @Override
    public void dispose() {
        mGraphics.dispose();
    }

    @Deprecated()
    /**
     * @deprecated As of JDK version 1.1, replaced by `getClipBounds()`.
     * @return
    
    @Override */
    public Rectangle getClipRect() {
        return mGraphics.getClipRect();
    }

    @Override
    public Rectangle getClipBounds(Rectangle r) {
        r.translate(translation.x, translation.y);
        r.setSize((int) (r.getSize().getWidth() * scale), (int) (r.getSize().getHeight() * scale));

        return mGraphics.getClipBounds(r);
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public void setTranslation(Point point) {
        translation = point;
    }

    @Override
    public Graphics create() {
        return new ProxyTranslateableGraphics2D((Graphics2D) mGraphics.create(), scale, translation);
    }
}
