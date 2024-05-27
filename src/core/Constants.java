package core;

import java.awt.Color;

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
        public static final int POSTAL_CODE_MAX_SEARCH_RADIUS = 50;
        public static final int POSTAL_CODE_MAX_BUS_OPTIONS = 20;
    } 

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Paths {
        public static final String POSTAL_COORDS_API_URL = "https://computerscience.dacs.unimaas.nl/get_coordinates";
        public static final String POSTAL_COORDS_FILE = "resources/MassZipLatLon.csv";
        public static final String DATABASE_PATH = "jdbc:sqlite:resources/gtfs/gtfs";
        public static final String MAP_IMAGE = "resources/visuals/MaastrichtOpenStreetMap.png";
        public static final String BUS_STOP_ICON = "resources/visuals/icons/BusIcon.png";
        public static final String A_IMAGE = "resources/visuals/icons/AIcon.png";
        public static final String B_IMAGE = "resources/visuals/icons/BIcon.png";
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class UIConstants {
        public static final Color GUI_BACKGROUND_COLOR           = new Color(201, 202 , 217);
        public static final Color GUI_HIGHLIGHT_BACKGROUND_COLOR = new Color(119, 150 , 203);
        public static final Color GUI_HIGHLIGHT_COLOR            = new Color(237, 242 , 244);
        public static final Color GUI_ACCENT_COLOR               = new Color(53 , 80  , 112);
        public static final Color GUI_TITLE_COLOR                = new Color(87 , 100 , 144);
        public static final Color GUI_BUTTON_COLOR               = new Color(117, 112, 131);
        public static final int GUI_INFO_FONT_SIZE       = 13;
        public static final int GUI_TEXT_FIELD_FONT_SIZE = 15;
        public static final int GUI_TITLE_FONT_SIZE      = 40;
        public static final String GUI_TIME_LABEL_TEXT = "Average duration: ";
        public static final String GUI_FONT_FAMILY = "Arial";
        public static final int GUI_BORDER_SIZE = 25;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ANSI {
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String RESET = "\u001B[0m";
    }
}
