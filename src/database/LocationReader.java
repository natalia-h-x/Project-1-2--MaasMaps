package database;
import models.Location;

public interface LocationReader {
    public Location getLocation(String zipcode);
}
