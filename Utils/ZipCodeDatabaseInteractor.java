package Utils;

import Objects.Location;
import Utils.DatabaseUtils.LocationReader;
import Utils.DatabaseUtils.ZipCodeAPI_Request;
import Utils.DatabaseUtils.ZipCodeCSV_Parser;

public class ZipCodeDatabaseInteractor implements LocationReader {
    
    ZipCodeCSV_Parser csvFile = new ZipCodeCSV_Parser("Database/MassZipLatLon.csv");

    public Location getLocation(String zipcode) {

        try {
            if (csvFile.zipCodeInFile(zipcode)) {
                return csvFile.getLocation(zipcode);
            } else {
                ZipCodeAPI_Request request = new ZipCodeAPI_Request();
                return request.getLocation(zipcode);
            }
        } catch (Exception e) {
            return null;
        }

    }
}
