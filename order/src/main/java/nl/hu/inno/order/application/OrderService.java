package nl.hu.inno.order.application;

import nl.hu.inno.order.data.OrderRepository;
import nl.hu.inno.order.domain.Order;
import nl.hu.inno.order.domain.OrderStatus;
import nl.hu.inno.order.presentation.receiveDTO.DishListDTO;
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

    public void deleteOrder(String id) {
        orderRepository.deleteById(UUID.fromString(id));
    }

    public void addDish(UUID orderid, UUID dishid) {
        Order order = orderRepository.findById(orderid).orElse(null);
        if (order != null) {
            if (order.getDishes().stream().anyMatch(d -> d.getDish().equals(dishid))) {
                order.getDishes().stream().filter(d -> d.getDish().equals(dishid)).forEach(d -> d.setAmount(d.getAmount() + 1));
            } else {
                order.addDish(dishid,1);
            }
            orderRepository.save(order);
        } else {
            System.out.println("Order not found");
        }
    }

    public void dishExists(String dishid, String orderid) {
        Order order = orderRepository.findById(UUID.fromString(orderid)).orElse(null);
        if (order != null) {
            jsonProducer.checkDish(UUID.fromString(dishid), UUID.fromString(orderid));
        } else {
            System.out.println("Order not found");
        }
    }



    public void cancelOrder(UUID orderid) {
        Order order = orderRepository.findById(orderid).orElse(null);
        if (order != null) {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        }
    }

    public void checkDishes(String id) {
        Order order = orderRepository.findById(UUID.fromString(id)).orElse(null);
        if (order != null) {
            jsonProducer.checkOrder(order);
        } else {
            System.out.println("Order not found");
        }
    }
}




