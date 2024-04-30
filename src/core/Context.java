package core;

import core.database.ZipCodeDatabase;
import lombok.Getter;
import lombok.Setter;
import ui.map.ProxyMap;

/**
 * This class represents the Context singleton that harbour all share objets amoungst classes.
 *
 * @author Sian Lodde
 * @author Alexandra Plishkin Islamgulova
 */
@Getter
@Setter
public class Context {
    private static Context context;
    private ProxyMap map;
    private ZipCodeDatabase zipCodeDatabase;

    private Context() {}

    /** Lazily create an object of this singleton when any interation happens with this class. */
    static {
        context = new Context();
    }

    public static Context getContext() {
        return context;
    }

    public ZipCodeDatabase getZipCodeDatabase() {
        if (zipCodeDatabase == null)
            zipCodeDatabase = new ZipCodeDatabase();

        return zipCodeDatabase;
    }
}
