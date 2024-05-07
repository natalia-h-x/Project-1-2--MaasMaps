package ui.map.geometry;

import java.awt.geom.Point2D;
import java.util.function.DoubleFunction;

import core.algorithms.datastructures.Graph;
import core.models.BusStop;

public class AbstractedBusNetwork extends BusNetwork {
    private static final DoubleFunction<Double> abstractingFunction = i -> stepify(i, 32);

    public AbstractedBusNetwork(Graph<BusStop> graph) {
        super(graph);
    }

    @Override
    public Line createLineSegment(Point2D from, Point2D to) {
        return super.createLineSegment(new Point2DImpostor(from, abstractingFunction),
                                       new Point2DImpostor(to  , abstractingFunction));
    }

    @Override
    public Marker createMarker(Point2D point) {
        return super.createMarker(new Point2DImpostor(point, abstractingFunction));
    }

    private static double stepify(double i, double step) {
        return (int) (i / step) * step;
    }
}
