package database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Location;

public class ZipCodeCSVParser implements LocationReader {
    private Map<String, Location> zipcodeToLocationMap;

    public ZipCodeCSVParser() {
        this.zipcodeToLocationMap = new HashMap<>();
        initializeZipCodeMap();
    }

    @Override
    public Location getLocation(String zipCode) {
        return zipcodeToLocationMap.get(zipCode);
    }

    private void initializeZipCodeMap() {
        List<String> data = FileManager.convertFileToList();

        for (String line : data)
        {
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

    public boolean zipCodeInFile(String zipCode) {
        return zipcodeToLocationMap.containsKey(zipCode);
    }

}
