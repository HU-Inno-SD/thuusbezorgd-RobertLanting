package nl.hu.inno.order.domain;

import java.time.LocalDateTime;
import java.util.*;

public class Order {

    private UUID id;
    private LocalDateTime date;
    private String adres;
    private List<OrderDish> dishes ;

    private OrderStatus status;

    public Order(String adres) {
        this.date = LocalDateTime.now();
        this.adres = adres;
        this.id = UUID.randomUUID();
        this.dishes = new ArrayList<>();
        this.status = OrderStatus.PENDING;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getAdres() {
        return adres;
    }

    public List<OrderDish> getDishes() {
        return dishes;
    }

    public UUID getId() {
        return id;
    }

    public void addDish(UUID dish, int amount) {
        for (OrderDish d : dishes) {
            if (d.getDish().equals(dish)) {
                d.setAmount(d.getAmount() + amount);
                return;
            }
        }
        dishes.add(new OrderDish(dish, amount));
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

}
