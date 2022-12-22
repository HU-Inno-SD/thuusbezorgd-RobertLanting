package nl.hu.inno.menu.presentation.rabbitDTO;
import nl.hu.inno.menu.domain.Dish;

import java.util.List;
import java.util.UUID;

public class DishDTO {

    public UUID id;

    public String name;
    public List<String> ingredients;
    public boolean vegetarian;

    public DishDTO() {
    }

    public DishDTO(Dish dish) {
        this.name = dish.getName();;
        this.ingredients = dish.getIngredients();
        this.vegetarian = dish.isVegetarian();
        this.id = dish.getId();
    }

}
