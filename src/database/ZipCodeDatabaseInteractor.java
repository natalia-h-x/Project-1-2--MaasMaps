package database;

import models.Location;

public class ZipCodeDatabaseInteractor implements LocationReader {
    private ZipCodeCSVParser csvFile = new ZipCodeCSVParser();

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
