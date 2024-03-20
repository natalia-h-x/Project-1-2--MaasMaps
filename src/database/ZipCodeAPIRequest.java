package database;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import constants.Constants;
import models.Location;

public class ZipCodeAPIRequest implements LocationReader {
    public Location getLocation(String postcode) {
        try {
            URL url = new URL(Constants.BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000); // 5 seconds for connection timeout
            connection.setReadTimeout(5000); // 5 seconds for read timeout

            String jsonInputString = "{\"postcode\": \"" + postcode + "\"}";

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(jsonInputString);
                wr.flush();
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response
                double latitude = extractValue(response.toString(), "latitude");
                double longitude = extractValue(response.toString(), "longitude");

                return new Location(latitude, longitude);
            }
            else {
                System.out.println("Error: Received HTTP response code " + responseCode);
            }
        }
        catch (java.net.SocketTimeoutException e) {
            System.out.println("Error: Timeout while connecting to the API. Please check your network connection.");
        }
        catch (java.net.UnknownHostException e) {
            System.out.println("Error: Unable to reach the API. Please check if you are connected to the university network or VPN.");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while fetching the coordinates.");
        }

        return null; // no location
    }

    // Extract value from JSON string
    private static double extractValue(String json, String key) {
        String keyWithQuotes = "\"" + key + "\":";
        int startIndex = json.indexOf(keyWithQuotes) + keyWithQuotes.length();

        if (startIndex == -1) {
            // key not found
            return Double.NaN; // TODO (maybe) throw error
        }

        int endIndex = json.indexOf(",", startIndex);

        if (endIndex == -1) { // If it's the last element, there might not be a comma
            endIndex = json.indexOf("}", startIndex);
        }

        if (endIndex == -1) {
            // Proper JSON closure not found
            return Double.NaN; // TODO (maybe) throw error
        }

        String value = json.substring(startIndex, endIndex).trim();

        // remove porential quotes
        value = value.replaceAll("^\"|\"$", "");
        return Double.parseDouble(value);
    }
}