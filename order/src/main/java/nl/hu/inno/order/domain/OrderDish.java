package nl.hu.inno.order.domain;


import java.util.UUID;

public class OrderDish {

    private UUID dish;
    private int amount;

    public OrderDish(UUID dish, int amount) {
        this.dish = dish;
        this.amount = amount;
    }

    public UUID getDish() {
        return dish;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
