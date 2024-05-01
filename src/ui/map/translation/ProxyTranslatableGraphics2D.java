package ui.map.translation;

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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

/**
 * Proxy for translating a Graphics2D independent from it.
 *
 * @author Sian Lodde
 */
public class ProxyTranslatableGraphics2D extends Graphics2D implements Translatable {
    /** Drawing methods will be forwarded to this object */
    private Graphics2D mGraphics;

    /** Variables for translating this Graphics2D */
    private double scaleX;
    private double scaleY;
    private Point translation;

    public ProxyTranslatableGraphics2D(Graphics2D g2, double scale, Point translation) {
        this(g2, scale, scale, translation);
    }

    public ProxyTranslatableGraphics2D(Graphics2D g2, double scaleX, double scaleY, Point translation) {
        mGraphics = g2;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.translation = translation;

        // Update font size to scale
        setFont(mGraphics.getFont());
    }

    public Graphics2D getDelegate() {
        return mGraphics;
    }

    public void setDelegate(Graphics2D g2) {
        mGraphics = g2;
    }

    private int scaleX(double width) {
        return (int) (width * scaleX);
    }

    private int deScaleX(double width) {
        return (int) (width / scaleX);
    }

    private int scaleY(double height) {
        return (int) (height * scaleY);
    }

    @SuppressWarnings("unused")
    private int deScaleY(double height) {
        return (int) (height / scaleY);
    }

    private int translateX(double x) {
        return scaleX(x) + translation.x;
    }

    private int translateY(double y) {
        return scaleY(y) + translation.y;
    }

    private void translateShape(Shape s) {
        if (s instanceof Rectangle2D rectangle)
            rectangle.setRect(translateX(rectangle.getX()), translateY(rectangle.getY()), scaleX(rectangle.getWidth()), scaleY(rectangle.getHeight()));
        else if (s instanceof Ellipse2D ellipse)
            ellipse.setFrame(translateX(ellipse.getX()), translateY(ellipse.getY()), scaleX(ellipse.getWidth()), scaleY(ellipse.getHeight()));
    }

    private void translatePolygon(Polygon p) {
        // Scale Polygon
        transformPointsPolygon(p.xpoints, p.ypoints);
    }

    private void transformPointsPolygon(int[] pxpoints, int[] pypoints) {
        int[] xpoints = pxpoints.clone();
        int[] ypoints = pypoints.clone();

        for (int i = 0; i < xpoints.length; i++)
            pxpoints[i] = translateX(xpoints[i]);

        for (int i = 0; i < ypoints.length; i++)
            pypoints[i] = translateY(ypoints[i]);
    }

    private void translateAffineTransform(AffineTransform xform) {
        xform.translate(translation.x, translation.y);
        xform.scale(scaleX, scaleY);
    }

    private void translateRectangle(Rectangle rect) {
        rect.translate(translation.x, translation.x);
        rect.setSize(scaleX(rect.getSize().getWidth()), scaleY(rect.getSize().getHeight()));
    }

    private Font translateFont(Font font) {
        return new Font(font.getName(), font.getStyle(), scaleX(font.getSize()));
    }

    private Font unTranslateFont(Font font) {
        return new Font(font.getName(), font.getStyle(), deScaleX(font.getSize()));
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
        return unTranslateFont(mGraphics.getFont());
    }

    @Override
    public void setFont(Font font) {
        mGraphics.setFont(translateFont(font));
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
        mGraphics.clipRect(translateX(x), translateY(y), scaleX(width), scaleY(height));
    }

    @Override
    public void setClip(int x, int y, int width, int height) {
        mGraphics.setClip(translateX(x), translateY(y), scaleX(width), scaleY(height));
    }

    @Override
    public Shape getClip() {
        return mGraphics.getClip();
    }

    @Override
    public void setClip(Shape clip) {
        translateShape(clip);

        mGraphics.setClip(clip);
    }

    @Override
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        mGraphics.copyArea(translateX(x), translateY(y), scaleX(width), scaleY(height), translateX(dx), translateY(dy));
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        mGraphics.drawLine(translateX(x1), translateY(y1), translateX(x2), translateY(y2));
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        mGraphics.fillRect(translateX(x), translateY(y), scaleX(width), scaleY(height));
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        mGraphics.drawRect(translateX(x), translateY(y), scaleX(width), scaleY(height));
    }

    @Override
    public void draw3DRect(int x, int y, int width, int height, boolean raised) {
        mGraphics.draw3DRect(translateX(x), translateY(y), scaleX(width), scaleY(height), raised);
    }

    @Override
    public void clearRect(int x, int y, int width, int height) {
        mGraphics.clearRect(translateX(x), translateY(y), scaleX(width), scaleY(height));
    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        mGraphics.drawRoundRect(translateX(x), translateY(y), scaleX(width), scaleY(height), scaleX(arcWidth), scaleY(arcHeight));
    }

    @Override
    public void fill3DRect(int x, int y, int width, int height, boolean raised) {
        mGraphics.fill3DRect(translateX(x), translateY(y), scaleX(width), scaleY(height), raised);
    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        mGraphics.fillRoundRect(translateX(x), translateY(y), scaleX(width), scaleY(height), scaleX(arcWidth), scaleY(arcHeight));
    }

    @Override
    public void draw(Shape s) {
        translateShape(s);

        mGraphics.draw(s);
    }

    @Override
    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        translateAffineTransform(xform);

        return mGraphics.drawImage(img, xform, obs);
    }

    @Override
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
        mGraphics.drawImage(img, op, translateX(x), translateY(y));
    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
        mGraphics.drawOval(translateX(x), translateY(y), scaleX(width), scaleY(height));
    }

    @Override
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
        translateAffineTransform(xform);

        mGraphics.drawRenderedImage(img, xform);
    }

    @Override
    public void fillOval(int x, int y, int width, int height) {
        mGraphics.fillOval(translateX(x), translateY(y), scaleX(width), scaleY(height));
    }

    @Override
    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
        translateAffineTransform(xform);

        mGraphics.drawRenderableImage(img, xform);
    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        mGraphics.drawArc(translateX(x), translateY(y), scaleX(width), scaleY(height), startAngle, arcAngle);
    }

    @Override
    public void drawString(String str, int x, int y) {
        mGraphics.drawString(str, translateX(x), translateY(y));
    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        mGraphics.fillArc(translateX(x), translateY(y), scaleX(width), scaleY(height), startAngle, arcAngle);
    }

    @Override
    public void drawString(String str, float x, float y) {
        mGraphics.drawString(str, translateX(x), translateY(y));
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        mGraphics.drawString(iterator, translateX(x), translateY(y));
    }

    @Override
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        transformPointsPolygon(xPoints, yPoints);

        mGraphics.drawPolyline(xPoints, yPoints, nPoints);
    }

    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        transformPointsPolygon(xPoints, yPoints);

        mGraphics.drawPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, float x, float y) {
        mGraphics.drawString(iterator, translateX(x), translateY(y));
    }

    @Override
    public void drawPolygon(Polygon p) {
        translatePolygon(p);

        // Finally, draw it
        mGraphics.drawPolygon(p);
    }

    @Override
    public void drawGlyphVector(GlyphVector g, float x, float y) {
        mGraphics.drawGlyphVector(g, translateX(x), translateY(y));
    }

    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        mGraphics.fillPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public void fill(Shape s) {
        translateShape(s);

        mGraphics.fill(s);
    }

    @Override
    public void fillPolygon(Polygon p) {
        translatePolygon(p);

        // Finally, draw it
        mGraphics.fillPolygon(p);
    }

    @Override
    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        translateShape(s);
        translateRectangle(rect);

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
        mGraphics.drawChars(data, offset, scaleX(length), translateX(x), translateY(y));
    }

    @Override
    public void drawBytes(byte[] data, int offset, int length, int x, int y) {
        mGraphics.drawBytes(data, offset, scaleX(length), translateX(x), translateY(y));
    }

    @Override
    public void setPaint(Paint paint) {
        if (paint instanceof TexturePaint texturePaint) {
            Rectangle2D rect = texturePaint.getAnchorRect();

            rect.setRect(translateX(rect.getX()), translateY(rect.getY()), scaleX(rect.getWidth()), scaleY(rect.getHeight()));

            paint = new TexturePaint(texturePaint.getImage(), rect);
        }

        // Add more implementations of the Paint class here. Still need to find out if
        // MultipleGradientPaint, GradientPaint, LinearGradientPaint, need translation / scaling

        mGraphics.setPaint(paint);
    }

    @Override
    public void setStroke(Stroke s) {
        BasicStroke bs = (BasicStroke) s;

        bs = new BasicStroke(scaleX(bs.getLineWidth()), bs.getEndCap(), bs.getLineJoin(), bs.getMiterLimit(), bs.getDashArray(), scaleX(bs.getDashPhase()));

        mGraphics.setStroke(bs);
    }

    @Override
    public void setRenderingHint(Key hintKey, Object hintValue) {
        mGraphics.setRenderingHint(hintKey, hintValue);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return mGraphics.drawImage(img, translateX(x), translateY(y), observer);
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
        return mGraphics.drawImage(img, translateX(x), translateY(y), scaleX(width), scaleY(height), observer);
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
        mGraphics.translate(translateX(x), translateY(y));
    }

    @Override
    public void translate(double tx, double ty) {
        mGraphics.translate(tx, ty);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return mGraphics.drawImage(img, translateX(x), translateY(y), bgcolor, observer);
    }

    @Override
    public void rotate(double theta) {
        mGraphics.rotate(theta);
    }

    @Override
    public void rotate(double theta, double x, double y) {
        mGraphics.rotate(theta, translateX(x), translateY(y));
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return mGraphics.drawImage(img, translateX(x), translateY(y), scaleX(width), scaleY(height), bgcolor, observer);
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
        translateAffineTransform(Tx);

        mGraphics.transform(Tx);
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
            ImageObserver observer) {
        return mGraphics.drawImage(img, translateX(dx1), translateY(dy1),
                                        translateX(dx2), translateY(dy2),
                                        translateX(sx1), translateY(sy1),
                                        translateX(sx2), translateY(sy2), observer);
    }

    @Override
    public void setTransform(AffineTransform Tx) {
        translateAffineTransform(Tx);

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
        return mGraphics.drawImage(img, translateX(dx1), translateY(dy1),
                                        translateX(dx2), translateY(dy2),
                                        translateX(sx1), translateY(sy1),
                                        translateX(sx2), translateY(sy2), bgcolor, observer);
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
        translateShape(s);

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

    /**
     * @deprecated As of JDK version 1.1, replaced by `getClipBounds()`.
     */
    @Override
    @Deprecated
    public Rectangle getClipRect() {
        return mGraphics.getClipRect();
    }

    @Override
    public Rectangle getClipBounds(Rectangle r) {
        translateRectangle(r);

        return mGraphics.getClipBounds(r);
    }

    @Override
    public void setScale(double scale) {
        scaleX = scale;
        scaleY = scale;
    }

    @Override
    public void setTranslation(Point point) {
        translation = point;
    }

    @Override
    public Graphics create() {
        return new ProxyTranslatableGraphics2D((Graphics2D) mGraphics.create(), scaleX, scaleY, translation);
    }
}
