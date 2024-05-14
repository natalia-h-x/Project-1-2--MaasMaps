package core;

import java.awt.Color;

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
public class Constants { private Constants() {}
    public static class Paths {
        public static final String MAAS_ZIP_LATLON_PATH = "resources/MassZipLatLon.csv";
        public static final String DATABASE_URL = "jdbc:sqlite:/C://Users//alexa//University//Project 1-2 Repository//project1-2//resources//gtfs//gtfs";
        public static final String DATABASE_FILEPATH = "resources/routing.db";
        public static final String RESOURCES_PLACEHOLDER_MAP_PNG = "resources/visuals/MaastrichtOpenStreetMap.png";
        public static final String BUS_STOP_ICON = "resources/visuals/icons/BusIcon.png";
        public static final String RANDOM_ICON = "resources/visuals/icons/Random.png";
        public static final String A_IMAGE = "resources/visuals/icons/AIcon.png";
        public static final String B_IMAGE = "resources/visuals/icons/BIcon.png";

        private Paths() {}
    }
    public static class UIConstants {
        public static final Color GUI_BACKGROUND_COLOR           = new Color(201, 202 , 217);
        public static final Color GUI_HIGHLIGHT_BACKGROUND_COLOR = new Color(119, 150 , 203);
        public static final Color GUI_HIGHLIGHT_COLOR            = new Color(237, 242 , 244);
        public static final Color GUI_ACCENT_COLOR               = new Color(53 , 80  , 112);
        public static final Color GUI_TITLE_COLOR                = new Color(87 , 100 , 144);
        public static final int GUI_INFO_FONT_SIZE       = 13;
        public static final int GUI_TEXT_FIELD_FONT_SIZE = 15;
        public static final int GUI_TITLE_FONT_SIZE      = 40;
        public static final String GUI_TIME_LABEL_TEXT = "Average time needed for this distance: ";
        public static final String GUI_FONT_FAMILY = "Arial";
        public static final int GUI_BORDER_SIZE = 25;

        private UIConstants() {}
    }
    public static class ANSI {
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String RESET = "\u001B[0m";

        private ANSI() {}
    }

    public static final String BASE_URL = "https://computerscience.dacs.unimaas.nl/get_coordinates";
}
