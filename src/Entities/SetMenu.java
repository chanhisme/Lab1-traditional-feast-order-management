package Entities;

import java.util.List;
import java.util.Map;

public class SetMenu {
    private String id;
    private String name;
    private double price;

    private Map<String, List<String>> Ingredient;

    //--------------------------------------------------------------
    public SetMenu(String id, String name, double price, Map<String, List<String>> Ingredient) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.Ingredient = Ingredient;
    }

    //--------------------------------------------------------------
    public String getId() {
        return id;
    }

    //--------------------------------------------------------------
    public void setId(String id) {
        this.id = id;
    }

    //--------------------------------------------------------------
    public String getName() {
        return name;
    }

    //--------------------------------------------------------------
    public void setName(String name) {
        this.name = name;
    }

    //--------------------------------------------------------------
    public double getPrice() {
        return price;
    }

    //--------------------------------------------------------------
    public void setPrice(double price) {
        this.price = price;
    }

    //--------------------------------------------------------------
    public Map<String, List<String>> getIngredient() {
        return Ingredient;
    }

    //--------------------------------------------------------------
    public void setIngredient(Map<String, List<String>> Ingredient) {
        this.Ingredient = Ingredient;
    }

    //--------------------------------------------------------------
    @Override
    public String toString() {
        return String.format("Code: %s | Name: %s | Price: %.0f", id, name, price);
    }

}
