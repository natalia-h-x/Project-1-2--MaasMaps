package core.managers.amenity;

import core.Constants;
import core.models.Location;
import core.models.geojson.GeoData;
import lombok.Data;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AmenityAccessibilityCalculator {
    public static void main(String[] args) {
        Map<String, List<GeoData>> amenities = AmenitySerializationManager.getGeoData();
        List<PostalCodeData> postalCodes = readPostalCodes(Constants.Paths.POSTAL_COORDS_FILE);

        calculateAccessibilityMetrics(postalCodes, amenities);
        normalizeAccessibilityMetrics(postalCodes);
        writeResultsToCSV(postalCodes, Constants.Paths.ACCESSIBILITY_FILE);
    }

    private static List<PostalCodeData> readPostalCodes(String csvFilePath) {
        List<PostalCodeData> postalCodes = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(csvFilePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String postalCode = values[0];
                double latitude = Double.parseDouble(values[1]);
                double longitude = Double.parseDouble(values[2]);
                postalCodes.add(new PostalCodeData(postalCode, latitude, longitude));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return postalCodes;
    }

    private static void calculateAccessibilityMetrics(List<PostalCodeData> postalCodes, Map<String, List<GeoData>> amenities) {
        for (PostalCodeData postalCode : postalCodes) {
            double accessibilityMetric = calculateAccessibility(postalCode, amenities);
            postalCode.setAccessibilityMetric(accessibilityMetric);
        }
    }

    private static double calculateAccessibility(PostalCodeData postalCode, Map<String, List<GeoData>> amenities) {
        double dL = averageDistanceToNearestAmenity(postalCode, amenities.values().stream().flatMap(Collection::stream).toList());
        double Ai = 0;

        for (Map.Entry<String, List<GeoData>> amenity : amenities.entrySet()) {
            double Aj = 0.0;

            for (GeoData geoData : amenity.getValue()) {
                double distance = Math.sqrt(distance(postalCode.getLocation(), geoData.getLocation()));
                double Wj = AmenityAccessibilityManager.getAmenityFrequency(geoData.toString());

                Aj += Wj * Math.exp(-distance / dL);
            }

            if (!amenity.getValue().isEmpty()) {
                double wL = amenity.getValue().get(0).getWeight();

                Aj *= wL;
            }

            Ai += Aj;
        }

        return Ai;
    }

    private static double averageDistanceToNearestAmenity(PostalCodeData postalCode, List<GeoData> amenities) {
        double totalDistance = 0.0;
        int count = 0;

        for (GeoData amenity : amenities) {
            totalDistance += distance(postalCode.getLocation(), amenity.getLocation());
            count++;
        }

        return count > 0 ? totalDistance / count : 0.0;
    }

    private static double distance(Location loc1, Location loc2) {
        double lat1 = loc1.getLatitude();
        double lon1 = loc1.getLongitude();
        double lat2 = loc2.getLatitude();
        double lon2 = loc2.getLongitude();

        final int R = 6371000; // Radius of the earth in meters

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    private static void normalizeAccessibilityMetrics(List<PostalCodeData> postalCodes) {
        double minMetric = Double.MAX_VALUE;
        double maxMetric = Double.MIN_VALUE;

        for (PostalCodeData postalCode : postalCodes) {
            double metric = postalCode.getAccessibilityMetric();
            if (metric < minMetric) {
                minMetric = metric;
            }
            if (metric > maxMetric) {
                maxMetric = metric;
            }
        }

        for (PostalCodeData postalCode : postalCodes) {
            double metric = postalCode.getAccessibilityMetric();
            double normalizedMetric = (metric - minMetric) / (maxMetric - minMetric);
            postalCode.setAccessibilityMetric(normalizedMetric);
        }
    }

    private static void writeResultsToCSV(List<PostalCodeData> postalCodes, String outputFilePath) {
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.append("Zip,Lat,Lon,AccessibilityMetric\n");
            for (PostalCodeData postalCode : postalCodes) {
                writer.append(postalCode.getPostalCode()).append(",")
                      .append(String.valueOf(postalCode.getLatitude())).append(",")
                      .append(String.valueOf(postalCode.getLongitude())).append(",")
                      .append(String.valueOf(postalCode.getAccessibilityMetric())).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

@Data
class PostalCodeData {
    private String postalCode;
    private double latitude;
    private double longitude;
    private double accessibilityMetric;

    public PostalCodeData(String postalCode, double latitude, double longitude) {
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location getLocation() {
        return new Location(latitude, longitude);
    }
}
