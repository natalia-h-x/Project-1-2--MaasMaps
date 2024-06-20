package core.algorithms.datastructures;

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

    public BusEdge(T element, int weight) {
        super(element, weight);
        departureTimes = new TreeMap<>();
    }

    public void addTrip(Trip trip, Time departingTime) {
        departureTimes.put(departingTime, trip);
    }

    /**
     * Get the time that this edge takes to walk across. This function prioritizes keeping the route you are already on, and if a
     * transfer is absolutely necessary, it will transfer.
     *
     * @param arrivalTime the time you arrive at the bus stop, where you want to look for departing buses to take.
     * @return
     */
    public int getWeight(int arrivalTime, Trip transfer) {
        Trip trip = departureTimes.get(Time.of(arrivalTime));

        if (trip != null) {
            transfer.copyInto(trip);
            return super.getWeight();
        }

        int closestDepartureTime = Integer.MAX_VALUE;

        for (Map.Entry<Time, Trip> entry : departureTimes.entrySet()) {
            Time departureTime = entry.getKey();

            if (departureTime.toSeconds() < arrivalTime)
                break;

            transfer.copyInto(entry.getValue());
            closestDepartureTime = departureTime.toSeconds();
        }

        if (closestDepartureTime == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;

        return super.getWeight() + closestDepartureTime - arrivalTime;
    }

    @Override
    public Transport asTransport(T outgoingVertex, Trip trip) {
        if (outgoingVertex instanceof Location point1 && getElement() instanceof Location point2) {
            return TransportFactory.createBus(point1, point2, trip);
        }

        throw new UnsupportedOperationException("could not make a transport from a classes of type " + outgoingVertex.getClass().getCanonicalName() + " and " + getElement().getClass().getCanonicalName());
    }
}
