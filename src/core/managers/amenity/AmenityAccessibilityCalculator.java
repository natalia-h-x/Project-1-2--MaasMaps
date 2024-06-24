package core.managers.amenity;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.Constants;
import core.Context;
import core.managers.map.DistanceManager;
import core.models.Location;
import core.models.ZipCode;
import core.models.geojson.GeoData;
import lombok.Data;

public class AmenityAccessibilityCalculator {
    private static Map<String, Double> averageDistanceForAmenityType;

    public static void main(String[] args) {
        Map<String, List<GeoData>> amenities = AmenitySerializationManager.getGeoData();
        List<PostalCodeData> postalCodes = new ArrayList<>();

        List<ZipCode> zipCodes = Context.getContext().getZipCodeDatabase().getZipCodes();

        for (ZipCode zipCode : zipCodes) {
            if (!zipCode.getCode().equals("6229EN"))
                postalCodes.add(new PostalCodeData(zipCode.getCode(), zipCode.getLocation().getLatitude(), zipCode.getLocation().getLongitude()));
        }

        calculateAccessibilityMetrics(postalCodes, amenities);
        normalizeAccessibilityMetrics(postalCodes);
        writeResultsToCSV(postalCodes, Constants.Paths.ACCESSIBILITY_FILE);
    }
    
    private static void calculateAccessibilityMetrics(List<PostalCodeData> postalCodes, Map<String, List<GeoData>> amenities) {
        for (PostalCodeData postalCode : postalCodes) {
            double accessibilityMetric = calculateAccessibility(postalCode, amenities, postalCodes);
            postalCode.setAccessibilityMetric(accessibilityMetric);
        }
    }

    private static double calculateAccessibility(PostalCodeData postalCode, Map<String, List<GeoData>> amenities, List<PostalCodeData> postalCodes) {
        double Ai = 0.0;
        int Wj = Context.getContext().getZipCodeDatabase().getPopulation(postalCode.getPostalCode());

        for (Map.Entry<String, List<GeoData>> amenity : amenities.entrySet()) {
            double Aj = 0.0;

            for (GeoData geoData : amenity.getValue()) {
                double distance = DistanceManager.haversine(postalCode.getLocation(), geoData.getLocation());
                List<GeoData> geoDataOfCertainType = AmenitySerializationManager.getGeoData(geoData.toString().equals("shop") || geoData.toString().equals("tourism")? geoData.toString() : "amenity", geoData.toString());
                
                if (averageDistanceForAmenityType == null)
                    averageDistanceForAmenityType = new HashMap<>();

                double dL = averageDistanceForAmenityType.computeIfAbsent(geoData.toString(), s -> averageDistanceToNearestAmenityType(postalCodes, geoDataOfCertainType));

                Aj += Wj * Math.exp(-distance / dL);
            }

            if (!amenity.getValue().isEmpty()) {
                double wL = amenity.getValue().get(0).getWeight();

                Aj *= (wL*1000);
            }

            Ai += Aj;
        }

        return Ai;
    }

    private static double averageDistanceToNearestAmenityType(List<PostalCodeData> postalCodes, List<GeoData> amenities) {
        double totalDistance = 0.0;
        int count = 0;

        for (PostalCodeData postalCode : postalCodes) {
            double nearest = Double.MAX_VALUE;

            for (GeoData amenity : amenities) {
                double dist = DistanceManager.haversine(postalCode.getLocation(), amenity.getLocation());
                if (dist < nearest) {
                    nearest = dist;
                }
            }

            if (nearest != Double.MAX_VALUE) {
                totalDistance += nearest;
                count++;
            }
        }

        return count > 0 ? totalDistance / count : 0.0;
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
