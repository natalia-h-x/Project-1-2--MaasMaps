import algorithms.utils.DistanceCalculator;
import database.ZipCodeDatabaseInteractor;
import models.Location;
import transport.Biking;
import transport.TransportMode;
import transport.Walking;
import ui.MaasMapsUI;

/*
 *  Location 1: Latitude: 50.8492, Longitude: 5.6889 (This could be near the Maastricht University area)
 *  Location 2: Latitude: 50.8418, Longitude: 5.6671 (This might be close to the St. Pietersberg area)
 */
public class Main {
    public static void main(String[] args) {
        new MaasMapsUI();

        /*
         * Location fetching logic
         */
        ZipCodeDatabaseInteractor db = ZipCodeDatabaseInteractor.getZipCodeDatabaseInteractor();

        Location loc1 = db.getLocation("6229EN");
        Location loc2 = db.getLocation("6211LC");

        TransportMode bike = new Biking();
        TransportMode walk = new Walking();

        double travelTimeWalking = walk.calculateTravelTime(loc1, loc2);
        double travelTimeBiking = bike.calculateTravelTime(loc1, loc2);

        double travelDistance = DistanceCalculator.haversine(loc1, loc2);

        System.out.println();
        System.out.println("Start:  " + loc1);
        System.out.println("Finish: " + loc2);
        System.out.println();
        System.out.println("Walking time in mins: " + travelTimeWalking);
        System.out.println("Biking time in mins: " + travelTimeBiking);
        System.out.println("Distance in km: " + travelDistance);
        System.out.println();
    }
}
