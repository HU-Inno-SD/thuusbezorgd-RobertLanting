package nl.hu.inno.stock.presentation.rabbitDTO;

import nl.hu.inno.stock.domain.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class StockDTO {

    public List<IngredientDTO> stock;

    public StockDTO(List<Ingredient> ingredients) {
        this.stock = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            this.stock.add(new IngredientDTO(ingredient));
        }
    }

    public List<IngredientDTO> getStock() {
        return stock;
    }
}
