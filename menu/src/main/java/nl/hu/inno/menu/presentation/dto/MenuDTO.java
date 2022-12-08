package nl.hu.inno.menu.presentation.dto;

import nl.hu.inno.menu.domain.Dish;

import java.util.ArrayList;
import java.util.List;

public class MenuDTO {

    private List<DishDTO> menu;

    public MenuDTO(List<Dish> menu) {
        this.menu = new ArrayList<>();
        for (Dish dish : menu) {
            this.menu.add(new DishDTO(dish));
        }
    }

    public List<DishDTO> getMenu() {
        return menu;
    }
}
