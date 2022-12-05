package nl.hu.inno.stock.application;

import nl.hu.inno.stock.data.StockRepository;
import nl.hu.inno.stock.domain.Ingredient;
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

    public boolean ingredientInStock(Map<String, Integer> ingredient) {
        return stockRepository.ingredientInStock(ingredient);
    }



}
