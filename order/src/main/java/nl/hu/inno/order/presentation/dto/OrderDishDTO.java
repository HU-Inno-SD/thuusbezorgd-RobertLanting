package nl.hu.inno.order.presentation.dto;

import nl.hu.inno.order.domain.orderdish.OrderDish;

import java.util.UUID;

public class OrderDishDTO {

    private UUID dish;
    private int amount;

    public OrderDishDTO() {
    }

    public OrderDishDTO(OrderDish orderdish) {
        this.dish = orderdish.getDish();
        this.amount = orderdish.getAmount().get();
    }

    public UUID getDish() {
        return dish;
    }

    public int getAmount() {
        return amount;
    }
}
