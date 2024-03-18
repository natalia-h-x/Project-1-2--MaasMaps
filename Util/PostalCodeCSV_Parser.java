package Util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PostalCodeCSV_Parser {
    public static void main(String[] args) {
        String filePath = "Database/MassZipLatLon.csv";
        String zipCodeToSearch = "6211AL";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean found = false;

            // Read the CSV line by line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(zipCodeToSearch)) {
                    found = true;
                    double lat = Double.parseDouble(parts[1]);
                    double lon = Double.parseDouble(parts[2]);
                    System.out.println("Latitude: " + lat);
                    System.out.println("Longitude: " + lon);
                    break;
                }
            }

            if (!found) {
                System.out.println("Zip code " + zipCodeToSearch + " does not exist.");
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
