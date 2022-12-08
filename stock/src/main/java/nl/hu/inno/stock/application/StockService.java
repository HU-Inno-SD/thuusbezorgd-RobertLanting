package nl.hu.inno.stock.application;

import nl.hu.inno.stock.presentation.dto.IngredientDTO;
import nl.hu.inno.stock.presentation.dto.IngredientListDTO;
import nl.hu.inno.stock.presentation.dto.StockDTO;
import nl.hu.inno.stock.rabbitmq.RabbitMQJsonProducer;
import nl.hu.inno.stock.repo.StockRepository;
import nl.hu.inno.stock.domain.Ingredient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;

    private final RabbitMQJsonProducer jsonProducer;

    public StockService(StockRepository stockRepository, RabbitMQJsonProducer jsonProducer) {
        this.stockRepository = stockRepository;
        this.jsonProducer = jsonProducer;
    }

    public void addIngredient(Ingredient ingredient) {
        stockRepository.save(ingredient);
        jsonProducer.updateStock(new StockDTO(stockRepository.findAll()));
    }

    public void deleteIngredient(String name) {
        stockRepository.deleteById(name);
        jsonProducer.updateStock(new StockDTO(stockRepository.findAll()));
    }

    public List<Ingredient> getIngredients() {
        return stockRepository.findAll();
    }

    public void takeIngredients(IngredientListDTO ingredientsListDTO) {
        for (IngredientDTO i : ingredientsListDTO.getIngredients()) {
            Ingredient ingredient = stockRepository.findById(i.getId()).get();
            ingredient.setAmount(ingredient.getAmount() - i.getAmount());
            stockRepository.save(ingredient);
        }
    }
}
