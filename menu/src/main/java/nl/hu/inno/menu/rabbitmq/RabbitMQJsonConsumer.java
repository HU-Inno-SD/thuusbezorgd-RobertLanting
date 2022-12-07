package nl.hu.inno.menu.rabbitmq;

import nl.hu.inno.menu.application.MenuService;
import nl.hu.inno.menu.presentation.recieveDTO.OrderDTO;
import org.bson.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitMQJsonConsumer {

    private RabbitMQJsonProducer jsonProducer;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    private MenuService menuService;

    public RabbitMQJsonConsumer(MenuService menuService, RabbitMQJsonProducer jsonProducer) {
        this.menuService = menuService;
        this.jsonProducer = jsonProducer;
    }

    @RabbitListener(queues = {"q.menu-order-check"})
    public void consumeOrderMessage(OrderDTO orderDTO, @Header("order-id") UUID orderId) {
        LOGGER.info(String.format("Received JSON message menu on order check q -> %s", orderDTO.toString()));
        jsonProducer.sendIngredients(menuService.getTotalIngredientsFromDishes(orderDTO), orderId);
    }

    @RabbitListener(queues = {"q.menu-dish-check"})
    public void consumeDishMessage(UUID dishId, @Header("order-id") UUID orderId) {
        LOGGER.info(String.format("Received JSON message menu on dish check q -> %s", dishId));
        if (menuService.checkIfDishExists(dishId)) {
            jsonProducer.sendConfirmDish(dishId, orderId);
        } else {
            jsonProducer.sendCancelledOrder(orderId);
        }
    }
}

