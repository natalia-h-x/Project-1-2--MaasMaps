package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import constants.Constants.UIConstants;
import ui.map.Map;

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
        map = new Map();

        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JSplitPane splitPane = new JSplitPane();
        setContentPane(splitPane);

        NavigationPanel navigationPanel = new NavigationPanel();

        JPanel resultsContainer = new JPanel();
        map = new Map();
        resultsContainer.setBorder(BorderFactory.createMatteBorder(UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BACKGROUND_COLOR));
        ResultsPanel resultsPanel = new ResultsPanel();
        resultsPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        map.setMinimumSize(new Dimension(800, 500));
        map.setPreferredSize(new Dimension(800, 500));
        resultsPanel.setMinimumSize(new Dimension(500,150));
        resultsPanel.setPreferredSize(new Dimension(500,150));

        splitPane2.add(map, JSplitPane.TOP);
        splitPane2.add(resultsPanel, JSplitPane.BOTTOM);

        resultsContainer.add(splitPane2);


        // create split pane with left and right panels
        resultsContainer.setMinimumSize(new Dimension(800, 600));
        resultsContainer.setPreferredSize(new Dimension(800, 600));
        navigationPanel.setMinimumSize(new Dimension(450, 600));
        navigationPanel.setPreferredSize(new Dimension(500, 600));

        splitPane.add(navigationPanel, JSplitPane.LEFT);
        splitPane.add(resultsContainer, JSplitPane.RIGHT);


        setVisible(true);
        revalidate();
    }
}

