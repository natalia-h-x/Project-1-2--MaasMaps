package Utils.DatabaseUtils;
import Objects.Location;

public interface LocationReader {

    public Location getLocation(String zipcode);
    
}
