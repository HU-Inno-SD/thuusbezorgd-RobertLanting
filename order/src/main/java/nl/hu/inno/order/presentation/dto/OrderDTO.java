package nl.hu.inno.order.presentation.dto;

import nl.hu.inno.order.domain.order.Order;
import nl.hu.inno.order.domain.orderdish.OrderDish;
import nl.hu.inno.order.domain.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDTO {

    private UUID orderid;

    private LocalDateTime orderDate;

    private String adres;

    private List<OrderDishDTO> dishes ;

    private OrderStatus status;

    public OrderDTO() {
    }

    public OrderDTO(Order order) {
        this.orderid = order.getOrderid();
        this.orderDate = order.getDate().get();
        this.adres = order.getAdres().get();
        this.dishes = new ArrayList<>();
        order.getDishes().forEach(dish -> dishes.add(new OrderDishDTO(dish)));
        this.status = order.getStatus();
    }

    public UUID getOrderid() {
        return orderid;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getAdres() {
        return adres;
    }

    public List<OrderDishDTO> getDishes() {
        return dishes;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
