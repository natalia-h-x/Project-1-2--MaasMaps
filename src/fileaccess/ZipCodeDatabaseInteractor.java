package fileaccess;

import models.Location;

public class ZipCodeDatabaseInteractor implements LocationReader {
    ZipCodeCSVParser csvFile = new ZipCodeCSVParser("resources/MassZipLatLon.csv");

    @Override
    public Location getLocation(String zipcode) {
        try {
            if (csvFile.zipCodeInFile(zipcode)) {
                return csvFile.getLocation(zipcode);
            }
            else {
                ZipCodeAPIRequest request = new ZipCodeAPIRequest();
                return request.getLocation(zipcode);
            }
        }
        catch (Exception e) {
            return null;
        }
    }
}
