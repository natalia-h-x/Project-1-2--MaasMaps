package fileaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileManager {

    public static List<String> convertFileToList() {
        List<String> locations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Databases.Paths.MAAS_ZIP_LATLON_PATH))) {
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                locations.add(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return locations;
    }
}
