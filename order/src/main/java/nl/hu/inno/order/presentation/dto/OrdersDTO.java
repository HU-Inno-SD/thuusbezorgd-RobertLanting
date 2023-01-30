package nl.hu.inno.order.presentation.dto;

import java.util.List;

public class OrdersDTO {

    private List<OrderDTO> orders;

    public OrdersDTO(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }
}
