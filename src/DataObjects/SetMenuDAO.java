package DataObjects;

import Entities.SetMenu;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author chanh
 */
public class SetMenuDAO {

    private final String PATH = "FeastMenu.txt";
    private final Map<String, SetMenu> setMenuMap;

    public SetMenuDAO(Map<String, SetMenu> setMenuMap) {
        this.setMenuMap = setMenuMap;
    }

    public Map<String, SetMenu> getSetMenuMap() {
        return setMenuMap;
    }
    
    public void load() {

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))){
            String line;
            String id = null;
            String name = null;
            double price = 0;
            Map<String, List<String>> ingredients = null;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }
                if (line.startsWith("-------------------")) {
                    if (id != null && ingredients != null) {
                        SetMenu setMenu = new SetMenu(id, name, price, ingredients);
                        setMenuMap.put(setMenu.getId(), setMenu);
                    }
                    id = null;
                    name = null;
                    price = 0;
                    ingredients = null;
                    continue;
                }

                String[] parts = line.split("=", 2);
                if (parts.length < 2) {
                    continue;
                }
                String type = parts[0].trim();
                String value = parts[1].trim();

                if (ingredients == null) {
                    ingredients = new LinkedHashMap<>();
                }

                switch (type) {
                    case "id":
                        id = value;
                        break;
                    case "Name":
                        name = value;
                        break;
                    case "Price":
                        try {
                            price = Double.parseDouble(value);
                        } catch (NumberFormatException e) {
                            price = 0;
                        }
                        break;
                    case "Khai vị":
                        ingredients.put("Khai vị", Arrays.asList(value.split(";\\s*")));
                        break;
                    case "Món chính":
                        ingredients.put("Món chính", Arrays.asList(value.split(";\\s*")));
                        break;
                    case "Tráng miệng":
                        ingredients.put("Tráng miệng", Arrays.asList(value.split(";\\s*")));
                        break;
                }
            }

            if (id != null && ingredients != null) {
                SetMenu setMenu = new SetMenu(id, name, price, ingredients);
                setMenuMap.put(setMenu.getId(), setMenu);
            }

        } catch (IOException e) {
            System.out.println("the “FeastMenu.txt” does not exist " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void save() {

    }
}
