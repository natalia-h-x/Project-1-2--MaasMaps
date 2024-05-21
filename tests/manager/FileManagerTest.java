package manager;


import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import core.Constants.Paths;
import core.managers.FileManager;
import core.models.Location;
import core.models.ZipCode;

public class FileManagerTest {
    
    @Test
    public void testGettingZipCodeLocations() {
        List<String> data;
        try {
            data = FileManager.getZipCodeLocations();
            for (String line : data) {
                String[] parts = line.split(",");
                
                if (parts.length == 3) {
                    double lat = Double.parseDouble(parts[1]);
                    double lon = Double.parseDouble(parts[2]);
                    ZipCode zipCode = new ZipCode(parts[0], new Location(lat, lon));
                    System.out.println("Code: " + zipCode.getCode() + ", Location: " + zipCode.getLocation());
                }
            }
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    public void getImageTest() {
        try {
            FileManager.getMapImage();
            FileManager.getImage(Paths.RESOURCES_PLACEHOLDER_MAP_PNG);
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    public void readLinesTest() {
        try {
            File file = new File("resources/gtfs/routes.txt");
            String[] lines;
            lines = FileManager.readLines(file);
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            fail(e);
        }
    }
}
