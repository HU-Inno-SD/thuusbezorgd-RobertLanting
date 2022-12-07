package nl.hu.inno.menu.application;

import nl.hu.inno.menu.data.MenuRepository;
import nl.hu.inno.menu.domain.Dish;
import nl.hu.inno.menu.presentation.recieveDTO.OrderDTO;
import nl.hu.inno.menu.presentation.recieveDTO.OrderDishDTO;
import nl.hu.inno.menu.presentation.sendDTO.IngredientDTO;
import nl.hu.inno.menu.presentation.sendDTO.IngredientListDTO;
import nl.hu.inno.menu.rabbitmq.RabbitMQJsonProducer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class MenuService {

    private RabbitMQJsonProducer jsonProducer;

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Dish> getMenu() {
        return menuRepository.findAll();
    }

    public Dish addDish(Dish dish) {
        return menuRepository.save(dish);
    }

    public void deleteDish(UUID id) {
        menuRepository.deleteById(id);
    }

    public IngredientListDTO getTotalIngredientsFromDishes(OrderDTO orderDTO) {
        IngredientListDTO ingredients = new IngredientListDTO();
        for (OrderDishDTO orderDishDTO : orderDTO.getDishes()) {
            Dish dish = menuRepository.findById(orderDishDTO.getDish()).get();
            for (String ingredient : dish.getIngredients()) {
                if (ingredients.getIngredients().stream().anyMatch(ingredientDTO -> ingredientDTO.getName().equals(ingredient))) {
                    ingredients.getIngredients().stream()
                            .filter(ingredientDTO -> ingredientDTO.getName().equals(ingredient))
                            .forEach(ingredientDTO -> ingredientDTO.setAmount(ingredientDTO.getAmount() + orderDishDTO.getAmount()));
                } else {
                    ingredients.getIngredients().add(new IngredientDTO(ingredient, orderDishDTO.getAmount()));
                }
            }
        }
        return ingredients;
    }

    public boolean checkIfDishesExist(OrderDTO orderDTO) {
        for (OrderDishDTO id : orderDTO.getDishes()) {
            if (!menuRepository.existsById(id.getDish())) return false;
        }
        return true;
    }

    public boolean checkIfDishExists(UUID dishId) {
        return menuRepository.existsById(dishId);
    }
}

