package ui.legend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Constants.AccessibilityColours;
import core.Constants.UIConstants;
import ui.accessibility.AccessibilityGradient;

public class AccessibilityGradientLegend extends JPanel {

    public AccessibilityGradientLegend(){
        super(new BorderLayout());

        initialiseUI();
    }

    public void initialiseUI() {
        setPreferredSize(new Dimension(50, 200));
        setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
        setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
        mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel leftPanel = new AccessibilityGradient();
        leftPanel.setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
        leftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (int i = 0; i < AccessibilityColours.ACC_GRADIENT.length; i++) {
            JLabel ppp = new JLabel(String.valueOf(i / (double) (AccessibilityColours.ACC_GRADIENT.length - 1)));
            mainPanel.add(ppp);

            if (i < AccessibilityColours.ACC_GRADIENT.length - 1)
                mainPanel.add(Box.createVerticalGlue());
        }

        add(leftPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.EAST);
    }
}
