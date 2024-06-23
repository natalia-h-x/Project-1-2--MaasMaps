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

import core.Constants.BusColors;
import core.Constants.Paths;
import core.Constants.UIConstants;
import core.algorithms.AStarAlgorithm;
import core.algorithms.DijkstraAlgorithm;
import core.algorithms.PathStrategy;
import core.Context;
import core.datastructures.graph.Graph;
import core.managers.map.MapManager;
import core.models.Location;
import ui.map.Map;
import ui.map.ProxyMap;
import ui.map.geometry.AbstractedBusNetwork;
import ui.map.geometry.MapBackground;
import ui.map.geometry.Network;
import ui.route.ResultsProxy;
import ui.route.RouteUI;

public class BusNetworkLegend extends Legend {
    
    public BusNetworkLegend() {
        JFrame legendWindow = new JFrame("Legend");
        legendWindow.setSize(250, 400);
        legendWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        legendWindow.setLocationRelativeTo(this);

        JLabel heading = new JLabel("Bus Lines");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(UIConstants.GUI_TITLE_COLOR);
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        legendPanel.setBackground(UIConstants.GUI_LEGEND_COLOR);
        legendPanel.add(heading);

        // Map to store bus colors and their corresponding counts
        java.util.Map<Color, Integer> busData = new HashMap<>();
        busData.put(BusColors.BUS_30, 30);
        busData.put(BusColors.BUS_6, 6);
        busData.put(BusColors.BUS_1, 1);
        busData.put(BusColors.BUS_10, 10);
        busData.put(BusColors.BUS_7, 7);
        busData.put(BusColors.BUS_4, 4);
        busData.put(BusColors.BUS_2, 2);
        busData.put(BusColors.BUS_350, 350);
        busData.put(BusColors.BUS_15, 15);

        // Create legend items
        for (java.util.Map.Entry<Color, Integer> entry : busData.entrySet()) {
            JPanel legendItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            legendItem.setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
            legendItem.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel colorLabel = new JLabel("●");
            colorLabel.setForeground(entry.getKey()); // Set color
            colorLabel.setFont(new Font("●", 3, 20)); // Set font size
            legendItem.add(colorLabel);

            JLabel numberLabel = new JLabel(" - No. " + entry.getValue());
            legendItem.add(numberLabel);

            legendPanel.add(legendItem);
        }

        legendWindow.add(legendPanel);
        legendWindow.setAlwaysOnTop(true);
        legendWindow.setVisible(true);
    }

}
