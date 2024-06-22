package core.models.geojson;

import core.Constants;
import core.Constants.Paths;
import core.models.Location;

public abstract class Amenity extends GeoData {
    protected Amenity(Location location, String id) {
        super(location, id);
    }

    public String getIcon() {
        return Constants.Paths.AMENITY_ICON_PATH + Paths.PATH_DELIMETER + getClass().getSimpleName();
    }

    public static class ArtsCentre extends Amenity {
        private static final double WEIGHT = 0.6;

        public ArtsCentre(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Atm extends Amenity {
        private static final double WEIGHT = 0.6;

        public Atm(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Bank extends Amenity {
        private static final double WEIGHT = 0.5;

        public Bank(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Bar extends Amenity {
        private static final double WEIGHT = 0.4;

        public Bar(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Bench extends Amenity {
        private static final double WEIGHT = 0.03;

        public Bench(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class BicycleParking extends Amenity {
        private static final double WEIGHT = 0.2;

        public BicycleParking(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class BicycleRental extends Amenity {
        private static final double WEIGHT = 0.3;

        public BicycleRental(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Binoculars extends Amenity {
        private static final double WEIGHT = 0.1;

        public Binoculars(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Brothel extends Amenity {
        private static final double WEIGHT = 1;

        public Brothel(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class BureauDeChange extends Amenity {
        private static final double WEIGHT = 0.2;

        public BureauDeChange(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Cafe extends Amenity {
        private static final double WEIGHT = 0.4;

        public Cafe(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class CarRental extends Amenity {
        private static final double WEIGHT = 0.2;

        public CarRental(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class CarWash extends Amenity {
        private static final double WEIGHT = 0.1;

        public CarWash(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Casino extends Amenity {
        private static final double WEIGHT = 0.3;

        public Casino(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Cinema extends Amenity {
        private static final double WEIGHT = 0.5;

        public Cinema(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class ChargingStation extends Amenity {
        private static final double WEIGHT = 0.4;

        public ChargingStation(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Childcare extends Amenity {
        private static final double WEIGHT = 0.7;

        public Childcare(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Clinic extends Amenity {
        private static final double WEIGHT = 0.7;

        public Clinic(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Clock extends Amenity {
        private static final double WEIGHT = 0.1;

        public Clock(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class College extends Amenity {
        private static final double WEIGHT = 0.8;

        public College(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class CommunityCentre extends Amenity {
        private static final double WEIGHT = 0.5;

        public CommunityCentre(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Courthouse extends Amenity {
        private static final double WEIGHT = 0.2;

        public Courthouse(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Dentist extends Amenity {
        private static final double WEIGHT = 0.9;

        public Dentist(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Doctors extends Amenity {
        private static final double WEIGHT = 0.9;

        public Doctors(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class DrinkingWater extends Amenity {
        private static final double WEIGHT = 0.1;

        public DrinkingWater(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class FastFood extends Amenity {
        private static final double WEIGHT = 0.2;

        public FastFood(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class FireStation extends Amenity {
        private static final double WEIGHT = 1;

        public FireStation(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class FoodCourt extends Amenity {
        private static final double WEIGHT = 0.2;

        public FoodCourt(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Fountain extends Amenity {
        private static final double WEIGHT = 0.02;

        public Fountain(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Fuel extends Amenity {
        private static final double WEIGHT = 0.4;

        public Fuel(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Hospital extends Amenity {
        private static final double WEIGHT = 1;

        public Hospital(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class HuntingStand extends Amenity {
        private static final double WEIGHT = 0.05;

        public HuntingStand(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class IceCream extends Amenity {
        private static final double WEIGHT = 0.4;

        public IceCream(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Information extends Amenity {
        private static final double WEIGHT = 0.3;

        public Information(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Library extends Amenity {
        private static final double WEIGHT = 0.5;

        public Library(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class LuggageLocker extends Amenity {
        private static final double WEIGHT = 0.05;

        public LuggageLocker(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Marketplace extends Amenity {
        private static final double WEIGHT = 0.7;

        public Marketplace(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class MopedParking extends Amenity {
        private static final double WEIGHT = 0.1;

        public MopedParking(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Nightclub extends Amenity {
        private static final double WEIGHT = 0.2;

        public Nightclub(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class NursingHome extends Amenity {
        private static final double WEIGHT = 0.4;

        public NursingHome(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Parking extends Amenity {
        private static final double WEIGHT = 0.1;

        public Parking(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class ParkingEntrance extends Amenity {
        private static final double WEIGHT = 0.1;

        public ParkingEntrance(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class ParkingSpace extends Amenity {
        private static final double WEIGHT = 0.1;

        public ParkingSpace(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Pharmacy extends Amenity {
        private static final double WEIGHT = 0.7;

        public Pharmacy(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class PhotoBooth extends Amenity {
        private static final double WEIGHT = 0.05;

        public PhotoBooth(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class PlaceOfWorship extends Amenity {
        private static final double WEIGHT = 0.6;

        public PlaceOfWorship(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Police extends Amenity {
        private static final double WEIGHT = 0.3;

        public Police(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class PostBox extends Amenity {
        private static final double WEIGHT = 0.2;

        public PostBox(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class PostOffice extends Amenity {
        private static final double WEIGHT = 0.6;

        public PostOffice(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class PrepSchool extends Amenity {
        private static final double WEIGHT = 0.4;

        public PrepSchool(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Pub extends Amenity {
        private static final double WEIGHT = 0.3;

        public Pub(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class PublicBookcase extends Amenity {
        private static final double WEIGHT = 0.3;

        public PublicBookcase(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Recycling extends Amenity {
        private static final double WEIGHT = 0.6;

        public Recycling(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Restaurant extends Amenity {
        private static final double WEIGHT = 0.6;

        public Restaurant(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Resthouse extends Amenity {
        private static final double WEIGHT = 0.2;

        public Resthouse(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class SanitaryDumpStation extends Amenity {
        private static final double WEIGHT = 0.2;

        public SanitaryDumpStation(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class School extends Amenity {
        private static final double WEIGHT = 0.9;

        public School(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Shelter extends Amenity {
        private static final double WEIGHT = 0.3;

        public Shelter(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Shower extends Amenity {
        private static final double WEIGHT = 0.02;

        public Shower(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class SocialFacility extends Amenity {
        private static final double WEIGHT = 0.4;

        public SocialFacility(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Taxi extends Amenity {
        private static final double WEIGHT = 0.3;

        public Taxi(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Theatre extends Amenity {
        private static final double WEIGHT = 0.7;

        public Theatre(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Toilets extends Amenity {
        private static final double WEIGHT = 0.2;

        public Toilets(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Townhall extends Amenity {
        private static final double WEIGHT = 1;

        public Townhall(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class University extends Amenity {
        private static final double WEIGHT = 0.8;

        public University(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class VendingMachine extends Amenity {
        private static final double WEIGHT = 0.1;

        public VendingMachine(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Veterinary extends Amenity {
        private static final double WEIGHT = 0.6;

        public Veterinary(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class WasteBasket extends Amenity {
        private static final double WEIGHT = 0.1;

        public WasteBasket(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class WaterPoint extends Amenity {
        private static final double WEIGHT = 0.05;

        public WaterPoint(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }
}
