package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

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
        setVisible(true);

        JSplitPane splitPane = new JSplitPane();
        setContentPane(splitPane);

        NavigationPanel navigationPanel = new NavigationPanel();

        // create split pane with left and right panels
        map.setMinimumSize(new Dimension(500, 600));
        map.setPreferredSize(new Dimension(500, 600));
        navigationPanel.setMinimumSize(new Dimension(450, 600));
        navigationPanel.setPreferredSize(new Dimension(500, 600));

        splitPane.add(navigationPanel, JSplitPane.LEFT);
        splitPane.add(map, JSplitPane.RIGHT);

        revalidate();
    }
}

