package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import models.Location;
import ui.map.geometry.Marker;
import ui.map.Map;
import ui.map.geometry.Line;

public class MaasMapsUI extends JFrame {
    Map map;

    public MaasMapsUI(){
        super("Maas Maps");
        initialiseUI();
    }

    private void initialiseUI() {
        map = new Map();
        setSize(800, 600);
        setLayout(new BorderLayout());
        NavigationPanel navigationPanel = new NavigationPanel();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // create split pane with left and right panels
        navigationPanel.setMinimumSize(new Dimension(300,600));
        map.setMinimumSize(new Dimension(500,600));
        map.setPreferredSize(new Dimension(500,600));
        navigationPanel.setPreferredSize(new Dimension(300,600));
        add(navigationPanel,BorderLayout.WEST);
        add(map);
        buildTestMap();

        revalidate();
    }

    private void buildTestMap() {
        map.addMapIcon(new Line(
            new Location(50.853037, 5.691825),
            new Location(50.90074, 5.714544), new Location(50.877296 ,5.672557)
        ));

        map.addMapIcon(new Marker(new Location(50.853617, 5.692009)));
    }
}

