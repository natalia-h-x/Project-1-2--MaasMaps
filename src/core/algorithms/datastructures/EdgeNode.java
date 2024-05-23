package core.algorithms.datastructures;

import java.util.List;
import java.util.Optional;

import core.models.GTFSTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * EdgeNode that can be a route.
 */
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EdgeNode<T> {
    private T element;
    private int weight;
    private List<GTFSTime> departureTimes;

    public void addDepartureTime(GTFSTime time) {
        departureTimes.add(time);
    }

    /**
     * Get the time that this edge takes to walk across.
     *
     * @param arrivalTime the time you arrive at the bus stop, where you want to look for departing buses to take.
     * @return
     */
    public static boolean pleaseForgiveMe = false;
    public int getWeight(GTFSTime arrivalTime) {
        GTFSTime closestDepartureTime = departureTimes.get(0);

        for (GTFSTime departureTime : departureTimes) {
            if (departureTime.toSeconds() < arrivalTime.toSeconds())
                break;

            closestDepartureTime = departureTime;
        }


        return weight + closestDepartureTime.toSeconds() - arrivalTime.toSeconds();
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
