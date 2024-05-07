package ui.results;

import ui.map.geometry.GeographicLine;

public class ResultsProxy {
    private ResultsPanel resultsPanel;

    public ResultsProxy(ResultsPanel resultsPanel) {
        this.resultsPanel = resultsPanel;
    }

    public void setLine(GeographicLine geographicLine) {
        resultsPanel.setLine(geographicLine);
    }
}