package core.datastructures.graph;

import core.models.Location;
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
public class WalkingEdge<T> extends Edge<T> {
    public WalkingEdge(T element, int weight) {
        super(element, weight);
    }

    /**
     * Get the time that this edge takes to walk across. This function prioritizes keeping the route you are already on, and if a
     * transfer is absolutely necessary, it will transfer.
     *
     * @param arrivalTime the time you arrive at the bus stop, where you want to look for departing buses to take.
     * @return
     */
    public Weight getWeight(int arrivalTime, Trip transfer) {
        return new Weight(super.getWeight(), 0);
    }

    @Override
    public Transport asTransport(T outgoingVertex, Trip trip) {
        if (outgoingVertex instanceof Location point1 && getElement() instanceof Location point2) {
            return TransportFactory.createWalking(point1, point2);
        }

        throw new UnsupportedOperationException("could not make a transport from a classes of type " + outgoingVertex.getClass().getCanonicalName() + " and " + getElement().getClass().getCanonicalName());
    }
}
