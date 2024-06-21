package ui.route;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import core.models.transport.Route;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RouteUI extends JPanel {
    private Route route;

    public RouteUI() {

    }

    public void initializeUI() {
        JScrollPane contentPane = new JScrollPane();


    }
}
