package ui.legend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import core.Constants;
import core.Constants.BusColors;
import core.Constants.AccessibilityColours;
import core.Constants.Paths;
import core.Constants.UIConstants;
import core.algorithms.AStarAlgorithm;
import core.algorithms.DijkstraAlgorithm;
import core.algorithms.PathStrategy;
import core.Context;
import core.datastructures.graph.Graph;
import core.managers.amenity.AmenityStatisticsManager;
import core.managers.map.MapManager;
import core.models.AccessibilityMeasure;
import core.models.Location;
import ui.map.Map;
import ui.map.ProxyMap;
import ui.map.geometry.AbstractedBusNetwork;
import ui.map.geometry.MapBackground;
import ui.map.geometry.Network;
import ui.route.ResultsProxy;
import ui.route.RouteUI;

public class AccessibilityLegend extends Legend {
    
    public AccessibilityLegend() {
        JFrame legendWindow = new JFrame("Legend");
        legendWindow.setSize(250, 400);
        legendWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        legendWindow.setLocationRelativeTo(this);

        JLabel heading = new JLabel("Accessibility");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(UIConstants.GUI_TITLE_COLOR);
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        legendPanel.setBackground(UIConstants.GUI_LEGEND_COLOR);
        legendPanel.add(heading);

        // Map to store bus colors and their corresponding counts
        java.util.Map<Color, Double> accessData = new HashMap<>();
        for (int i = 0; i < AccessibilityColours.ACC_GRADIENT.length; i++) {
            accessData.put(AccessibilityColours.ACC_GRADIENT[i], (i / (double)(AccessibilityColours.ACC_GRADIENT.length - 1)));
        }
        var test = AmenityStatisticsManager.getTop5();
        for (int index = 0; index < test.size(); index++) {
            AccessibilityMeasure accessibilityMeasure = test.get(index);
            String string = "Top %s at %s with accessibility %s".formatted(index, accessibilityMeasure.getPostalCode(), accessibilityMeasure.getAccessibility());
            JLabel pp = new JLabel(string);
            legendWindow.add(pp);
        }

        legendWindow.add(legendPanel);
        legendWindow.setAlwaysOnTop(true);
        legendWindow.setVisible(true);

    for(java.util.Map.Entry<Color, Double> entry : accessData.entrySet()) {
        JPanel legendItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        legendItem.setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
        legendItem.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel colorLabel = new JLabel("●");
        colorLabel.setForeground((Color) entry); // Set color
        colorLabel.setFont(new Font("●", 3, 20)); // Set font size
        legendItem.add(colorLabel);
            
        JLabel accessLabel = new JLabel();
        legendItem.add(accessLabel);

        legendPanel.add(legendItem);
    }
}

    public static void createHashMap() {
        java.util.Map<Color, String> accessData = new HashMap<>();
        accessData.put(BusColors.BUS_1, "Low accessibility");
        accessData.put(BusColors.BUS_30, "Medium accessibility");
        accessData.put(BusColors.BUS_6, "High accessibility");
    }
}
