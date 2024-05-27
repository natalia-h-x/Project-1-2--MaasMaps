package core.models.transport;

import java.util.List;

import core.models.Location;
import core.models.Time;
import core.models.Trip;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import ui.map.geometry.GeographicLine;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Transport {
    private GeographicLine line;
    private List<Trip> transfers;
    private Time time;
    private TransportMode manualTransportModeA;
    private TransportMode manualTransportModeB;

    public static Transport of(GeographicLine line, Time time, List<Trip> transfers) {
        return ofWalking(line, time, transfers);
    }

    public static Transport ofWalking(GeographicLine line, Time time, List<Trip> transfers) {
        return new Transport(line, transfers, time, new Walking(), new Walking());
    }

    public static Transport ofBiking(GeographicLine line, Time time, List<Trip> transfers) {
        return new Transport(line, transfers, time, new Biking(), new Biking());
    }
}
