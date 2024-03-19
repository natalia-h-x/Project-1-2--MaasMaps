package database;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import models.Location;

public class ZipCodeCSVParser implements LocationReader {
    private String filePath;
    private Map<String, Location> zipcodeToLocationMap;

    public ZipCodeCSVParser(String filePath) {
        this.filePath = filePath;
        this.zipcodeToLocationMap = new HashMap<>();
        initializeZipCodeMap();
    }

    @Override
    public Location getLocation(String zipCode) {
        return zipcodeToLocationMap.get(zipCode);
    }

    private void initializeZipCodeMap() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 3) {
                    try {
                        String zipCode = parts[0];
                        double lat = Double.parseDouble(parts[1]);
                        double lon = Double.parseDouble(parts[2]);
                        zipcodeToLocationMap.put(zipCode, new Location(lat, lon));
                    }
                    catch (NumberFormatException e) {
                        System.err.println("Skipping invalid line: " + line);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean zipCodeInFile(String zipCode) {
        return zipcodeToLocationMap.containsKey(zipCode);
    }

}
