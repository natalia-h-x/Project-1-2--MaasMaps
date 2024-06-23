package ui.route;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

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
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        JLabel startLabel = new JLabel(transport.takeTransport());

        return startLabel;
    }
}
