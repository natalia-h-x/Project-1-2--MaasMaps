package core.datastructures.graph;

import core.models.gtfs.Trip;
import core.models.transport.Transport;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Edge<T> {
    private T element;
    private int weight;

    public abstract Transport asTransport(T outgoingVertex, Trip trip);
    public abstract Weight getWeight(int arrivalTime, Trip transfer, int maxWalkingTime);

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (o instanceof WalkingEdge)
            return element.equals(((WalkingEdge<T>) o).getElement());

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
