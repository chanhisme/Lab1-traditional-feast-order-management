package Utilities;

import java.util.Arrays;
import java.util.List;
public class Menu {
    //--------------------------------------------------------------
    public static void printMenu(String str){
        List <String> menuList = Arrays.asList(str.split("\\|"));
        menuList.forEach( menuItem ->{
            if(menuItem.equalsIgnoreCase("select")){
                System.out.print(menuItem);
            }
            else{
                System.out.println(menuItem);
            }
        });
    }
}
