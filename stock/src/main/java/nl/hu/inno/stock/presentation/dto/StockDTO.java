package nl.hu.inno.stock.presentation.dto;

import nl.hu.inno.stock.domain.Ingredient;

import java.util.List;

public class StockDTO {

    public List<Ingredient> ingredients;

    public StockDTO(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
