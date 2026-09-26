package Utilities;

import java.time.LocalDate;
import java.util.Scanner;

public class DataInput {

    private static final Scanner scanner = new Scanner(System.in, "UTF-8");

    public static int getInteger(String message) throws Exception {
        int number = 0;
        System.out.print(message);
        number = getInteger();
        return number;
    }

    public static int getInteger() throws Exception {
        int number = 0;
        String strInput;
        strInput = getString();
        if (!DataValidation.checkStringWithFormat(strInput, "\\d{1,10}")) {
            throw new Exception("Data invalid.");
        } else {
            try {
                number = Integer.parseInt(strInput);
            } catch (NumberFormatException e) {
                throw new Exception("Data is much larger than allowed");
            }

        }
        return number;
    }

    public static String getString(String displayMessage) {

        System.out.print(displayMessage);
        String strInput = getString();
        return strInput;
    }

    public static String getString() {

        String strInput = scanner.nextLine();

        return strInput.trim();
    }

    public static int getPositiveIntNumber(String message) throws Exception {
        System.out.print(message);
        int number = getInteger();
        if (!DataValidation.checkPositiveNumber(number)) {
            throw new Exception("Number must be > 0");
        }
        return number;
    }

    public static LocalDate getLocalDate(String message) throws Exception {
        System.out.print(message);
        String date = DataNormalize.normalizeDate(getString());

        if (!DataValidation.isNonEmptyString(date)) {
            throw new Exception("String cannot be empty");
        }
        if (!DataValidation.checkDate(date)) {
            throw new Exception("Date invalid.");
        }
        return LocalDate.parse(date, DataValidation.DATE_FORMATTER);
    }
    
    public static int getIntegerMaxMin(int max, int min, String message) throws Exception{
        int number = getInteger(message);
        if(!DataValidation.isIntergerMaxMin(min, max, number)){
            throw new Exception("the number is invalid");
        }
        return number;
    }

}
