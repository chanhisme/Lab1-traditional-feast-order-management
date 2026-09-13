/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessObject;

import DataObjects.SetMenuDAO;
import Entities.SetMenu;
import java.util.List;
import java.util.Map;

/**
 *
 * @author chanh
 */
public class SetMenuManagement {

    private final String NUMBER_REGEX = "(?<=\\d)(?=(\\d{3})+$)";
    private final SetMenuDAO setMenuDAO;

    public SetMenuManagement(SetMenuDAO setMenuDAO) {
        this.setMenuDAO = setMenuDAO;
    }

    public static String formatNumber(double price) {
        String number = String.valueOf((long) price);
        return number.replaceAll("(?<=\\d)(?=(\\d{3})+$)", ",");
    }
    public void displayOneMenu(){}
    public void displaySetMenu(Map<String, SetMenu> setMenuMap) {
        System.out.println("-----------------------------------------------------------");
        System.out.println("List of Set Menus for ordering party:");
        System.out.println("-----------------------------------------------------------");
        for (Map.Entry<String, SetMenu> entry : setMenuMap.entrySet()) {
            SetMenu setMenu = entry.getValue();
            System.out.printf("%-15s: %s\n", "Code", setMenu.getId());
            System.out.printf("%-15s: %s\n", "Name", setMenu.getName());
            System.out.printf("%-15s: %s\n", "Price", formatNumber(setMenu.getPrice()));
            System.out.printf("%-15s:\n", "Ingredient");
            List<String> starter = setMenu.getIngredient().get("Khai vị");
            List<String> mainCourse = setMenu.getIngredient().get("Món chính");
            List<String> desert = setMenu.getIngredient().get("Tráng miệng");

            System.out.print("+ Khai vị: ");
            for (int i = 0; i < starter.size(); i++) {
                System.out.print(starter.get(i));
                if (i < starter.size() - 1) {
                    System.out.print("; ");
                }
            }
            System.out.println();
            System.out.print("+ Món chính: ");
            for (int i = 0; i < mainCourse.size(); i++) {
                System.out.print(mainCourse.get(i));
                if (i < mainCourse.size() - 1) {
                    System.out.print("; ");
                }
            }
            System.out.println();

            System.out.print("+ Tráng miệng: ");
            for (int i = 0; i < desert.size(); i++) {
                System.out.print(desert.get(i));
                if (i < desert.size() - 1) {
                    System.out.print("; ");
                }
            }
            System.out.println("\n----------------------------------------------------------");
        }
    }

}
