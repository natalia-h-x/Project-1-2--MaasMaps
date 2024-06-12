package core.managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manager for common JSON manipulations and JSON data extraction.
 *
 * @author Sian Lodde
 * @author Kimon Navridis
 */
public class SerializationManager {
    private SerializationManager() {}
    
    /**
     * Extract value from JSON string
     *
     * @param json
     * @param key
     * @return
     */
    public static double extractJSON(String json, String key) {
        String keyWithQuotes = "\"" + key + "\":";
        int startIndex = json.indexOf(keyWithQuotes) + keyWithQuotes.length();

        if (startIndex == -1) {
            throw new IllegalArgumentException("key not found");
        }

        int endIndex = json.indexOf(",", startIndex);

        if (endIndex == -1) { // If it's the last element, there might not be a comma
            endIndex = json.indexOf("}", startIndex);
        }

        if (endIndex == -1) {
            throw new IllegalArgumentException("Proper JSON closure not found");
        }

        String value = json.substring(startIndex, endIndex).trim();

        // remove porential quotes
        value = value.replaceAll("(^\")|(\"$)", "");

        return Double.parseDouble(value);
    }

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
