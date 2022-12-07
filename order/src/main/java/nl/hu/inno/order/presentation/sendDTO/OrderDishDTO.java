package nl.hu.inno.order.presentation.sendDTO;

import java.util.UUID;

public class OrderDishDTO {

    private UUID dish;
    private int amount;

    public OrderDishDTO(UUID dish, int amount) {
        this.dish = dish;
        this.amount = amount;
    }

    public UUID getDish() {
        return dish;
    }

    public void setDish(UUID dish) {
        this.dish = dish;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
