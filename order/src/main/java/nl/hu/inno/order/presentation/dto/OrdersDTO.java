package nl.hu.inno.order.presentation.dto;

import nl.hu.inno.order.domain.Order;

import java.util.List;

public class OrdersDTO {

    private List<Order> orders;

    public OrdersDTO(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
