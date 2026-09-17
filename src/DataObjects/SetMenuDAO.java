package DataObjects;

import Entities.SetMenu;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

        private final String PATH = "feastMenu.txt";
        private final Map<String, SetMenu> setMenu;

    //--------------------------------------------------------------
        public SetMenuDAO(Map<String, SetMenu> setMenu) {
            this.setMenu = setMenu;
        }
    //--------------------------------------------------------------
        public List <SetMenu> getAllSetMenu(){
            return new ArrayList<>(setMenu.values());
        }
    //--------------------------------------------------------------
        public void load() {
            try ( BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
                String line;
                reader.readLine();

                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    String[] data = line.split(",", 4);

                    String id = data[0].trim();
                    String name = data[1].trim();
                    double price = Double.parseDouble(data[2].trim());
                    String ingredientsData = data[3].trim();
                      
                    if (ingredientsData.startsWith("\"") && ingredientsData.endsWith("\"")) {
                        ingredientsData = ingredientsData.substring(1, ingredientsData.length() - 1);
                    }
                    
                    Map<String, List<String>> ingredients = new LinkedHashMap<>();
                    String[] categories = ingredientsData.split("#");
                    
                    for (String category : categories) {
                        category = category.trim();

                        if (category.startsWith("+ Khai vị:")) {
                            String dishes = category.substring("+ Khai vị:".length()).trim();
                            
                            ingredients.put("Khai vị", Arrays.asList(dishes.split(";")));
                        } else if (category.startsWith("+ Món chính:")) {
                            String dishes = category.substring("+ Món chính:".length()).trim();
                            ingredients.put("Món chính", Arrays.asList(dishes.split(";")));
                        } else if (category.startsWith("+ Tráng miệng:")) {
                            String dishes = category.substring("+ Tráng miệng:".length()).trim();
                            ingredients.put("Tráng miệng", Arrays.asList(dishes.split(";")));
                        }
                    }

                    SetMenu menu = new SetMenu(id, name, price, ingredients);
                    setMenu.put(id, menu);
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }

    //--------------------------------------------------------------
        public void save() {

        }

    //--------------------------------------------------------------
        public SetMenu findSetMenu(String id){
            return setMenu.get(id);
        }
    }

