package core.algorithms.datastructures;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import core.models.Time;
import core.models.Route;
import lombok.Data;

/**
 * EdgeNode that can be a route.
 */
@Data
public class EdgeNode<T> {
    private T element;
    private int weight;
    private Map<Route, SortedSet<Time>> departureTimes;

    public EdgeNode(T element, int weight) {
        this.element = element;
        this.weight = weight;
        departureTimes = new HashMap<>();
    }

    public void addDepartureTime(Route route, Time time) {
        departureTimes.computeIfAbsent(route, t -> new TreeSet<>()).add(time);
            //throw new IllegalArgumentException("Time is already contained in departure times");
    }

    /**
     * Get the time that this edge takes to walk across. This function prioritizes keeping the route you are already on, and if a
     * transfer is absolutely necessary, it will transfer.
     *
     * @param arrivalTime the time you arrive at the bus stop, where you want to look for departing buses to take.
     * @return
     */
    public int getWeight(Time arrivalTime, Route route, Route transfer) {
        SortedSet<Time> times = departureTimes.get(route);
        int bestTime = times != null ? getClosestDeparture(arrivalTime, times) : Integer.MAX_VALUE;
        
        if (bestTime != Integer.MAX_VALUE)
            return bestTime;

        for (Map.Entry<Route, SortedSet<Time>> entry : departureTimes.entrySet()) {
            if (entry.getKey() == route)
                continue;

            int time = getClosestDeparture(arrivalTime, entry.getValue());

            if (time < bestTime) {
                transfer.copyInto(entry.getKey());
                bestTime = time;
            }
        }

        return bestTime;
    }

    public static boolean pleaseForgiveMe = false;
    private int getClosestDeparture(Time arrivalTime, SortedSet<Time> times) {
        int closestDepartureTime = Integer.MAX_VALUE;
        int arrival = arrivalTime.toSeconds();

        for (Time departureTime : times) {
            if (departureTime.toSeconds() < arrival)
                break;

            closestDepartureTime = departureTime.toSeconds();
        }

        if (closestDepartureTime == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        
        // if (closestDepartureTime - arrival != 0 && pleaseForgiveMe)
        //     return Integer.MAX_VALUE;

        return weight + closestDepartureTime - arrival;
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
