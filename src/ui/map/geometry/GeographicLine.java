package ui.map.geometry;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.managers.DistanceManager;
import core.managers.MapManager;
import core.models.Location;
import core.models.Time;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ui.map.geometry.interfaces.GeographicMapGraphics;

@Data
@EqualsAndHashCode(callSuper = true)
public class GeographicLine extends Line implements GeographicMapGraphics {
    private List<Time> times;

    public void setTimes(Time... times) {
        this.times.addAll(Arrays.asList(times));
    }

    public GeographicLine(Location... locations) {
        super(locations);
        times = new ArrayList<>();
    }

    public void addTime(Time time) {
        times.add(time);
    }

    @Override
    public Segment createSegment(Point2D start, Point2D end, List<Point2D> controlPoints) {
        int i = getLocations().indexOf(end);
        return new GeographicSegment(start, end, times.size() < i? times.get(i) : null, controlPoints.toArray(Point2D[]::new));
    }

    @Override
    public double getTotalDistance() {
        double totalDistance = 0;

        try {
            for (int i = 0; i < getLocations().size() - 1; i++) {
                Location loc1 = (Location) getLocations().get(i);
                Location loc2 = (Location) getLocations().get(i + 1);
                totalDistance += DistanceManager.haversine(loc1, loc2);
            }
        }
        catch (ClassCastException e) {
            throw new IllegalArgumentException("A Geographic Line only supports Locations. Please do not use other Point2D's!", e);
        }

        return totalDistance;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class GeographicSegment extends Segment {
        private Time time;

        public GeographicSegment(Point2D start, Point2D end, Time time, Point2D... controlPoints) {
            super(start, end, controlPoints);
            this.time = time;
        }

        @Override
        public void paint(Graphics2D g2) {
            super.paint(g2);
            
            if (time != null) drawTime(g2);
            else drawDistance(g2);
        }

        private void drawTime(Graphics2D g2) {
            MapManager.drawString(g2, time.toISOString(), getCenter(getStart(), getEnd()));
        }

        private void drawDistance(Graphics2D g2) {
            try {
                Location loc1 = (Location) getStart();
                Location loc2 = (Location) getEnd();

                String distance = String.valueOf(Math.round(DistanceManager.haversine(loc1, loc2) * 100.0) / 100.0) + " km";
                Point2D center = getCenter(loc1, loc2);

                // Create a 'border' effect for the text by drawing it in black first with
                // slight offsets
                MapManager.drawString(g2, distance, center);
            }
            catch (ClassCastException e) {
                throw new IllegalArgumentException("A Geographic Line only supports Locations. Please do not use other Point2D's!", e);
            }
        }

        private Point2D getCenter(Point2D p1, Point2D p2) {
            int x = ((int) p1.getX() + (int) p2.getX()) / 2;
            int y = ((int) p1.getY() + (int) p2.getY()) / 2;

            return new Point2D.Double(x, y);
        }
    }
}
