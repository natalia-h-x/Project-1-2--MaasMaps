package core.models;

import java.awt.Color;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Route {
    private int id;
    private String name;
    private Color color;

    public static Route empty() {
        return new Route(0, "", Color.white);
    }

    public void copyInto(Route route) {
        id = route.id;
        name = route.name;
        color = route.color;
    }
}
