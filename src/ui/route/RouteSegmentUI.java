package ui.route;

import java.awt.Font;

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
        add(timeLabel());
        add(startLabel());
        add(destinationLabel());
    }

    public JLabel timeLabel() {
        String time = String.format("Time: %s", transport.getTime().toString());
        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 1000000));

        return timeLabel;
    }

    public JLabel startLabel() {
        String start = String.format("Start: %s", transport.getStart());
        JLabel startLabel = new JLabel(start);

        return startLabel;
    }

    public JLabel destinationLabel() {
        String destination = String.format("Time: %s", transport.getDestination());
        JLabel destinationLabel = new JLabel(destination);

        return destinationLabel;
    }
}
