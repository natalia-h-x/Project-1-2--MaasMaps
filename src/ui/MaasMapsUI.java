package ui;

import javax.swing.JFrame;

public class MaasMapsUI extends JFrame{

    private Map map;

    public MaasMapsUI(){
        super("Maas Maps");
        initialiseUI();
    }

    private void initialiseUI(){
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        map = new Map();
        add(map);
    }
}
