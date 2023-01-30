package nl.hu.inno.order.domain.orderdish;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class OrderDish {

    private UUID dish;
    private Amount amount;

    public OrderDish() {
    }

    public OrderDish(UUID dish, int amount) {
        this.dish = dish;
        this.amount = new Amount(amount);
    }

    public UUID getDish() {
        return dish;
    }

    public Amount getAmount() {
        return amount;
    }

}
