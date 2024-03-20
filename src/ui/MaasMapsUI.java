package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import models.Location;
import ui.map.geometry.ImageMarker;
import ui.map.Map;
import ui.map.geometry.Line;

public class MaasMapsUI extends JFrame {
    private Map map;

    public MaasMapsUI(){
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
        map.setMinimumSize(new Dimension(300,500));
        map.setPreferredSize(new Dimension(500,600));
        navigationPanel.setMinimumSize(new Dimension(200,500));
        navigationPanel.setPreferredSize(new Dimension(300,600));

        splitPane.add(navigationPanel, JSplitPane.LEFT);
        splitPane.add(map, JSplitPane.RIGHT);

        //buildTestMap();
        revalidate();
    }

    private void buildTestMap() {
        map.addMapIcon(new Line(
                new Location(50.853617, 5.692009),
                new Location(50.90074, 5.714544), new Location(50.877296 ,5.672557)
        ));

        map.addMapIcon(ImageMarker.createBusImageMarker(new Location(50.853617, 5.692009)));
    }
}

