package Utilities;

public class DataNormalize {

    public static String normalizeId(String id) {
        if (id == null) {
            return null;
        }
        return id.replaceAll("\\s+", "").toUpperCase();
    }

    public static String normalizeString(String name) { //user for name or any String have same normalize
        if (name == null) {
            return null;
        }
        return name.trim().replaceAll("\\s+", " ");
    }

    public static String normalizePhone(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("\\s+", "");
    }
 
    public static String normalizeEmail(String input) {
        if (input == null) {
            return null;
        }
        return input.trim();
    }

    public static String normalizeDate(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("\\s+", "");
    }
}
