package ui.route;

import core.models.transport.Route;

public class ResultsProxy {
    private RouteUI routeUI;

    public ResultsProxy(RouteUI routeUI) {
        this.routeUI = routeUI;
    }

    public void setRoute(Route route) {
        routeUI.setRoute(route);
    }
}