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
     * @return
     */
    public int getWeight(GTFSTime currentTime) {
        GTFSTime closestTime = departureTimes.get(0);

        for (GTFSTime time : departureTimes) {
            if (time.toSeconds() > currentTime.toSeconds())
                break;

            closestTime = time;
        }

        return Optional.ofNullable(closestTime).orElse(GTFSTime.of("Infinity")).toSeconds() - currentTime.toSeconds();
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
