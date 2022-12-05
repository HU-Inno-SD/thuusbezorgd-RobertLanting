package nl.hu.inno.menu.presentation.controller;

import nl.hu.inno.menu.application.MenuService;
import nl.hu.inno.menu.domain.Dish;
import nl.hu.inno.menu.presentation.dto.DishDTO;
import nl.hu.inno.menu.presentation.dto.MenuDTO;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public void addDish(@RequestBody DishDTO dish) {
        service.addDish(new Dish(dish.name, dish.ingredients, dish.vegetarian));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDish(@PathVariable String id) {
        service.deleteDish(UUID.fromString(id));
    }

}
