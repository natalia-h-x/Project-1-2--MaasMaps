import fileaccess.ZipCodeDatabaseInteractor;
import models.Location;
import transport.*;
import ui.map.MaasMapsUI;

/*
 *  Location 1: Latitude: 50.8492, Longitude: 5.6889 (This could be near the Maastricht University area)
 *  Location 2: Latitude: 50.8418, Longitude: 5.6671 (This might be close to the St. Pietersberg area)
 */
public class Main {
    public static void main(String[] args) {

        // /*
        //  * Location fetching logic
        //  */
        // ZipCodeDatabaseInteractor db = new ZipCodeDatabaseInteractor();

        // Location loc1 = db.getLocation("6211BE");
        // Location loc2 = db.getLocation("6224KB");

        // TransportMode bike = new Biking();
        // TransportMode walk = new Walking();

        // System.out.println();
        // System.out.println("Start:  " + loc2);
        // System.out.println("Finish: " + loc2);
        // System.out.println();
        // System.out.println("Walking time in mins: " + walk.calculateTravelTime(loc1, loc2));
        // System.out.println("Biking time in mins: " + bike.calculateTravelTime(loc1, loc2));
        // System.out.println();

        new MaasMapsUI();
    }

}
