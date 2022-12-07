package nl.hu.inno.order.presentation.sendDTO;

import nl.hu.inno.order.domain.Order;
import nl.hu.inno.order.domain.OrderDish;
import nl.hu.inno.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderDTO {

    private UUID id;
    private String date;
    private String adres;
    private List<OrderDishDTO> dishes ;

    private String status;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.date = order.getDate().toString();
        this.adres = order.getAdres();
        this.dishes = new ArrayList<>();
        for (OrderDish orderDish : order.getDishes()) {
            this.dishes.add(new OrderDishDTO(orderDish.getDish(), orderDish.getAmount()));
        }
        this.status = String.valueOf(order.getStatus());
    }

    public String getDate() {
        return date;
    }

    public String getAdres() {
        return adres;
    }

    public List<OrderDishDTO> getDishes() {
        return dishes;
    }

    public UUID getId() {
        return id;
    }

    public Object getStatus() {
        return status;
    }
}