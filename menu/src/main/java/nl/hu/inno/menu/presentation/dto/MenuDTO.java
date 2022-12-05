package nl.hu.inno.menu.presentation.dto;

import nl.hu.inno.menu.domain.Dish;

import java.util.List;

public class MenuDTO {

    private List<Dish> menu;

    public MenuDTO(List<Dish> menu) {
        this.menu = menu;
    }

    public List<Dish> getMenu() {
        return menu;
    }
}
