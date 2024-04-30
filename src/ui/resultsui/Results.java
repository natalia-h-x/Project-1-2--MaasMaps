package ui.resultsui;

import static org.junit.Assert.assertThat;

import java.awt.*;
import javax.swing.*;

public class Results extends JPanel {
    private ResultsPanel resultsPanel;

    public Results() {
        initialiseUI();
    }
    
    private void initialiseUI() {
        resultsPanel = new ResultsPanel();
        setLayout(new BorderLayout());
        resultsPanel.setBackground(getBackground());
        add(resultsPanel);
    }
}
