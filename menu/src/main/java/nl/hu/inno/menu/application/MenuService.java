package nl.hu.inno.menu.application;

import nl.hu.inno.menu.data.MenuRepository;
import nl.hu.inno.menu.domain.Dish;
import nl.hu.inno.menu.rabbitmq.RabbitMQJsonProducer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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


    public boolean checkIfDishExists(Map<UUID, Integer> id) {
        return menuRepository.existsById(id.keySet().iterator().next());
    }

    public Map<String, Integer>[] getAmountOfIngredientsFromDishes(List<Map<UUID, Integer>> dishes) {
        Map<String, Integer>[] ingredients = new Map[]{};
        for (Map<UUID, Integer> d : dishes) {
            Dish dish = menuRepository.findById(d.keySet().iterator().next()).orElse(null);
            for (String i : dish.getIngredients()) {
                i
            }
        }
    }
}
