package nl.hu.inno.order.rabbitmq;

import nl.hu.inno.order.application.OrderService;
import nl.hu.inno.order.domain.Order;
import nl.hu.inno.order.presentation.receiveDTO.IngredientListDTO;
import nl.hu.inno.order.presentation.sendDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class RabbitMQJsonConsumer {

    private RabbitMQJsonProducer jsonProducer;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    private OrderService orderService;

    public RabbitMQJsonConsumer(OrderService orderService, RabbitMQJsonProducer jsonProducer) {
        this.orderService = orderService;
        this.jsonProducer = jsonProducer;
    }

    @RabbitListener(queues = {"q.order-cancelled"})
    public void consumeCancelMessage(String message, @Header("order-id") UUID orderId) {
        LOGGER.info(String.format("Received JSON message order on cancelled q -> %s", message));
        if (message.equals("dish.not.found")) {
            System.out.println("Dish not found");
        }
        if (message.equals("not.enough.ingredients")) {
            System.out.println("Not enough ingredients");
            orderService.cancelOrder(orderId);
        }
    }

    @RabbitListener(queues = {"q.order-confirmed"})
    public void consumeConfirmationMessage(String orderid) {
        LOGGER.info(String.format("Received JSON message order on confirmed q -> %s", orderid));
        System.out.println("Order confirmed");
    }

    @RabbitListener(queues = {"q.order-ingredients"})
    public void consumeIngredientsMessage(IngredientListDTO ingredients, @Header("order-id") UUID orderid) {
        LOGGER.info(String.format("Received JSON message order on ingredients q -> %s", ingredients.toString()));
        jsonProducer.sendIngredients(ingredients, orderid);
    }

    @RabbitListener(queues = {"q.order-dish-confirmed"})
    public void consumeDishConfirmationMessage(UUID dishId, @Header("order-id") UUID orderId) {
        LOGGER.info(String.format("Received JSON message order on dish confirmed q-> %s", dishId));
        orderService.addDish(orderId, dishId);
    }

}
