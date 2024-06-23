package ui.map.geometry;

import java.awt.Graphics2D;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import ui.map.geometry.interfaces.MapGraphics;

@Data
@AllArgsConstructor
public class MapGraphicsGroup implements MapGraphics {
    private List<MapGraphics> mapGraphics;

    public void paint (Graphics2D g) {
        for (MapGraphics graphics : mapGraphics) {
            graphics.paint(g);
        }
    }
}
