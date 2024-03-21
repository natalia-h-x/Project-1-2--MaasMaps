package core;

import ui.map.ProxyMap;

public class Context {
    private static Context context;
    private ProxyMap proxyMap;

    private Context() {}

    /** Lazily create an object of this singleton when any interation happens with this class. */
    static {
        context = new Context();
    }

    public static Context getContext() {
        return context;
    }

    public ProxyMap getMap() {
        return proxyMap;
    }

    public void setMap(ProxyMap proxyMap) {
        this.proxyMap = proxyMap;
    }
}
