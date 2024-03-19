package ui.map;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MaasMapsUI extends JFrame {
    public MaasMapsUI(){
        super("Maas Maps");
        initialiseUI();
    }

    private void initialiseUI() {
        setSize(500, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        add(new Map());
    }
}
