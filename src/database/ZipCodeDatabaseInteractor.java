package database;

import javax.swing.JOptionPane;

import models.Location;
import models.ZipCode;

/**
 * Singleton database for only loading the zip codes once.
 */
public class ZipCodeDatabaseInteractor implements LocationReader {
    private static ZipCodeDatabaseInteractor zipCodeDatabaseInteractor;

    private ZipCodeCSVParser csvParser = new ZipCodeCSVParser();
    private ZipCodeAPIRequest apiRequest = new ZipCodeAPIRequest();

    /** Lazily create an object of this singleton when any interation happens with this class. */
    static {
        zipCodeDatabaseInteractor = new ZipCodeDatabaseInteractor();
    }

    private ZipCodeDatabaseInteractor() {}

    public static ZipCodeDatabaseInteractor getZipCodeDatabaseInteractor() {
        return zipCodeDatabaseInteractor;
    }

    @Override
    public Location getLocation(String zipcode) throws IllegalArgumentException, IllegalStateException {
        if (!ZipCode.isValid(zipcode)) {
            throw new IllegalArgumentException(zipcode + " not a valid postal code.");
        }

        if (csvParser.zipCodeInFile(zipcode))
            return csvParser.getLocation(zipcode);

        return apiRequest.getLocation(zipcode);
    }
}
