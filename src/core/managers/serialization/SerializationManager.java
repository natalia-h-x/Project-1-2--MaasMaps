package core.managers.serialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Serialization manager to provide helper methods for deserializing and serializing JSON files.
 *
 * @author Sian Lodde
 */
public class SerializationManager {
    private static final String STRING_QUOTATION = "'";

    private SerializationManager() {}

    public static int smallestUsable(int a, int b) {
        if (a == -1 && b == -1) return -1;
        if (a == -1) return b;
        if (b == -1) return a;

        return Math.min(a, b);
    }

    public static String hideStringsAndBrackets(String data, List<String> strings) {
        int quotation = -1;

        while ((quotation = smallestUsable(
                    data.indexOf("\"", quotation),
                    data.indexOf("(" , quotation)
                    )) != -1) {
            int endingQuotation = data.charAt(quotation) != '(' ? data.indexOf("\"", quotation + 1) : data.indexOf(")", quotation + 1);

            strings.add((data.charAt(quotation) == '(' ? "(" : "") + new String(data.substring(quotation + 1, endingQuotation)) + (data.charAt(endingQuotation) == ')' ? ")" : ""));
            data = new String(
                       data.substring(0, quotation)) +
                       STRING_QUOTATION + (strings.size() - 1) + STRING_QUOTATION +
                       new String(data.substring(endingQuotation + 1, data.length())
                   );
        }

        return data;
    }

    public static String hideElements(String data, List<String> elements, char elementChar, String[] separators, String... binarySeparators) {
        int startChar = -1;

        while ((startChar = smallestUsableSeparator(data, add(separators, splitArray(binarySeparators, 2, 0)), startChar)) != -1) {
            int endChar = smallestUsableSeparator(data, add(separators, splitArray(binarySeparators, 2, 1)), startChar + 1);

            if (endChar == -1) break;

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
        }

        return data;
    }

    private static int smallestUsableSeparator(String data, String[] separators, int startChar) {
        List<Integer> indexes = new ArrayList<>();

        for (String separator : separators) {
            indexes.add(data.indexOf(separator, startChar));
        }

        return smallestUsable(indexes.toArray(Integer[]::new));
    }

    public static int smallestUsable(Integer... nums) {
        if (nums.length < 2) return nums[0];

        for (int i = 1; i < nums.length; i++) {
            nums[i] = smallestUsable(nums[i - 1], nums[i]);
        }

        return nums[nums.length - 1];
    }

    private static String[] splitArray(String[] array, int parts, int part) {
        int delta = array.length / parts;

        String[] newArray = new String[delta];

        for (int i = 0; i < delta; i++) {
            newArray[i] = array[i + (delta * part)];
        }

        return newArray;
    }

    /**
     * Add an element to an array with thesame type.
     *
     * @param <T>
     * @param array1
     * @param element
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] add(T[] array1, T... array2) {
        try {
            int lastIndex = array1.length;
            array1 = Arrays.copyOf(array1, array1.length + array2.length);

            for (int i = lastIndex; i < array1.length; i++) {
                array1[i] = array2[i - lastIndex];
            }
        }
        catch (Exception e) {
            System.out.println(String.format("Array1 of type %s is not appliccable for an array2 of type %s", array2.getClass().toString(), array1.getClass().getTypeName()));
            e.printStackTrace();
        }

        return array1;
    }

    /**
     * !Trims everything of the provided string but a value between the first occasions of two consecutive elements
     *
     * @param string
     * @param elements
     * @return trimmed string
     */
    public static String trimString(String string, String elements) {
        if (elements.length() != 2) throw new IllegalArgumentException("Please provide two elements in the elements String for trimming!");
        return string.replaceFirst("\\Q" + elements.charAt(0) + "\\E.*\\Q" + elements.charAt(1) + "\\E", "");
    }

    public static String trimStringInclusive(String string, String elements) {
        return elements.charAt(0) + trimString(string, elements) + elements.charAt(1);
    }

    public static String digString(String string, String elements) {
        if (elements.length() != 2) throw new IllegalArgumentException("Please provide two elements in the elements String for trimming!");
        if (!string.contains("" + elements.charAt(0)) && !string.contains("" + elements.charAt(1))) return "";
        return string.replaceFirst("^.*\\Q" + elements.charAt(0) + "\\E(.*\\Q" + elements.charAt(1) + "\\E)", "$1").replaceFirst("\\Q" + elements.charAt(1) + "\\E.*", "");
    }

    public static String digStringInclusive(String string, String elements) {
        return elements.charAt(0) + digString(string, elements) + elements.charAt(1);
    }
}
