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

    private static final String NUMBER_REGEX = "(?<=\\d)(?=(\\d{3})+$)";
    private final SetMenuDAO setMenuDAO;

    public SetMenuManagement(SetMenuDAO setMenuDAO) {
        this.setMenuDAO = setMenuDAO;
    }

    public static String formatNumber(double price) {
        String number = String.valueOf((long) price);
        return number.replaceAll(NUMBER_REGEX, ",");
    }

    public void displayOneMenu() {
    }

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

            displayDish("+ Khai vị: ", setMenu.getIngredient().get("Khai vị"));
            displayDish("+ Món chính: ", setMenu.getIngredient().get("Món chính"));
            displayDish("+ Tráng miệng: ", setMenu.getIngredient().get("Tráng miệng"));

            System.out.println("\n----------------------------------------------------------");
        }
    }

    public static void displayDish(String title, List<String> dishes) {
        if (dishes == null || dishes.isEmpty()) {
            return;
        }
        System.out.println(title);
        for (int i = 0; i < dishes.size(); i++) {
            System.out.print(dishes.get(i));
            if (i < dishes.size() - 1) {
                System.out.print("; ");
            }
        }
        System.out.println();
    }

}
