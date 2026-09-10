/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessObject;

import DataObjects.FeastMenuDAO;
import Entities.FeastMenu;
import java.util.List;
import java.util.Map;

/**
 *
 * @author chanh
 */
public class FeastMenuManagement {

    private final String NUMBER_REGEX = "(?<=\\d)(?=(\\d{3})+$)";
    private final FeastMenuDAO feastMenuDAO;

    public FeastMenuManagement(FeastMenuDAO feastMenuDAO) {
        this.feastMenuDAO = feastMenuDAO;
    }

    public String formatNumber(double price) {
        String number = String.valueOf((long) price);
        return number.replaceAll("(?<=\\d)(?=(\\d{3})+$)", ",");
    }

    public void displayFeastMenu(Map<String, FeastMenu> feastMap) {
        System.out.println("-----------------------------------------------------------");
        System.out.println("List of Set Menus for ordering party:");
        System.out.println("-----------------------------------------------------------");
        for (Map.Entry<String, FeastMenu> entry : feastMap.entrySet()) {
            FeastMenu feastMenu = entry.getValue();
            System.out.printf("%-15s: %s\n", "Code", feastMenu.getId());
            System.out.printf("%-15s: %s\n", "Name", feastMenu.getName());
            System.out.printf("%-15s: %s\n", "Price", formatNumber(feastMenu.getPrice()));
            System.out.printf("%-15s:\n", "Ingredient");
            List<String> starter = feastMenu.getIngredient().get("Khai vị");
            List<String> mainCourse = feastMenu.getIngredient().get("Món chính");
            List<String> desert = feastMenu.getIngredient().get("Tráng miệng");

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
