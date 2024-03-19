package ui.map;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import models.Location;
import ui.map.geometry.Marker;
import ui.map.geometry.Line;

public class MaasMapsUI extends JFrame {
    Map map;

    public MaasMapsUI(){
        super("Maas Maps");
        initialiseUI();
    }

    private void initialiseUI() {
        setSize(500, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        add(map = new Map());
        map.addLine(new Line(new Location(50.853617, 5.692009), new Location(50.853037,5.691825),
        new Location(50.90074,5.714544), new Location(50.877296,5.672557)));
        map.addMarker(new Marker(new Location(50.853617, 5.692009)));
        map.addMarker(new Marker(new Location(50.877296,5.672557)));

    }
}

