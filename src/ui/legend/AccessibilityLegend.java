package ui.legend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Constants.AccessibilityColours;
import core.Constants.BusColors;
import core.Constants.UIConstants;
import core.managers.amenity.AmenityStatisticsManager;
import core.models.AccessibilityMeasure;

public class AccessibilityLegend extends Legend {

    public AccessibilityLegend() {
        JFrame legendWindow = new JFrame("Legend");
        legendWindow.setSize(600, 600);
        legendWindow.setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
        legendWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        legendWindow.setLocationRelativeTo(this);

        JLabel heading = new JLabel("Accessibility");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(UIConstants.GUI_TITLE_COLOR);
        
        
        JPanel legendPanel = new JPanel();
        legendPanel.add(heading, BorderLayout.NORTH);
        legendPanel.setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
        legendPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        legendWindow.add(legendPanel);
        legendWindow.setAlwaysOnTop(true);
        legendWindow.setVisible(true);


    }
}
