import Objects.Location;
import TransportModes.*;



/*
 *  Location 1: Latitude: 50.8492, Longitude: 5.6889 (This could be near the Maastricht University area)
 *  Location 2: Latitude: 50.8418, Longitude: 5.6671 (This might be close to the St. Pietersberg area)
 */
public class Main {
    public static void main(String[] args) {
        Location loc1 = new Location(50.8492, 5.6889);
        Location loc2 = new Location(50.8418, 5.6671);
        TransportMode bike = new Biking();
        TransportMode walk = new Walking();

        System.out.println();
        System.out.println("Start: ");
        System.out.println(loc1);
        System.out.println("Finish: ");
        System.out.println(loc2);
        System.out.println();
        System.out.println("Walking time in mins: " + walk.calculateTravelTime(loc1, loc2));
        System.out.println("Biking time in mins: " + bike.calculateTravelTime(loc1, loc2));
        System.out.println();

    }
    
}
