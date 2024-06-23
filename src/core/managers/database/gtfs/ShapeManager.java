package core.managers.database.gtfs;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import core.managers.database.QueryManager;
import core.models.Location;
import core.models.gtfs.Shape;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShapeManager {
    public static Shape getShape(int shapeId) {
        List<?>[] attributes = QueryManager.executeQuery("select shape_pt_lat, shape_pt_lon\r\n" + //
            "from shapes\r\n" + //
            "where shape_id = '%s' ORDER BY shape_pt_sequence".formatted(shapeId), new ArrayList<Double>(), new ArrayList<Double>());

        try {
            List<Location> locations = new ArrayList<>();

            for (int i = 0; i < attributes[0].size(); i++) {
                locations.add(new Location((double) attributes[0].get(i),
                                           (double) attributes[1].get(i)));
            }

            return new Shape(shapeId, Color.gray, locations.toArray(Location[]::new));
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Could not make a shape from shape_id '%s'".formatted(shapeId), e);
        }
        catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Could find a shape with shape_id '%s'".formatted(shapeId));
        }
    }
}
