package ui.map.geometry;

import static core.Constants.Paths.A_IMAGE;
import static core.Constants.Paths.BUS_STOP_ICON;
import static core.Constants.Paths.B_IMAGE;

import java.awt.image.BufferedImage;
import java.io.IOException;

import core.managers.ExceptionManager;
import core.managers.FileManager;
import core.models.Location;

/**
 * This class is a marker factory to create different markers in the map.
 * @author Sheena Gallagher
 * @author Sian Lodde
 * @author Alexandra Plishkin Islamgulova
 * @author Meriç Uruş
 */
public class MarkerFactory {
    private MarkerFactory() {}

    private static BufferedImage busStopImage;
    private static BufferedImage aImage;
    private static BufferedImage bImage;

    static {
        try {
            busStopImage = FileManager.getImage(BUS_STOP_ICON);
            aImage = FileManager.getImage(A_IMAGE);
            bImage = FileManager.getImage(B_IMAGE);
        }
        catch (IOException e) {
            ExceptionManager.handle(new IllegalArgumentException("Could not find icon.", e));
        }
    }

    public static ImageMarker createBusImageMarker(Location location){
        return new ImageMarker(location, busStopImage);
    }

    public static ImageMarker createAImageMarker(Location location){
        return new ImageMarker(location, aImage);
    }

    public static ImageMarker createBImageMarker(Location location){
        return new ImageMarker(location, bImage);
    }
}
