package nl.hu.inno.menu.presentation.sendDTO;

import java.util.List;
import java.util.Map;

public class IngredientsDTO {

    public Map<String, Integer>[] ingredients;

    public IngredientsDTO(Map<String, Integer>[] ingredients) {
        this.ingredients = ingredients;
    }
}
