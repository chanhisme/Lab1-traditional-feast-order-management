package Utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataValidation {


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

    public static boolean checkStringEmpty(String value) {
        boolean result = true;
        if(value.isEmpty()){
            result = false;
        }
        return result;
    }

    public static boolean CheckDate(String input) {
        try {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate.parse(input, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


}