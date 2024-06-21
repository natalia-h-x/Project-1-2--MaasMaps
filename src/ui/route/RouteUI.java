package ui.route;

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

        setVisible(true);
        removeAll();
        initializeUI();
        revalidate();
        repaint();
    }

    public void initializeUI() {
        JPanel panel = new JPanel();

        for (Transport transport : route) {
            panel.add(new RouteSegmentUI(transport));
        }

        add(new JScrollPane(panel));
    }
}
