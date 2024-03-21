package database;

import java.util.ArrayList;
import java.util.List;

import core.managers.FileManager;
import models.Location;
import models.ZipCode;

public class ZipCodeCSVParser implements LocationReader {
    private List<ZipCode> zipCodeList;

    public ZipCodeCSVParser() {
        this.zipCodeList = new ArrayList<>();

        initializeZipCodeMap();
    }

    @Override
    public Location getLocation(String zipCode) {
        return zipCodeList.get(zipCodeList.indexOf(new ZipCode(zipCode, null))).getLocation();
    }

    private void initializeZipCodeMap() {
        List<String> data = FileManager.getZipCodeLocations();

        for (String line : data) {
            String[] parts = line.split(",");

            if (parts.length == 3) {
                try {
                    double lat = Double.parseDouble(parts[1]);
                    double lon = Double.parseDouble(parts[2]);
                    ZipCode zipCode = new ZipCode(parts[0], new Location(lat, lon));
                    zipCodeList.add(zipCode);
                }
                catch (NumberFormatException e) {
                    System.err.println("Skipping invalid line: " + line);
                }
            }
        }
    }

    public boolean zipCodeInFile(String zipCode) {
        return zipCodeList.contains(new ZipCode(zipCode, null));
    }
}
