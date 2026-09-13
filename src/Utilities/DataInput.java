package Utilities;

import java.time.LocalDate;
import java.util.Scanner;

public class DataInput {
    
    
    public static int getInteger(String message) throws Exception{
        int number = 0;
        System.out.print(message);
        number = getInteger();
        return number;
    }

    public static int getInteger() throws Exception{
        int number = 0;
        String strInput;
        strInput = getString();
        if(!DataValidation.checkStringWithFormat(strInput, "\\d{1,10}")){
            throw new Exception("Data invalid.");
        }
        else{
            number = Integer.parseInt(strInput);
        }
        return number;
    }
    public static String getString(String displayMessage) {
        String strInput;
        System.out.print(displayMessage);
        strInput = getString();
        return strInput;
    }


    public static String getString() {
        String strInput;
        Scanner sc = new Scanner(System.in);
        strInput = sc.nextLine();
        return strInput;
    }
    
    public static int getPositiveIntNumber(String message)throws Exception{
        System.out.print(message);
        int number = getInteger();
        if(!DataValidation.checkPositiveNumber(number)){
            throw new Exception("Number must be > 0");
        }
        return number;
    }


    public static LocalDate getLocalDate(String message) throws Exception{
        System.out.print(message);
        String date = getString();

        if(!DataValidation.isNonEmptyString(date)){
            throw new Exception("String cannot be empty");
        }
        if(!DataValidation.CheckDate(date)){
            throw new Exception("Date invalid.");
        }
        return  LocalDate.parse(date);
    }


    
    
}
