package ui.results;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import core.Constants.UIConstants;
import core.Context;

public class Results extends JPanel {
    public Results() {
        initialiseUI();
    }

    private void initialiseUI() {
        setLayout(new BorderLayout());

        ResultsPanel resultsPanel = new ResultsPanel();
        Context.getContext().setResultsPanel(new ResultsProxy(resultsPanel));
        
        resultsPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        JLabel routeTitle = new JLabel("Travel route");
        routeTitle.setForeground(UIConstants.GUI_TITLE_COLOR);
        routeTitle.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TITLE_FONT_SIZE/2));
        routeTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(routeTitle, BorderLayout.NORTH);

        add(resultsPanel);
    }
}
