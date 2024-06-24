package core.zipcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.managers.ExceptionManager;
import core.managers.FileManager;
import core.models.Location;
import core.models.ZipCode;
import lombok.Getter;

/**
 * This class reads and parses the zipcode data from the csv file.
 *
 * @author Kimon Navridis
 * @author Natalia Hadjisoteriou
 * @author Sian Lodde
 */
@Getter
public class CSVParser implements LocationReader {
    private List<ZipCode> zipCodeList;
    private Map<String, Integer> populationSize;

    public CSVParser() {
        this.zipCodeList = new ArrayList<>();
        this.populationSize = new HashMap<>();

        initializeZipCodeList();
        initializeDemographics();
    }

    /**
     * Check if the zipCode String is already present in the list.
     */
    @Override
    public Location getLocation(String zipCode) {
        return zipCodeList.get(zipCodeList.indexOf(new ZipCode(zipCode, null))).getLocation();
    }

    private void initializeZipCodeList() {
        try {
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
        catch (IOException e) {
            ExceptionManager.handle(e);
        }
    }

    private void initializeDemographics() {
        try {
            List<String> data = FileManager.getDemographicData();

            for (String line : data) {
                String[] parts = line.split(";");

                if (parts.length == 36) {
                    try {
                        if (!populationSize.containsKey(parts[0].substring(0, parts[0].length() - 2))) {
                            populationSize.computeIfAbsent(parts[0].substring(0, parts[0].length() - 2), s -> Integer.parseInt(parts[1]));
                        } else {
                            populationSize.put(parts[0].substring(0, parts[0].length() - 2), (populationSize.get(parts[0].substring(0, parts[0].length() - 2)) + Integer.parseInt(parts[1])));
                        }
                    }
                    catch (NumberFormatException e) {
                        System.err.println("Skipping invalid line: " + line);
                    }
                }
            }
        }
        catch (IOException e) {
            ExceptionManager.handle(e);
        }
    }

    public int populationNumber(String zipCode) {
        return populationSize.get(zipCode);
    }

    public boolean containsZipCode(String zipCode) {
        return zipCodeList.contains(new ZipCode(zipCode, null));
    }
}
