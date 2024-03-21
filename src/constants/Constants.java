package constants;

import java.awt.Color;

public class Constants { private Constants() {}
    public static class Paths { private Paths() {}
        public static final String RESOURCES_PLACEHOLDER_MAP_PNG = "resources/MaastrichtOpenStreetMap.png";
        public static final String MAAS_ZIP_LATLON_PATH = "resources/MassZipLatLon.csv";
        public static final String BUS_STOP_ICON = "resources/BusIcon.png";
        public static final String RANDOM_ICON = "resources/Random.png";
        public static final String A_IMAGE = "resources/AIcon.png";
        public static final String B_IMAGE = "resources/BIcon.png";
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

    public static final String BASE_URL = "https://computerscience.dacs.unimaas.nl/get_coordinates";
}
