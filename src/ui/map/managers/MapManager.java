package ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapManager {
    public static BufferedImage getMapData() throws IOException{
        BufferedImage img = null;
        img = ImageIO.read(new File("C:\\Users\\sianl\\University\\prj1-2\\project 1-2 MaasMaps\\src\\Database\\PlaceholderMap.jpg"));

        return img;
    }
}