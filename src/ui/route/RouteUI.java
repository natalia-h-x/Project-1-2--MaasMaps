package ui.route;

import javax.swing.JScrollPane;

import core.models.transport.Route;
import core.models.transport.Transport;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RouteUI extends JScrollPane {
    private transient Route route;

    public RouteUI() {

    }

    public void setRoute(Route route) {
        this.route = route;

        removeAll();
        initializeUI();
    }

    public void initializeUI() {
        for (Transport transport : route) {
            add(new RouteSegmentUI(transport));
        }
    }
}
