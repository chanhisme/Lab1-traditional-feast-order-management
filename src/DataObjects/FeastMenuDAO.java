package DataObjects;

import Entities.FeastMenu;
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
public class FeastMenuDAO {

    private final String PATH = "FeastMenu.txt";
    private final Map<String, List<String>> Ingredient;
    private final Map<String, FeastMenu> feastMap;

    public FeastMenuDAO(Map<String, List<String>> Ingredient, Map<String, FeastMenu> feastMap) {
        this.Ingredient = Ingredient;
        this.feastMap = feastMap;
    }

    public void load() {

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {

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

                if (line.startsWith("[") && line.endsWith("]")) {

                    id = line.substring(1, line.length() - 1);
                    name = null;
                    price = 0;

                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save() {

    }
}
