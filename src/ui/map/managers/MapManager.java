package ui.map.managers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapManager {
    private MapManager() {}

    public static BufferedImage getMapData() throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(new File("resources/PlaceholderMap.jpg"));

        // 9_9

        return img;
    }
}