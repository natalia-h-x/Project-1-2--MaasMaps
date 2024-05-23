package database;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import core.managers.DatabaseManager;
import core.models.BusStop;

public class SQLConnectionTest {
    @Test
    public void test1() {
        List<?>[] attributes = DatabaseManager.executeQuery("select a.stop_lon as from_stop_lon, a.stop_lat as from_stop_lat, b.stop_lon as to_stop_lon, b.stop_lat as to_stop_lat, a.stop_id\r\n" + //
                        "from stops a\r\n" + //
                        "left join transfers on a.stop_id = transfers.from_stop_id \r\n" + //
                        "left join stops b on b.stop_id = transfers.to_stop_id \r\n", new ArrayList<Double>(), new ArrayList<Double>(), new ArrayList<Double>(), new ArrayList<Double>());

        for (int i = 0; i < attributes[0].size(); i++) {
            try {
                BusStop fromLocation = new BusStop(Double.parseDouble((String) attributes[0].get(i)), Double.parseDouble((String) attributes[1].get(i)));
                BusStop toLocation = new BusStop(Double.parseDouble((String) attributes[2].get(i)), Double.parseDouble((String) attributes[3].get(i)));
                int weight = (int) (1000 * Double.parseDouble((String) attributes[5].get(i)));
                System.out.println("%s -> %s: %s".formatted(fromLocation, toLocation, weight));
            } catch (NullPointerException e) {
            
            } catch (IndexOutOfBoundsException e) {
                
            }

        }
    }
}