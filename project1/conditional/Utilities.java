package project1.conditional;

public class Utilities {

    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String sanitizeFieldName(String input)
    {
        return input.replace("\"", "");
    }
}
