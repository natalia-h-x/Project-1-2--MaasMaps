package core;

import java.util.Random;

import core.zipcode.ZipCodeDatabase;
import lombok.Getter;
import lombok.Setter;
import ui.map.ProxyMap;
import ui.route.ResultsProxy;

/**
 * This class is the Context singleton of our program that harbour all share objects amoungst classes.
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
    private ResultsProxy resultsPanel;
    private Random random;

    private Context() {}

    /** Lazily create an object of this singleton when any interation happens with this class. */
    public static Context getContext() {
        if (context == null)
            context = new Context();

        return context;
    }

    public ZipCodeDatabase getZipCodeDatabase() {
        if (zipCodeDatabase == null)
            zipCodeDatabase = new ZipCodeDatabase();

        return zipCodeDatabase;
    }

    public Random getRandom() {
        if (random == null)
            random = new Random();

        return random;
    }
}
