package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import core.Constants.UIConstants;
import ui.map.Map;
import ui.results.Results;

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
        setSize(800, 600);
        setLayout(new BorderLayout()); // Use borderlayout to completely fill the child panel in this panel
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Creating all components
        map = new Map();
        map.setMinimumSize(new Dimension(800, 500));
        map.setPreferredSize(new Dimension(800, 500));

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

        Results resultsPanel = new Results();
        resultsPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        resultsPanel.setMinimumSize(new Dimension(500,150));
        resultsPanel.setPreferredSize(new Dimension(500,150));
        
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

