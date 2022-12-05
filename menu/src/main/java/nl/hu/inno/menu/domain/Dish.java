package nl.hu.inno.menu.domain;

import java.util.List;
import java.util.UUID;

public class Dish {

    private UUID id;
    private String name;
    private List<String> ingredients;
    private boolean vegetarian;

    public Dish(String name, List<String> ingredients, boolean vegetarian) {
        this.name = name;
        this.ingredients = ingredients;
        this.vegetarian = vegetarian;
        this.id = UUID.randomUUID();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
