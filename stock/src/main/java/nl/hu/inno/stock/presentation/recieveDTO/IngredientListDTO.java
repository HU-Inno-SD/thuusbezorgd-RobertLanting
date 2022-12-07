package nl.hu.inno.stock.presentation.recieveDTO;

import java.util.List;

public class IngredientListDTO {

    private List<IngredientDTO> ingredients;

    public IngredientListDTO() {
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
