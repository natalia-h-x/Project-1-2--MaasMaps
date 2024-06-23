package core.managers.database.gtfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.managers.ExceptionManager;
import core.managers.database.QueryManager;
import core.models.BusStop;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BusStopManager {
    private static Map<Integer, BusStop> busStopMap;

    public static Map<Integer, BusStop> getBusStopMap() {
        if (busStopMap == null)
            busStopMap = getBusStops();

        return busStopMap;
    }

    public static BusStop getBusStop(int stopId) {
        return getBusStopMap().get(stopId);
    }

    private static Map<Integer, BusStop> getBusStops() {
        Map<Integer, BusStop> map = new HashMap<>();
        List<?>[] attributes = QueryManager.executeQuery("select stop_id, stop_lat, stop_lon, stop_name\r\n" + //
            "from stops;\r\n", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

        for (int i = 0; i < attributes[0].size(); i++) {
            try {
                Integer id = (int) attributes[0].get(i);
                BusStop busStop = new BusStop(id, (double) attributes[1].get(i), (double) attributes[2].get(i), (String) attributes[3].get(i));

                map.put(id, busStop);
            }
            catch (ClassCastException e) {
                ExceptionManager.warn(new IllegalStateException("Could not parse row from stops table", e));
            }
        }

        return map;
    }
}
