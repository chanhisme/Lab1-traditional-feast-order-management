package Utilities;

public class DataValidation{


    public static boolean checkStringWithFormat(String id, String pattern){
        boolean isValid = false;
        if(id.matches(pattern)){
            isValid = true;
        }
        return isValid;
    }


}