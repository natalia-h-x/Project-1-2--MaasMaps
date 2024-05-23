package core.models.transport;

import core.models.GTFSTime;
import core.models.Location;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import ui.map.geometry.GeographicLine;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Route {
    private GeographicLine line;
    private GTFSTime time;
    private TransportMode manualTransportModeA;
    private TransportMode manualTransportModeB;

    public static Route ofWalking(Location start, Location startingStop, GeographicLine line, GTFSTime time) {
        return new Route(line, time, new Walking(start, startingStop), new Walking(start, startingStop));
    }

    public static Route ofWalking(GeographicLine line, GTFSTime time) {
        return new Route(line, time, new Walking(new Location(0, 0), new Location(0, 0)), new Walking(new Location(0, 0), new Location(0, 0)));
    }

    public static Route ofBiking(Location start, Location startingStop, GeographicLine line, GTFSTime time) {
        return new Route(line, time, new Biking(start, startingStop), new Biking(start, startingStop));
    }

    public static Route ofBiking(GeographicLine line, GTFSTime time) {
        return new Route(line, time, new Biking(new Location(0, 0), new Location(0, 0)), new Biking(new Location(0, 0), new Location(0, 0)));
    }
}
