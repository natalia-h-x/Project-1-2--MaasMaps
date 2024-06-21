package ui.route;

import javax.swing.BoxLayout;
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

    public void setRoute(Route route) {
        this.route = route;

        removeAll();
        initializeUI();
    }

    public void initializeUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Transport transport : route) {
            panel.add(new RouteSegmentUI(transport));
        }

        add(new JScrollPane(panel));
    }
}
