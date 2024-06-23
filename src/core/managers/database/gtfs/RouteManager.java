package core.managers.database.gtfs;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.managers.ExceptionManager;
import core.managers.database.QueryManager;
import core.models.gtfs.Route;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RouteManager {
    private static Map<Integer, Route> routeMap;

    public static Map<Integer, Route> getRouteMap() {
        if (routeMap == null)
            routeMap = getRoutes();

        return routeMap;
    }

    public static Route getRoute(int id) {
        return getRouteMap().get(id);
    }

    private static Map<Integer, Route> getRoutes() {
        Map<Integer, Route> map = new HashMap<>();
        List<?>[] attributes = QueryManager.executeQuery("select route_id, route_long_name, route_color\r\n" + //
            "from routes;\r\n", new ArrayList<Integer>(), new ArrayList<String>(), new ArrayList<String>());

        for (int i = 0; i < attributes[0].size(); i++) {
            try {
                int id = (int) attributes[0].get(i);
                Route trip = new Route(id, (String) attributes[1].get(i), Color.decode((String) attributes[2].get(i)));

                map.put(id, trip);
            }
            catch (NumberFormatException e) {
                ExceptionManager.warn(new IllegalStateException("Could not parse row from trips table", e));
            }
        }

        return map;
    }
}
