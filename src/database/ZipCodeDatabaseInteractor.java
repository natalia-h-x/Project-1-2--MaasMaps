package database;

import models.Location;

public class ZipCodeDatabaseInteractor implements LocationReader {
    private static ZipCodeDatabaseInteractor zipCodeDatabaseInteractor;
    private ZipCodeCSVParser csvFile = new ZipCodeCSVParser();

    static {
        zipCodeDatabaseInteractor = new ZipCodeDatabaseInteractor();
    }

    private ZipCodeDatabaseInteractor() {}

    public static ZipCodeDatabaseInteractor getZipCodeDatabaseInteractor() {
        return zipCodeDatabaseInteractor;
    }

    @Override
    public Location getLocation(String zipcode) {
        try {
            if (csvFile.zipCodeInFile(zipcode))
                return csvFile.getLocation(zipcode);

            return new ZipCodeAPIRequest().getLocation(zipcode);
        }
        catch (Exception e) {
            return null;
        }
    }
}
