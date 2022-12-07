package nl.hu.inno.stock.application;

import nl.hu.inno.stock.data.StockRepository;
import nl.hu.inno.stock.domain.Ingredient;
import nl.hu.inno.stock.presentation.recieveDTO.IngredientDTO;
import nl.hu.inno.stock.presentation.recieveDTO.IngredientListDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void addIngredient(Ingredient ingredient) {
        stockRepository.save(ingredient);
    }

    public void deleteIngredient(String name) {
        stockRepository.deleteById(name);
    }

    public Ingredient getIngredient(String id) {
        return stockRepository.findById(id).orElse(null);
    }

    public List<Ingredient> getIngredients() {
        return stockRepository.findAll();
    }

    public void addIngredients(List<Ingredient> ingredients) {
        stockRepository.saveAll(ingredients);
    }

    public boolean ingredientsInStock(IngredientListDTO ingredientsListDTO) {
        for (IngredientDTO i : ingredientsListDTO.getIngredients()) {
            Ingredient ingredient = stockRepository.findById(i.getName()).orElse(null);
            if (ingredient == null) {
                return false;
            } else {
                if (ingredient.getAmount() < i.getAmount()) {
                    return false;
                }
            }
        }
        return true;
    }
}
