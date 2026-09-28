package DataObjects;

import Entities.SetMenu;
import Utilities.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SetMenuDAO {

    private static final String PATH = Constants.FILE_FEAST_MENU;
    private final Map<String, SetMenu> setMenus;

    public SetMenuDAO(Map<String, SetMenu> setMenus) {
        this.setMenus = setMenus;
    }

    public List<SetMenu> getAllSetMenu() {
        return new ArrayList<>(setMenus.values());
    }

    public void add(SetMenu setMenu) {
        setMenus.put(setMenu.getId(), setMenu);
    }

    public void load() {
        try ( BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(PATH), StandardCharsets.UTF_8))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",", 4);

                String id = data[0].trim();
                String name = data[1].trim();
                long price = Long.parseLong(data[2].trim());
                String ingredientsData = data[3].trim();

                if (ingredientsData.startsWith("\"") && ingredientsData.endsWith("\"")) {
                    ingredientsData = ingredientsData.substring(1, ingredientsData.length() - 1);
                }

                Map<String, List<String>> ingredients = new LinkedHashMap<>();
                String[] categories = ingredientsData.split("#");

                for (String category : categories) {
                    category = category.trim();

                    if (category.startsWith(Constants.PREFIX_APPETIZER)) {
                        String dishes = category.substring(Constants.PREFIX_APPETIZER.length()).trim();
                        ingredients.put(Constants.CATEGORY_APPETIZER, parseDishes(dishes));

                    } else if (category.startsWith(Constants.PREFIX_MAIN)) {
                        String dishes = category.substring(Constants.PREFIX_MAIN.length()).trim();
                        ingredients.put(Constants.CATEGORY_MAIN, parseDishes(dishes));

                    } else if (category.startsWith(Constants.PREFIX_DESSERT)) {
                        String dishes = category.substring(Constants.PREFIX_DESSERT.length()).trim();
                        ingredients.put(Constants.CATEGORY_DESSERT, parseDishes(dishes));

                    }
                }

                SetMenu menu = new SetMenu(id, name, price, ingredients);
                setMenus.put(id, menu);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save() {
        try ( BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(PATH), StandardCharsets.UTF_8))) {
            writer.write("Code,Name,Price,Ingredients");
            writer.newLine();
            for (SetMenu menu : setMenus.values()) {
                String ingredients = Constants.PREFIX_APPETIZER + " "
                        + String.join("; ",
                                menu.getIngredients().getOrDefault(Constants.CATEGORY_APPETIZER, new ArrayList<>()))
                        + "#" + Constants.PREFIX_MAIN + " "
                        + String.join("; ",
                                menu.getIngredients().getOrDefault(Constants.CATEGORY_MAIN, new ArrayList<>()))
                        + "#" + Constants.PREFIX_DESSERT + " "
                        + String.join("; ",
                                menu.getIngredients().getOrDefault(Constants.CATEGORY_DESSERT, new ArrayList<>()));
                writer.write(menu.getId() + "," + menu.getName() + "," + menu.getPrice() + ",\"" + ingredients
                        + "\"");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<String> parseDishes(String dishes) {
        List<String> dishList = new ArrayList<>();
        for (String d : dishes.split(";")) {
            String t = d.trim();
            if (!t.isEmpty()) {
                dishList.add(t);
            }
        }
        return dishList;
    }

    public SetMenu findSetMenu(String id) {
        return setMenus.get(id);
    }

  
    public ArrayList<SetMenu> findMenuInRange(long min, long max) {
        ArrayList<SetMenu> res = new ArrayList<>();
        for (SetMenu setMenu : getAllSetMenu()) {
            if (setMenu.getPrice() <= max && setMenu.getPrice() >= min) {
                res.add(setMenu);
            }
        }
        return res;
    }
}
