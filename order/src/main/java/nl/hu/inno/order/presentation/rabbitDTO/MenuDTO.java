package nl.hu.inno.order.presentation.rabbitDTO;

import java.util.List;

public class MenuDTO {

    private List<DishDTO> menu;

    public MenuDTO() {
    }

    public MenuDTO(List<DishDTO> menu) {
        this.menu = menu;
    }

    public List<DishDTO> getMenu() {
        return menu;
    }
}
