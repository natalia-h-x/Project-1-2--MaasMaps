package core.models.transport;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BinaryOperator;

import javax.swing.ImageIcon;

import core.Context;
import core.algorithms.DijkstraAlgorithm;
import core.algorithms.PathStrategy;
import core.models.BusStop;
import core.models.Location;
import core.models.gtfs.Time;
import core.models.gtfs.Trip;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ui.map.geometry.Radius;
import ui.map.geometry.interfaces.MapGraphics;

@Data
@EqualsAndHashCode(callSuper = true)
public class Bus extends Transport {
    private static final double AVERAGE_SPEED = 333; // meters per minute

    private BusStop busStop;
    private Trip trip;

    private RouteType routeType = RouteType.SHORTEST;
    private PathStrategy<Location> pathStrategy;

    private int radius;

    public Bus() {}
    public Bus(Location start, Location destination) {
        super(start, destination);
    }

    public double getAverageSpeed() {
        return AVERAGE_SPEED;
    }

    public String toString() {
        return "Take Bus";
    }

    @Override
    public Route getRoute() {
        try {
            return Optional.ofNullable(pathStrategy).orElse(new DijkstraAlgorithm<>()).calculateShortestPath(this).orElseThrow();
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Cannot find a connection between these postal codes.");
        }
    }

    public int getTransfers() {
        return getRoute().getTransferCount() - 2;
    }

    @Override
    public Time getTravelTime() {
        return getRoute().getTime();
    }

    @Override
    public MapGraphics[] getGraphics() {
        return new MapGraphics[] {
            new Radius((int) getStart      ().getX(), (int) getStart      ().getY(), getRadius()),
            new Radius((int) getDestination().getX(), (int) getDestination().getY(), getRadius()),
            getRoute().getLine(),
        };
    }

    @Override
    public ImageIcon getIcon() {
        throw new UnsupportedOperationException("Unimplemented method 'getIcon'");
    }

    public enum RouteType {
        SHORTEST {
            @Override
            public BinaryOperator<Time> getBinaryOperator() {
                return Time::add;
            }
        },
        SHORTEST_MANUAL {
            @Override
            public BinaryOperator<Time> getBinaryOperator() {
                return (m, v) -> m;
            }
        },
        SHORTEST_VEHICLE {
            @Override
            public BinaryOperator<Time> getBinaryOperator() {
                return (m, v) -> v;
            }
        };

        public abstract BinaryOperator<Time> getBinaryOperator();
    }

    @Override
    public String takeTransport() {
        String busStopName = busStop.getStopName();
        String time = getTime().toString();
        String waitTime = getWaitTime().toString();

        return String.format("Bus Stop: %s Bus Travel Time: %s, Wait Time: %s", busStopName, time, waitTime);
    }

    @Override
    public Transport copy() {
        return TransportFactory.copyBus(this);
    }
}
