package nl.hu.inno.order.presentation.rabbitDTO;

import java.util.ArrayList;
import java.util.List;

public class IngredientListDTO {

    private List<IngredientDTO> ingredients;

    public IngredientListDTO() {
        this.ingredients = new ArrayList<>();
    }

    public IngredientListDTO(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}
