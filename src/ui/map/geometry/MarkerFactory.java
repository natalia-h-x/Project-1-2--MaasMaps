package ui.map.geometry;

import java.awt.image.BufferedImage;

import core.managers.FileManager;
import models.Location;

import java.io.IOException;

import org.w3c.dom.DOMException;

import static constants.Constants.Paths.BUS_STOP_ICON;
import static constants.Constants.Paths.RANDOM_ICON;
import static constants.Constants.Paths.A_IMAGE;
import static constants.Constants.Paths.B_IMAGE;

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
    private static BufferedImage randomImage;
    private static BufferedImage aImage;
    private static BufferedImage bImage;

    static {
        try {
            busStopImage = FileManager.getImage(BUS_STOP_ICON);
            randomImage = FileManager.getImage(RANDOM_ICON);
            aImage = FileManager.getImage(A_IMAGE);
            bImage = FileManager.getImage(B_IMAGE);
        }
        catch (IOException e) {
            throw new DOMException((short) 0, "Could not find icon.");
        }
    }

    public static ImageMarker createBusImageMarker(Location location){
        return new ImageMarker(location, busStopImage);
    }

    public static ImageMarker createRandomImageMarker(Location location){
        return new ImageMarker(location, randomImage);
    }

    public static ImageMarker createAImageMarker(Location location){
        return new ImageMarker(location, aImage);
    }

    public static ImageMarker createBImageMarker(Location location){
        return new ImageMarker(location, bImage);
    }
}
