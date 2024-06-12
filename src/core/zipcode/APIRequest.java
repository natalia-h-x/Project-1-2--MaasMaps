package core.zipcode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import core.Constants.Paths;
import core.managers.FileManager;
import core.managers.SerializationManager;
import core.models.Location;

/**
 * This class gets zipcode data by requesting it from an API.
 *
 * @author Kimon Navridis
 * @author Sian Lodde
 * @author Natalia Hadjisoteriou
 */
public class APIRequest implements LocationReader {
    public Location getLocation(String zipCode) throws IllegalStateException {
        try {
            URL url = new URL(Paths.POSTAL_COORDS_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000); // 5 seconds for connection timeout
            connection.setReadTimeout(5000); // 5 seconds for read timeout

            String jsonInputString = "{\"postcode\": \"" + zipCode + "\"}";

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
                double latitude = SerializationManager.extractJSON(response.toString(), "latitude");
                double longitude = SerializationManager.extractJSON(response.toString(), "longitude");

                // Cache the result so that we do not have to ask the API for this zipcode again
                FileManager.appendToFile(Paths.POSTAL_COORDS_FILE, "%s,%s,%s".formatted(zipCode, latitude, longitude));

                return new Location(latitude, longitude);
            }
            else {
                throw new IllegalStateException("Error: Received HTTP response code " + responseCode);
            }
        }
        catch (java.net.SocketTimeoutException e) {
            throw new IllegalArgumentException("Valid postal code but timeout occurs while connecting to the API. Please check your network connection.", e);
        }
        catch (java.net.UnknownHostException e) {
            throw new IllegalArgumentException("Valid postal code but unable to reach the API. Please check if you are connected to the university network or VPN.", e);
        }
        catch (MalformedURLException e) {
            throw new IllegalArgumentException("Valid postal code but not valid link: %s.".formatted(Paths.POSTAL_COORDS_API_URL), e);
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Valid postal code but an IOException occurred", e);
        }
    }
}