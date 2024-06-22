package core.managers.serialization;

import java.util.ArrayList;
import java.util.List;

import core.models.serialization.Serializable;


/**
 * Serialization manager to deserialize and serialize JSON files.
 *
 * @author Sian Lodde
 */
public class JSONSerializationManager {
    private JSONSerializationManager() {}

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

    public static Serializable deSterializeJSON(String data) {
        Serializable currentSterializable = new Serializable();

        // Prune all strings and save them into an array
        List<String> strings = new ArrayList<>();
        List<String> elements = new ArrayList<>();
        int startChar = -1;

        // Using this instead of stripping leading and trailing to prevent a loss of possibly important spaces in front or after the JSON data
        int quotation = -1;

        while ((quotation = smallestUsable(
                    data.indexOf("\"", quotation),
                    data.indexOf("(" , quotation)
                    )) != -1) {
            int endingQuotation = data.charAt(quotation) != '(' ? data.indexOf("\"", quotation + 1) : data.indexOf(")", quotation + 1);

            strings.add((data.charAt(quotation) == '(' ? "(" : "") + new String(data.substring(quotation + 1, endingQuotation)) + (data.charAt(endingQuotation) == ')' ? ")" : ""));
            data = new String(
                       data.substring(0, quotation)) +
                       "'" + (strings.size() - 1) + "'" +
                       new String(data.substring(endingQuotation + 1, data.length())
                   );
        }

        // Prune all spaces and unimportant duplicate characters to make it easier for ourselves
        // Commas and colons are solely used for seperating elements
        data = data.replace("\n" , "");
        data = data.replace("\r" , "");
        data = data.replace(" " ,  "");
        data = data.replace("'", "\"");
        data = data.replace(":{", "{");
        data = data.replace(":[", "[");
        data = data.replace("},", "}");
        data = data.replace("],", "]");

        // In keeping track of the elements character e is used
        char elementChar = 'e';

        while (
        (startChar = smallestUsable(
                         data.indexOf(",", startChar),
                         data.indexOf(":", startChar),
                         data.indexOf("{", startChar),
                         data.indexOf("[", startChar),
                         data.indexOf("}", startChar),
                         data.indexOf("]", startChar)
                     )
            ) != -1) {
            int endChar = smallestUsable(
                              data.indexOf(",", startChar + 1),
                              data.indexOf(":", startChar + 1),
                              data.indexOf("}", startChar + 1),
                              data.indexOf("]", startChar + 1),
                              data.indexOf("{", startChar + 1),
                              data.indexOf("[", startChar + 1)
                          );

            if (endChar == -1) break;

            while (startChar + 1 < data.length() && (data.charAt(startChar + 1) == '{' || data.charAt(startChar + 1) == '[' || data.charAt(startChar + 1) == '}' || data.charAt(startChar + 1) == ']')) {
                startChar++;
            }

            // Add element to elements array
            elements.add(new String(data.substring(startChar + 1, endChar)));

            int lengthOffset = data.length();

            // Replace the element by the identifier of the element
            data = new String(
                       data.substring(0, startChar + 1)) +
                       elementChar +
                       new String(data.substring(endChar, data.length())
                   );

            lengthOffset -= data.length();

            // Start again from the endingCharacter index
            startChar = endChar - lengthOffset;

            while (startChar + 1 < data.length() && (data.charAt(startChar + 1) == '{' || data.charAt(startChar + 1) == '[' || data.charAt(startChar + 1) == '}' || data.charAt(startChar + 1) == ']')) {
                startChar++;
            }
        }

        // Iterate over the characters and add elements to the current sterializable
        // Keeping track of the elements in natural order
        int i = 0;
        char[] characters = data.toCharArray();

        for (char c : characters) {
            switch (c) {
                case '{': currentSterializable = currentSterializable.openObject (); break;
                case '}': currentSterializable = currentSterializable.closeObject(); break;
                case '[': currentSterializable = currentSterializable.openArray  (); break;
                case ']': currentSterializable = currentSterializable.closeArray (); break;
                case ':': break;
                case ',': break;
                case 'e': currentSterializable.add(getString(elements.get(i++), strings)); break;
                default: throw new IllegalArgumentException("Unknown character \"" + c + "\"!");
            }
        }

        return currentSterializable;
    }

    private static String getString(String element, List<String> strings) {
        if (!element.contains("\"")){
            // Possibly cast element according to it's type
            return element;
        }

        return SerializationManager.trimString(element, "\"\"") + strings.get(Integer.valueOf(SerializationManager.digString(element, "\"\"")));
    }

    public static int smallestUsable(int a, int b) {
        if (a == -1 && b == -1) return -1;
        if (a == -1) return b;
        if (b == -1) return a;

        return Math.min(a, b);
    }

    public static int smallestUsable(int... nums) {
        if (nums.length < 2) return nums[0];

        for (int i = 1; i < nums.length; i++) {
            nums[i] = smallestUsable(nums[i - 1], nums[i]);
        }

        return nums[nums.length - 1];
    }
}
