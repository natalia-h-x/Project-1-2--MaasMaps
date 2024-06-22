package core.managers.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class DrawManager {
    private DrawManager() {}

    public static void drawString(Graphics2D g2, String distance, Point2D center) {
        int offset = 1;
        g2.setPaint(Color.BLACK);
        g2.drawString(distance, (int) center.getX() - offset, (int) center.getY() - offset);
        g2.drawString(distance, (int) center.getX() - offset, (int) center.getY()         );
        g2.drawString(distance, (int) center.getX() - offset, (int) center.getY() + offset);
        g2.drawString(distance, (int) center.getX() + offset, (int) center.getY()         );
        g2.drawString(distance, (int) center.getX() + offset, (int) center.getY() - offset);
        g2.drawString(distance, (int) center.getX()         , (int) center.getY() - offset);
        g2.drawString(distance, (int) center.getX() + offset, (int) center.getY() + offset);
        g2.drawString(distance, (int) center.getX()         , (int) center.getY() + offset);

        // Then draw the text in white at the original position
        g2.setPaint(Color.WHITE);
        g2.drawString(distance, (int) center.getX(), (int) center.getY());
    }
}
