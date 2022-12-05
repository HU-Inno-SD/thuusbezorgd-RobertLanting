package nl.hu.inno.order.application;

import nl.hu.inno.order.data.OrderRepository;
import nl.hu.inno.order.domain.Order;
import nl.hu.inno.order.domain.OrderStatus;
import nl.hu.inno.order.presentation.sendDTO.OrderDTO;
import nl.hu.inno.order.rabbitmq.RabbitMQJsonProducer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class OrderService {

    private RabbitMQJsonProducer jsonProducer;

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, RabbitMQJsonProducer jsonProducer) {
        this.orderRepository = orderRepository;
        this.jsonProducer = jsonProducer;
    }

    public void createOrder(String adres) {
        orderRepository.save(new Order(adres));
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(UUID id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    public void addDish(String id, String dishid, int amount) {
        Order order = orderRepository.findById(UUID.fromString(id)).orElse(null);
        if (order != null) {
            order.addDish(UUID.fromString(dishid), amount);
            orderRepository.save(order);
        }
    }

    public void cancelOrder(UUID id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        }
    }

    public void placeOrder(String id) {
        jsonProducer.send(new OrderDTO(getOrder(UUID.fromString(id))), "menu");
    }
}




