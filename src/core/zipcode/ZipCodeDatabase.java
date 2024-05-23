package core.zipcode;

import java.util.List;

import core.models.Location;
import core.models.ZipCode;

/**
 * Singleton database for only loading the zip codes once.
 *
 * @author Kimon Navridis
 * @author Sian Lodde
 */
public class ZipCodeDatabase implements LocationReader {
    private CSVParser csvParser = new CSVParser();
    private APIRequest apiRequest = new APIRequest();

    @Override
    public Location getLocation(String zipcode) throws IllegalArgumentException, IllegalStateException {
        if (!ZipCode.isValid(zipcode)) {
            throw new IllegalArgumentException(zipcode + " not a valid postal code.");
        }

        if (csvParser.containsZipCode(zipcode))
            return csvParser.getLocation(zipcode);

        return apiRequest.getLocation(zipcode);
    }

    public List<ZipCode> getZipCodes() {
        return csvParser.getZipCodeList();
    }
}
