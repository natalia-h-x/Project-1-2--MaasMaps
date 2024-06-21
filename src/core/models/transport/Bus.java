package core.models.transport;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BinaryOperator;

import javax.swing.ImageIcon;

import core.Context;
import core.algorithms.DijkstraAlgorithm;
import core.algorithms.PathStrategy;
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
    private Trip trip;
    private BinaryOperator<Time> routeType = RouteType.SHORTEST.getBinaryOperator();
    private PathStrategy<Location> pathStrategy;

    public Bus() {}
    public Bus(Location start, Location destination) {
        super(start, destination);
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType.getBinaryOperator();
    }

    public double getAverageSpeed() {
        return AVERAGE_SPEED;
    }

    public String toString() {
        return "Take Bus";
    }

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
            new Radius((int) getStart      ().getX(), (int) getStart      ().getY(), (int) Context.getContext().getMap().getRadius()),
            new Radius((int) getDestination().getX(), (int) getDestination().getY(), (int) Context.getContext().getMap().getRadius()),
            getRoute().getLine(),
        };
    }

    @Override
    public ImageIcon getIcon() {
        throw new UnsupportedOperationException("Unimplemented method 'getIcon'");
    }

    enum RouteType {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeTransport'");
    }
}
