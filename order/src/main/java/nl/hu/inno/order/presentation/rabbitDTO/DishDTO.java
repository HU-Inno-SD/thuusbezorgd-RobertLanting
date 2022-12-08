package nl.hu.inno.order.presentation.rabbitDTO;

import java.util.List;
import java.util.UUID;

public class DishDTO {


    private UUID id;

    public String name;
    public List<String> ingredients;

    public boolean vegetarian;

    public List<String> getIngredients() {
        return ingredients;
    }

    public UUID getId() {
        return id;
    }
}
