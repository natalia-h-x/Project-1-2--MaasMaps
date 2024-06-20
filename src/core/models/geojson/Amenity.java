package core.models.geojson;

import core.Constants;
import core.models.Location;

public abstract class Amenity extends GeoData {
    protected Amenity(Location location, String id) {
        super(location, id);
    }

    public static Amenity of(Location location, String id, String amenity) {
        switch (amenity) {
            case ("arts_centre"): return new ArtsCentre(location, id);
            case ("atm"): return new Atm(location, id);
            case ("bank"): return new Bank(location, id);
            case ("bar"): return new Bar(location, id);
            case ("bench"): return new Bench(location, id);
            case ("bicycle_parking"): return new BicycleParking(location, id);
            case ("bicycle_rental"): return new BicycleRental(location, id);
            case ("binoculars"): return new Binoculars(location, id);
            case ("brothel"): return new Brothel(location, id);
            case ("bureau_de_change"): return new BureauDeChange(location, id);
            case ("cafe"): return new Cafe(location, id);
            case ("car_rental"): return new CarRental(location, id);
            case ("car_wash"): return new CarWash(location, id);
            case ("casino"): return new Casino(location, id);
            case ("cinema"): return new Cinema(location, id);
            case ("charging_station"): return new ChargingStation(location, id);
            case ("childcare"): return new Childcare(location, id);
            case ("clinic"): return new Clinic(location, id);
            case ("clock"): return new Clock(location, id);
            case ("college"): return new College(location, id);
            case ("community_centre"): return new CommunityCentre(location, id);
            case ("courthouse"): return new Courthouse(location, id);
            case ("dentist"): return new Dentist(location, id);
            case ("doctors"): return new Doctors(location, id);
            case ("drinking_water"): return new DrinkingWater(location, id);
            case ("fast_food"): return new FastFood(location, id);
            case ("fire_station"): return new FireStation(location, id);
            case ("food_court"): return new FoodCourt(location, id);
            case ("fountain"): return new Fountain(location, id);
            case ("fuel"): return new Fuel(location, id);
            case ("hospital"): return new Hospital(location, id);
            case ("hunting_stand"): return new HuntingStand(location, id);
            case ("ice_cream"): return new IceCream(location, id);
            case ("information"): return new Information(location, id);
            case ("library"): return new Library(location, id);
            case ("luggage_locker"): return new LuggageLocker(location, id);
            case ("marketplace"): return new Marketplace(location, id);
            case ("moped_parking"): return new MopedParking(location, id);
            case ("nightclub"): return new Nightclub(location, id);
            case ("nursing_home"): return new NursingHome(location, id);
            case ("parking"): return new Parking(location, id);
            case ("parking_entrance"): return new ParkingEntrance(location, id);
            case ("parking_space"): return new ParkingSpace(location, id);
            case ("pharmacy"): return new Pharmacy(location, id);
            case ("photo_booth"): return new PhotoBooth(location, id);
            case ("place_of_worship"): return new PlaceOfWorship(location, id);
            case ("police"): return new Police(location, id);
            case ("post_box"): return new PostBox(location, id);
            case ("post_office"): return new PostOffice(location, id);
            case ("prep_school"): return new PrepSchool(location, id);
            case ("pub"): return new Pub(location, id);
            case ("public_bookcase"): return new PublicBookcase(location, id);
            case ("recycling"): return new Recycling(location, id);
            case ("restaurant"): return new Restaurant(location, id);
            case ("resthouse"): return new Resthouse(location, id);
            case ("sanitary_dump_station"): return new SanitaryDumpStation(location, id);
            case ("school"): return new School(location, id);
            case ("shelter"): return new Shelter(location, id);
            case ("shower"): return new Shower(location, id);
            case ("social_facility"): return new SocialFacility(location, id);
            case ("taxi"): return new Taxi(location, id);
            case ("theater"): return new Theater(location, id);
            case ("toilets"): return new Toilets(location, id);
            case ("townhall"): return new Townhall(location, id);
            case ("university"): return new University(location, id);
            case ("vending_machine"): return new VendingMachine(location, id);
            case ("veterinary"): return new Veterinary(location, id);
            case ("waste_basket"): return new WasteBasket(location, id);
            case ("water_point"): return new WaterPoint(location, id);
            default: throw new UnsupportedOperationException("deserialisation of this Amenity is not possible in this version");
        }

    }

    public String getIcon() {
        return Constants.Paths.AMENITY_ICON_PATH + "/" + getClass().getSimpleName();
    }

    public static class ArtsCentre extends Amenity {
        private static final double WEIGHT = 1;
        
        public ArtsCentre(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Atm extends Amenity {
        private static final double WEIGHT = 1;
        
        public Atm(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Bank extends Amenity {
        private static final double WEIGHT = 1;
        
        public Bank(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Bar extends Amenity {
        private static final double WEIGHT = 1;
        
        public Bar(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Bench extends Amenity {
        private static final double WEIGHT = 1;
        
        public Bench(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class BicycleParking extends Amenity {
        private static final double WEIGHT = 1;
        
        public BicycleParking(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class BicycleRental extends Amenity {
        private static final double WEIGHT = 1;
        
        public BicycleRental(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Binoculars extends Amenity {
        private static final double WEIGHT = 1;
        
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
        private static final double WEIGHT = 1;
        
        public BureauDeChange(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Cafe extends Amenity {
        private static final double WEIGHT = 1;
        
        public Cafe(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class CarRental extends Amenity {
        private static final double WEIGHT = 1;
        
        public CarRental(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class CarWash extends Amenity {
        private static final double WEIGHT = 1;
        
        public CarWash(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Casino extends Amenity {
        private static final double WEIGHT = 1;
        
        public Casino(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Cinema extends Amenity {
        private static final double WEIGHT = 1;
        
        public Cinema(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class ChargingStation extends Amenity {
        private static final double WEIGHT = 1;
        
        public ChargingStation(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Childcare extends Amenity {
        private static final double WEIGHT = 1;
        
        public Childcare(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Clinic extends Amenity {
        private static final double WEIGHT = 1;
        
        public Clinic(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Clock extends Amenity {
        private static final double WEIGHT = 1;
        
        public Clock(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class College extends Amenity {
        private static final double WEIGHT = 1;
        
        public College(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class CommunityCentre extends Amenity {
        private static final double WEIGHT = 1;
        
        public CommunityCentre(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Courthouse extends Amenity {
        private static final double WEIGHT = 1;
        
        public Courthouse(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Dentist extends Amenity {
        private static final double WEIGHT = 1;
        
        public Dentist(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Doctors extends Amenity {
        private static final double WEIGHT = 1;
        
        public Doctors(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class DrinkingWater extends Amenity {
        private static final double WEIGHT = 1;
        
        public DrinkingWater(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class FastFood extends Amenity {
        private static final double WEIGHT = 1;
        
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
        private static final double WEIGHT = 1;
        
        public FoodCourt(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Fountain extends Amenity {
        private static final double WEIGHT = 1;
        
        public Fountain(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Fuel extends Amenity {
        private static final double WEIGHT = 1;
        
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
        private static final double WEIGHT = 1;
        
        public HuntingStand(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class IceCream extends Amenity {
        private static final double WEIGHT = 1;
        
        public IceCream(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Information extends Amenity {
        private static final double WEIGHT = 1;
        
        public Information(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Library extends Amenity {
        private static final double WEIGHT = 1;
        
        public Library(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class LuggageLocker extends Amenity {
        private static final double WEIGHT = 1;
        
        public LuggageLocker(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Marketplace extends Amenity {
        private static final double WEIGHT = 1;
        
        public Marketplace(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class MopedParking extends Amenity {
        private static final double WEIGHT = 1;
        
        public MopedParking(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Nightclub extends Amenity {
        private static final double WEIGHT = 1;
        
        public Nightclub(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class NursingHome extends Amenity {
        private static final double WEIGHT = 1;
        
        public NursingHome(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Parking extends Amenity {
        private static final double WEIGHT = 1;
        
        public Parking(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class ParkingEntrance extends Amenity {
        private static final double WEIGHT = 1;
        
        public ParkingEntrance(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class ParkingSpace extends Amenity {
        private static final double WEIGHT = 1;
        
        public ParkingSpace(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Pharmacy extends Amenity {
        private static final double WEIGHT = 1;
        
        public Pharmacy(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class PhotoBooth extends Amenity {
        private static final double WEIGHT = 1;
        
        public PhotoBooth(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class PlaceOfWorship extends Amenity {
        private static final double WEIGHT = 1;
        
        public PlaceOfWorship(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Police extends Amenity {
        private static final double WEIGHT = 1;
        
        public Police(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class PostBox extends Amenity {
        private static final double WEIGHT = 1;
        
        public PostBox(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class PostOffice extends Amenity {
        private static final double WEIGHT = 1;
        
        public PostOffice(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class PrepSchool extends Amenity {
        private static final double WEIGHT = 1;
        
        public PrepSchool(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Pub extends Amenity {
        private static final double WEIGHT = 1;
        
        public Pub(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class PublicBookcase extends Amenity {
        private static final double WEIGHT = 1;
        
        public PublicBookcase(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Recycling extends Amenity {
        private static final double WEIGHT = 1;
        
        public Recycling(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Restaurant extends Amenity {
        private static final double WEIGHT = 1;
        
        public Restaurant(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Resthouse extends Amenity {
        private static final double WEIGHT = 1;
        
        public Resthouse(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class SanitaryDumpStation extends Amenity {
        private static final double WEIGHT = 1;
        
        public SanitaryDumpStation(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class School extends Amenity {
        private static final double WEIGHT = 1;
        
        public School(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Shelter extends Amenity {
        private static final double WEIGHT = 1;
        
        public Shelter(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Shower extends Amenity {
        private static final double WEIGHT = 1;
        
        public Shower(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class SocialFacility extends Amenity {
        private static final double WEIGHT = 1;
        
        public SocialFacility(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Taxi extends Amenity {
        private static final double WEIGHT = 1;
        
        public Taxi(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Theater extends Amenity {
        private static final double WEIGHT = 1;
        
        public Theater(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Toilets extends Amenity {
        private static final double WEIGHT = 1;
        
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
        private static final double WEIGHT = 1;
        
        public University(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class VendingMachine extends Amenity {
        private static final double WEIGHT = 1;
        
        public VendingMachine(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class Veterinary extends Amenity {
        private static final double WEIGHT = 1;
        
        public Veterinary(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class WasteBasket extends Amenity {
        private static final double WEIGHT = 1;
        
        public WasteBasket(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }

    public static class WaterPoint extends Amenity {
        private static final double WEIGHT = 1;
        
        public WaterPoint(Location location, String id) {
            super(location, id);
        }

        @Override
        public double getWeight() {
            return WEIGHT;
        }
    }
}

