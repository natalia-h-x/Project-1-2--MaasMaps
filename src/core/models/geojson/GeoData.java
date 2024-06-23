package core.models.geojson;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import core.Constants;
import core.Constants.Paths;
import core.managers.amenity.AmenityIconManager;
import core.models.Location;
import core.models.geojson.Amenity.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class GeoData {
    private Location location;
    private String id;

    public abstract double getWeight();

    public BufferedImage getIcon() {
        return AmenityIconManager.getIcon(toString());
    }

    public String getIconPath() {
        return Constants.Paths.AMENITY_ICON_PATH + Paths.PATH_DELIMETER + toString() + ".PNG";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().replaceAll("([A-Z])", "_$1").toLowerCase().substring(1);
    }

    public static GeoData of(Location location, String id, String type) {
        switch (type) {
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
            case ("shop"): return new Shop(location, id);
            case ("shower"): return new Shower(location, id);
            case ("social_facility"): return new SocialFacility(location, id);
            case ("taxi"): return new Taxi(location, id);
            case ("theatre"): return new Theatre(location, id);
            case ("toilets"): return new Toilets(location, id);
            case ("tourism"): return new Tourism(location, id);
            case ("townhall"): return new Townhall(location, id);
            case ("university"): return new University(location, id);
            case ("vending_machine"): return new VendingMachine(location, id);
            case ("veterinary"): return new Veterinary(location, id);
            case ("waste_basket"): return new WasteBasket(location, id);
            case ("water_point"): return new WaterPoint(location, id);
            default: throw new UnsupportedOperationException("deserialisation of this geodata \"%s\" is not possible in this version".formatted(type));
        }
    }
}
