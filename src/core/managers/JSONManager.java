package core.managers;

/**
 * Manager for common JSON manipulations and JSON data extraction.
 *
 * @author Sian Lodde
 * @author Kimon Navridis
 */
public class JSONManager {
    /**
     * Extract value from JSON string
     *
     * @param json
     * @param key
     * @return
     */
    public static double extractValue(String json, String key) {
        String keyWithQuotes = "\"" + key + "\":";
        int startIndex = json.indexOf(keyWithQuotes) + keyWithQuotes.length();

        if (startIndex == -1) {
            // key not found
            return Double.NaN; // TODO (maybe) throw error
        }

        int endIndex = json.indexOf(",", startIndex);

        if (endIndex == -1) { // If it's the last element, there might not be a comma
            endIndex = json.indexOf("}", startIndex);
        }

        if (endIndex == -1) {
            // Proper JSON closure not found
            return Double.NaN; // TODO (maybe) throw error
        }

        String value = json.substring(startIndex, endIndex).trim();

        // remove porential quotes
        value = value.replaceAll("^\"|\"$", "");
        return Double.parseDouble(value);
    }

}
