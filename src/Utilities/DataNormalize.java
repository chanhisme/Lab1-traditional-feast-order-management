package Utilities;

public class DataNormalize {

    public static String normalizeId(String id) {
        return id.replaceAll("\\s+", "").toUpperCase();
    }

    public static String normalizeString(String name) { //user for name or any String have same normalize
        return name.trim().replaceAll("\\s+", " ");
    }

    public static String normalizePhone(String input) {
        return input.replaceAll("\\s+", "");
    }
 
    public static String normalizeEmail(String input) {
        return input.trim();
    }

    public static String normalizeDate(String input) {
        return input.replaceAll("\\s+", "");
    }
}
