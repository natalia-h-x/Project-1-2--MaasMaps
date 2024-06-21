package models.gtfs;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import core.models.gtfs.Route;

public class RouteTest {
    @Test
    public void routeTest() {
        Route route = Route.empty();
        Route notEmptyRoute = Route.empty();
        notEmptyRoute.setColor(Color.BLACK);
        notEmptyRoute.setId(12);
        notEmptyRoute.setName("test");

        route.copyInto(notEmptyRoute);
        assertEquals(route, notEmptyRoute);
        assertEquals(route.toString(), notEmptyRoute.toString());
    }

    @Test
    public void notEqualsRouteTest() {
        Route route = Route.empty();
        Route notEmptyRoute = Route.empty();
        notEmptyRoute.setColor(Color.BLACK);
        notEmptyRoute.setId(12);
        notEmptyRoute.setName("test");

        assertNotEquals(route, notEmptyRoute);
        assertNotEquals(route.toString(), notEmptyRoute.toString());
    }
}
