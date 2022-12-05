package nl.hu.inno.stock.data;

import nl.hu.inno.stock.domain.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Map;

public interface StockRepository extends MongoRepository<Ingredient, String> {
    boolean ingredientInStock(Map<String, Integer> ingredient);
}

