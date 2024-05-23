package core.managers;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import core.Constants;
import core.Constants.Paths;

/**
 * This class represents the file manager to read the contents from the csv file.
 *
 * @author Alexandra Plishkin Islamgulova
 */
public class FileManager {
    private FileManager() {}

    public static List<String> getZipCodeLocations() throws IOException {
        List<String> locations = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(Constants.Paths.POSTAL_COORDS_FILE));
        String line = br.readLine(); // Skip header line

        while ((line = br.readLine()) != null) {
            locations.add(line);
        }

        br.close();

        return locations;
    }

    public static BufferedImage getMapImage() throws IOException {
        return getImage(Paths.MAP_IMAGE);
    }

    public static BufferedImage getImage(String path) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(new File(path));

        return img;
    }

    public static void appendToFile(String path, String content) throws IOException {
        Files.write(java.nio.file.Paths.get(path), content.getBytes(), StandardOpenOption.APPEND);
    }

    public static String[] readLines(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
        }
        return lines.toArray(String[]::new);
    }
}

