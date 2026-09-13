package Utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataValidation {
     public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static boolean checkStringWithFormat(String id, String pattern) {
        boolean isValid = false;
        if (id.matches(pattern)) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean checkPositiveNumber(int number) {
        boolean isValid = false;
        if (number > 0) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isNonEmptyString(String value) {
        boolean result = true;
        if (value.isEmpty()) {
            result = false;
        }
        return result;
    }

    public static boolean CheckDate(String input) {
        try {
            LocalDate.parse(input, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isFutureDate(LocalDate eventDate) {
        boolean isValid = false;
        if (eventDate.isAfter(LocalDate.now())) {
            isValid = true;
        }
        return isValid;
    }

}