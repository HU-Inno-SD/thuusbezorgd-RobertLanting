package nl.hu.inno.menu.presentation;

import nl.hu.inno.menu.application.MenuService;
import nl.hu.inno.menu.domain.Dish;
import nl.hu.inno.menu.domain.exception.DishNotFoundException;
import nl.hu.inno.menu.presentation.dto.DishDTO;
import nl.hu.inno.menu.presentation.dto.MenuDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping("")
    public MenuDTO menu() {
        return new MenuDTO(service.getMenu());
    }

    @PostMapping("")
    public void addDish(@RequestBody DishDTO dish) {
        service.addDish(new Dish(dish.name, dish.ingredients, dish.vegetarian));
    }

    @DeleteMapping("/{id}")
    public void deleteDish(@PathVariable String id) {
        try {
            service.deleteDish(UUID.fromString(id));
        } catch (DishNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Dish not found with id: " + id);
        }
    }

}
