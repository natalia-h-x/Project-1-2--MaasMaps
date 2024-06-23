package core.managers.database.gtfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.managers.ExceptionManager;
import core.managers.database.QueryManager;
import core.models.gtfs.Trip;

public class TripManager {
    private static Map<Integer, Trip> tripMap;

    public static Map<Integer, Trip> getTripMap() {
        if (tripMap == null)
            tripMap = getTrips();

        return tripMap;
    }

    public static Trip getTrip(int id) {
        return getTripMap().get(id);
    }

    private static Map<Integer, Trip> getTrips() {
        Map<Integer, Trip> map = new HashMap<>();
        List<?>[] attributes = QueryManager.executeQuery("select trip_id, shape_id, route_id, trip_headsign\r\n" + //
            "from trips;\r\n", new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<String>());

        for (int i = 0; i < attributes[0].size(); i++) {
            try {
                int id = (int) attributes[0].get(i);
                Trip trip = new Trip(id, (int) attributes[1].get(i), (int) attributes[2].get(i), (String) attributes[3].get(i));

                map.put(id, trip);
            }
            catch (ClassCastException e) {
                ExceptionManager.warn(new IllegalStateException("Could not parse row from trips table", e));
            }
        }

        return map;
    }

}
