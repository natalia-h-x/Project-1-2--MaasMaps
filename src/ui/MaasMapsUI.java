package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import core.Constants.UIConstants;
import core.Context;
import core.algorithms.datastructures.Graph;
import core.managers.MapManager;
import ui.map.Map;
import ui.map.ProxyMap;
import ui.map.geometry.AbstractedBusNetwork;
import ui.map.geometry.MapBackground;
import ui.map.geometry.Network;

/**
 * This class represents the app UI showing the map and the navigation panel
 *
 * @author Sheena Gallagher
 */
public class MaasMapsUI extends JFrame {
    private Map map;

    public MaasMapsUI() {
        super("Maas Maps");
        initialiseUI();
    }

    private void initialiseUI() {
        setSize(800, 700);
        setLayout(new BorderLayout()); // Use borderlayout to completely fill the child panel in this panel
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Creating all components
        map = new Map();
        map.addMapBackground();
        map.setMinimumSize(new Dimension(800, 500));
        map.setPreferredSize(new Dimension(800, 500));

        ProxyMap proxyMap = new ProxyMap(map);
        Context.getContext().setMap(proxyMap);

        // create split pane with left and right panels
        JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JSplitPane horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        setContentPane(horizontalSplitPane);

        // create split pane with top and bottom panels
        JPanel resultsContainer = new JPanel(new BorderLayout());
        resultsContainer.setBorder(BorderFactory.createMatteBorder(UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE,
                                                                   UIConstants.GUI_BACKGROUND_COLOR));
        resultsContainer.setMinimumSize(new Dimension(800, 600));
        resultsContainer.setPreferredSize(new Dimension(800, 600));


        // Adding our custom panels here
        // Use minimum and preferred size to always see the map and navigation panel
        NavigationPanel navigationPanel = new NavigationPanel();
        navigationPanel.setMinimumSize(new Dimension(450, 600));
        navigationPanel.setPreferredSize(new Dimension(500, 600));

        Map resultsPanel = new Map();
        resultsPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        resultsPanel.setMinimumSize(new Dimension(500,150));
        resultsPanel.setPreferredSize(new Dimension(500,150));
        
        Graph<Point2D> graph = MapManager.getBusGraph();
        Network abstractedBusNetwork = new AbstractedBusNetwork(graph);

        resultsPanel.addMapGraphics(abstractedBusNetwork);
        resultsPanel.repaint();

        // Adding all components
        verticalSplitPane.add(map, JSplitPane.TOP);
        verticalSplitPane.add(resultsPanel, JSplitPane.BOTTOM);

        resultsContainer.add(verticalSplitPane);

        horizontalSplitPane.add(navigationPanel, JSplitPane.LEFT);
        horizontalSplitPane.add(resultsContainer, JSplitPane.RIGHT);

        setVisible(true);
        revalidate();
    }
}

