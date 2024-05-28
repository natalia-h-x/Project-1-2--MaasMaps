package core.algorithms.datastructures;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import core.models.Time;
import core.models.Trip;
import lombok.Data;

/**
 * EdgeNode that can be a route.
 */
@Data
public class EdgeNode<T> {
    private T element;
    private int weight;
    private SortedMap<Time, Trip> departureTimes;

    public EdgeNode(T element, int weight) {
        this.element = element;
        this.weight = weight;
        departureTimes = new TreeMap<>();
    }

    public void addTrip(Trip trip, Time departingTime) {
        departureTimes.put(departingTime, trip);
            //throw new IllegalArgumentException("Time is already contained in departure times");
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
            return weight;
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

        return weight + closestDepartureTime - arrivalTime;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (o instanceof EdgeNode)
            return element.equals(((EdgeNode<T>) o).getElement());

        return false;
    }

    @Override
    public int hashCode() {
        final int PRIME = 29;
        int p = 2;
        int result = -1;
        result = p * element.hashCode() * PRIME + p^2 * weight * PRIME;
        return result;
    }
}
