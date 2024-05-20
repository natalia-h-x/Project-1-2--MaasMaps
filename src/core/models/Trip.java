package core.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Trip {
    private int id;
    private int shapeId;
    private int routeId;
    private String tripHeadsign;
}
