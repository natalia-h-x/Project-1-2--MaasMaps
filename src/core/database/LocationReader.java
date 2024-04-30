package core.database;

import core.models.Location;

/**
 * Interface that gets implemented by all classes that handle reading zipcode data.
 * 
 * @author Kimon Navridis
 */
public interface LocationReader {
    public Location getLocation(String zipcode);
}
