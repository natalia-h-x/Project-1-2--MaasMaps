package core.models.transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import core.models.Location;
import core.models.gtfs.Time;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import ui.map.geometry.GeographicLine;
import ui.map.geometry.ImageMarkerFactory;
import ui.map.geometry.factories.LineFactory;
import ui.map.geometry.interfaces.MapGraphics;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Route {
    public static Route empty() {
        return new Route(Time.of(0), new LinkedList<>());
    }

    public static Route of(Time start, List<Transport> path) {
        return new Route(start, path);
    }

    private Time startTime;
    private List<Transport> transfers;

    public void addTransport(Transport... transfers) {
        this.transfers.addAll(Arrays.asList(transfers));
    }

    private List<Transport> prune() {
        Transport previous = null;
        List<Transport> newTransfers = new LinkedList<>();

        for (Transport transport : transfers) {
            if (previous != null && transport.canMerge(previous)) {
                previous.setDestination(transport.getDestination());
                previous.setTime(previous.getTime().add(transport.getTime()));

                continue;
            }

            newTransfers.add(transport);

            previous = transport;
        }

        return newTransfers;
    }

    public Location getStart() {
        if (getTransferCount() <= 0)
            throw new IllegalArgumentException("transfers are empty and could not find a start location");

        return transfers.get(0).getStart();
    }

    public Location getDestination() {
        if (getTransferCount() <= 0)
            throw new IllegalArgumentException("transfers are empty and could not find a destination location");

        return transfers.get(transfers.size() - 1).getDestination();
    }

    /**
     * Includes walking to bus stops and to destination as transfers.
     * Subtract 2 to remove these trivial transfers.
     */
    public int getTransferCount() {
        return prune().size() - 1;
    }

    public GeographicLine getLine() {
        return LineFactory.createResultsLine(transfers.toArray(Transport[]::new));
    }

    public Time getManualTravelTime() {
        Time total = startTime;

        for (Transport transport : transfers) {
            if (transport instanceof Walking || transport instanceof Biking) {
                total = total.add(transport.getTime());
            }
        }

        return total;
    }

    public Time getVehicleTravelTime() {
        Time total = startTime;

        for (Transport transport : transfers) {
            if (transport instanceof Bus) {
                total = total.add(transport.getTime());
            }
        }

        return total;
    }

    public Time getTime() {
        Time total = Time.of(0);

        for (Transport transport : transfers) {
            total = total.add(transport.getTime());
        }

        return total;
    }

    public MapGraphics[] getGraphics() {
        List<MapGraphics> graphics = new ArrayList<>();

        transfers.stream().forEach(t -> Arrays.asList(t.getGraphics()));
        graphics.add(ImageMarkerFactory.createAImageMarker(getStart()));
        graphics.add(ImageMarkerFactory.createBImageMarker(getDestination()));

        return graphics.toArray(MapGraphics[]::new);
    }
}
