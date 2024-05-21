package core.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Trip {
    private int id;
    private int shapeId;
    private int routeId;
    private String tripHeadsign;
    private List<BusStop> busStops;
}
