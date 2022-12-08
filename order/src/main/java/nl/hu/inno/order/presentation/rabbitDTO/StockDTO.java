package nl.hu.inno.order.presentation.rabbitDTO;

import java.util.ArrayList;
import java.util.List;

public class StockDTO {

    public List<IngredientDTO> stock;

    public StockDTO() {
    }

    public StockDTO(List<IngredientDTO> ingredients) {
        this.stock = ingredients;
    }

    public List<IngredientDTO> getStock() {
        return stock;
    }
}
