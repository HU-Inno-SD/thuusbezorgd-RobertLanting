package nl.hu.inno.stock.presentation.dto;

import nl.hu.inno.stock.domain.Ingredient;

public class IngredientDTO {

    public String id;

    public int amount;

    public IngredientDTO() {
    }

    public IngredientDTO(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.amount = ingredient.getAmount();
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }
}
