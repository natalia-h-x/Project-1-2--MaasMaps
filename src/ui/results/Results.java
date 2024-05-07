package ui.results;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class Results extends JPanel {
    public Results() {
        initialiseUI();
    }

    private void initialiseUI() {
        setLayout(new BorderLayout());

        ResultsPanel resultsPanel = new ResultsPanel();
        resultsPanel.setBackground(getBackground());

        add(resultsPanel);
    }
}
