package core.managers.serialization;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manager for common JSON manipulations and JSON data extraction.
 *
 * @author Sian Lodde
 * @author Kimon Navridis
 */
public class XMLSerializationManager {
    private XMLSerializationManager() {}
    
    public static String getFirstTag(String xml) {
        Pattern r = Pattern.compile("<(.*?)>");
        Matcher m = r.matcher(xml);
    
        if (m.find())
            return m.group(1);

        return "";
    }

    public static String removeFirstTag(String xml) {
        return xml.replaceFirst("<(.*?)>", "");
    }

    public static String removeTags(String xml) {
        return xml.replaceAll("<.*>", "");
    }
}
