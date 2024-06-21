package ui.route;

import javax.swing.JLabel;
import javax.swing.JPanel;

import core.models.transport.Transport;

public class RouteSegmentUI extends JPanel {
    private Transport transport;

    public RouteSegmentUI(Transport transport) {
        this.transport = transport;

        initializeUI();
    }

    private void initializeUI() {
        add(startLabel());
    }

    public JLabel startLabel() {
        JLabel startLabel = new JLabel(transport.takeTransport());

        return startLabel;
    }
}
