package core;

import java.awt.Color;

import javax.swing.ImageIcon;

import core.models.gtfs.Time;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * This class represents contant paths and general constants.
 *
 * @author Natalia Hadjisoteriou
 * @author Arda Ayyildizbayraktar
 * @author Sheena Gallagher
 * @author Sian Lodde
 * @author Alexandra Plishkin Islamgulova
 * @author Meriç Uruş
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Map {
        public static final int POSTAL_CODE_MAX_SEARCH_RADIUS = 200;
        public static final int WALKING_MAX_TIME = 30;
        public static final int POSTAL_CODE_MAX_BUS_OPTIONS = 100;
        public static final Time DEFAULT_DEPARTURE_TIME = Time.of(7, 0, 0);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Paths {
        public static final String AMENITY_ICON_PATH = "resources/visuals/icons/amenity";
        public static final String POSTAL_COORDS_API_URL = "https://project12.ashish.nl/get_coordinates";
        public static final String POSTAL_COORDS_FILE = "resources/MassZipLatLon.csv";
        public static final String ACCESSIBILITY_FILE = "resources/PostalCodeAccessibility.csv";
        public static final String DATABASE_PATH = "jdbc:sqlite:resources\\gtfs\\gtfs";
        public static final String MAP_IMAGE = "resources/visuals/MaastrichtOpenStreetMap.png";
        public static final String BUS_STOP_ICON = "resources/visuals/icons/BusIcon.jpg";
        public static final String A_IMAGE = "resources/visuals/icons/AIcon.png";
        public static final String B_IMAGE = "resources/visuals/icons/BIcon.png";
        public static final String PATH_DELIMETER = "/";
        public static final String GEOJSON = "resources/geoJSON/%s.geojson";

        public static final ImageIcon buttonMenu1 = new ImageIcon("resources/visuals/icons/display1.png");
        public static final ImageIcon buttonMenu2 = new ImageIcon("resources/visuals/icons/display2.png");
        public static final ImageIcon buttonMenu3 = new ImageIcon("resources/visuals/icons/display3.png");
        public static final ImageIcon menuIcon = new ImageIcon("resources/visuals/icons/menuIcon.png");
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class UIConstants {
        public static final Color GUI_BACKGROUND_COLOR           = new Color(201, 202 , 217);
        public static final Color GUI_HIGHLIGHT_BACKGROUND_COLOR = new Color(119, 150 , 203);
        public static final Color GUI_HIGHLIGHT_COLOR            = new Color(237, 242 , 244);
        public static final Color GUI_ACCENT_COLOR               = new Color(53 , 80  , 112);
        public static final Color GUI_TITLE_COLOR                = new Color(87 , 100 , 144);
        public static final Color GUI_BUTTON_COLOR               = new Color(117, 112 , 131);
        public static final Color GUI_LEGEND_COLOR               = new Color(225, 238, 254);
        public static final Color GUI_LEGENDITEM_COLOR           = new Color(225, 238, 254);
        public static final int GUI_INFO_FONT_SIZE       = 13;
        public static final int GUI_TEXT_FIELD_FONT_SIZE = 15;
        public static final int GUI_TITLE_FONT_SIZE      = 40;
        public static final String GUI_TIME_LABEL_TEXT = "Average duration: ";
        public static final String GUI_FONT_FAMILY = "Arial";
        public static final int GUI_BORDER_SIZE = 25;
        public static final int BUTTON_SIZE =65;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class BusColors {
        public static final Color BUS_30 = Color.GREEN;
        public static final Color BUS_6 = Color.CYAN;
        public static final Color BUS_1 = Color.decode("#80217D");
        public static final Color BUS_10 = Color.RED;
        public static final Color BUS_7 = Color.YELLOW;
        public static final Color BUS_4 = Color.ORANGE;
        public static final Color BUS_2 = Color.MAGENTA;
        public static final Color BUS_350 = Color.BLUE;
        public static final Color BUS_15 = Color.decode("#31AE9B");
    } //CHANGE ALL BUS COLORS ACCORDINGLY

    public static final class AccessibiltyColours {
        public static final Color[] ACC_GRADIENT = new Color[] {Color.RED, Color.YELLOW, Color.GREEN};
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ANSI {
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String RESET = "\u001B[0m";
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Settings {
        public static final boolean USE_CLIPBOARD = false;
    }
}
