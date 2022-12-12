package nl.hu.inno.stock.presentation.controller;


import nl.hu.inno.stock.application.StockService;
import nl.hu.inno.stock.domain.Ingredient;
import nl.hu.inno.stock.domain.exception.IngredientNotFoundException;
import nl.hu.inno.stock.presentation.dto.IngredientDTO;
import nl.hu.inno.stock.presentation.dto.StockDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("")
    public StockDTO getStock() {
        return new StockDTO(stockService.getIngredients());
    }

    @PostMapping("")
    public void addIngredient(@RequestBody IngredientDTO ingredient) {
        stockService.addIngredient(new Ingredient(ingredient.id, ingredient.amount));
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable String id) {
        try {
            stockService.deleteIngredient(id);
        } catch (IngredientNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Ingredient not found with id: " + id);
        }
    }

}
