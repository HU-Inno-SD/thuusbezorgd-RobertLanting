package nl.hu.inno.menu.domain;

import java.util.List;
import java.util.UUID;

public class Dish {

    private UUID id;
    private String name;
    private List<String> ingredients;
    private boolean vegetarian;

    public Dish() {
    }

    public Dish(String name, List<String> ingredients, boolean vegetarian) {
        this.name = name;
        this.ingredients = ingredients;
        this.vegetarian = vegetarian;
        this.id = UUID.randomUUID();
    }
    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public UUID getId() {
        return id;
    }
}
