package org.example.model;

public class Ingredient {
    private int ingredientId;
    private String quantity;
    private String unit;
    private String name;

    public Ingredient() {};

    public Ingredient(String quantity, String unit, String name) {
        this.quantity = quantity;
        this.unit = unit;
        this.name = name;
    }

    //For testing
    public Ingredient(int ingredientId, String quantity, String unit, String name) {
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.unit = unit;
        this.name = name;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
