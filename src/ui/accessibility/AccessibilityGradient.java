package ui.accessibility;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import core.Constants.UIConstants;
import ui.map.geometry.AccessibilityMapBackground;

public class AccessibilityGradient extends JPanel {
    public AccessibilityGradient() {
        initlialiseUI();
    }

    public void initlialiseUI() {
        setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
        double margin = 10;
        double sizeOfGradient = getSize().getHeight();

        for (double i = margin; i < sizeOfGradient - margin; i += 0.1) {
            g2.setPaint(AccessibilityMapBackground.notSiansLinearInterpolation((i - margin) / (sizeOfGradient), new Color[] {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN}));
            g2.fill(new Ellipse2D.Double(0, i, 10, 10));
        }
    }
}
