package nl.hu.inno.order.presentation.receiveDTO;

import java.util.List;

public class DishListDTO {

    public List<DishDTO> dishes;

    public DishListDTO() {
    }

    public DishListDTO(List<DishDTO> dishes) {
        this.dishes = dishes;
    }

    public List<DishDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishDTO> dishes) {
        this.dishes = dishes;
    }
}
