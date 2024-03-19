package core;

import ui.map.Map;

public class Context {
    private static Context context;
    private Map map;

    private Context() {}

    static {
        context = new Context();
    }

    public static Context getContext() {
        return context;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
