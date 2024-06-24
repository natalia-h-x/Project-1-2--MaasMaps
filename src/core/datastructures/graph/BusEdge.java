package core.datastructures.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import core.models.Location;
import core.models.gtfs.Time;
import core.models.gtfs.Trip;
import core.models.transport.Transport;
import core.models.transport.TransportFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * EdgeNode that can be a route.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusEdge<T> extends Edge<T> {
    private SortedMap<Time, Trip> departureTimes;
    private Map<Trip, Integer> weights;

    public BusEdge(T element, int weight) {
        super(element, weight);
        departureTimes = new TreeMap<>();
        weights = new HashMap<>();
    }

    public void addTrip(Trip trip, Time departingTime, int weight) {
        departureTimes.put(departingTime, trip);
        weights.put(trip, weight);
    }

    /**
     * Get the time that this edge takes to walk across. This function prioritizes keeping the route you are already on, and if a
     * transfer is absolutely necessary, it will transfer.
     *
     * @param arrivalTime the time you arrive at the bus stop, where you want to look for departing buses to take.
     * @return
     */
    public Weight getWeight(int arrivalTime, Trip trip) {
        int closestDepartureTime = getClosestDepartureTime(arrivalTime);

        if (closestDepartureTime == Integer.MAX_VALUE)
            return new BusWeight(Integer.MAX_VALUE, 0);

        // It is possible that the trip changes to another trip here which means a transfer occurred.
        trip.copyInto(departureTimes.get(Time.of(closestDepartureTime)));

        return new BusWeight(super.getWeight(), closestDepartureTime - arrivalTime);
    }

    private int getClosestDepartureTime(int arrivalTime) {
        Trip trip = departureTimes.get(Time.of(arrivalTime));

        if (trip != null)
            return arrivalTime;

        int closestDepartureTime = Integer.MAX_VALUE;

        for (Map.Entry<Time, Trip> entry : departureTimes.entrySet()) {
            Time departureTime = entry.getKey();

            if (departureTime.toSeconds() < arrivalTime)
                break;

            closestDepartureTime = departureTime.toSeconds();
        }

        return closestDepartureTime;
    }

    @Override
    public Transport asTransport(T outgoingVertex, Trip trip) {
        if (outgoingVertex instanceof Location point1 && getElement() instanceof Location point2) {
            return TransportFactory.createBus(point1, point2, trip);
        }

        throw new UnsupportedOperationException("could not make a transport from a classes of type " + outgoingVertex.getClass().getCanonicalName() + " and " + getElement().getClass().getCanonicalName());
    }
}
