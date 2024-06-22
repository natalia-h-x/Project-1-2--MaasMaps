package ui.route;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import core.models.transport.Route;
import core.models.transport.Transport;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RouteUI extends JPanel {
    private transient Route route;

    public RouteUI() {
        super(new BorderLayout());
    }

    public void setRoute(Route route) {
        this.route = route;

        removeAll();
        initializeUI();
    }

    public void initializeUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Transport previous = null;

        for (Transport transport : route) {
            panel.add(new RouteSegmentUI(transport));

            if (previous != null && previous.canMerge(transport)) {
                panel.add(new JLabel("Transfer"));
            }

            previous = transport;
        }

        JScrollPane scrollbar = new JScrollPane(panel);
        scrollbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollbar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(BorderLayout.CENTER, scrollbar);
    }
}
