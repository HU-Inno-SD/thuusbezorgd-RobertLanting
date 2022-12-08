package nl.hu.inno.menu.application;

import nl.hu.inno.menu.presentation.dto.MenuDTO;
import nl.hu.inno.menu.repo.MenuRepository;
import nl.hu.inno.menu.domain.Dish;
import nl.hu.inno.menu.rabbitmq.RabbitMQJsonProducer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MenuService {

    private final RabbitMQJsonProducer jsonProducer;

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository, RabbitMQJsonProducer jsonProducer) {
        this.menuRepository = menuRepository;
        this.jsonProducer = jsonProducer;
    }

    public List<Dish> getMenu() {
        return menuRepository.findAll();
    }

    public void addDish(Dish dish) {
        menuRepository.save(dish);
        jsonProducer.updateMenu(new MenuDTO(menuRepository.findAll()));
    }

    public void deleteDish(UUID id) {
        menuRepository.deleteById(id);
        jsonProducer.updateMenu(new MenuDTO(menuRepository.findAll()));
    }
}

