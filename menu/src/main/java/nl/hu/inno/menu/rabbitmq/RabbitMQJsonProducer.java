package nl.hu.inno.menu.rabbitmq;

import nl.hu.inno.menu.presentation.sendDTO.IngredientListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitMQJsonProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;


    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCancelledOrder(UUID orderid) {
        LOGGER.info(String.format("Json message sent from menu to cancel order in order -> %s", "dish.not.available"));
        rabbitTemplate.convertAndSend(exchange, "order.cancelled", "dish.not.found", message -> {
            message.getMessageProperties().getHeaders().put("order-id", orderid);
            return message;
        });
    }

    public void sendIngredients(IngredientListDTO ingredientListDTO, UUID orderId) {
        LOGGER.info(String.format("Json message sent from menu to give ingredients to order -> %s", ingredientListDTO.toString()));
        rabbitTemplate.convertAndSend(exchange,"order.ingredients", ingredientListDTO, message -> {
            message.getMessageProperties().getHeaders().put("order-id", orderId);
            return message;
        });
    }

    public void sendConfirmDish(UUID dishId, UUID orderId) {
        LOGGER.info(String.format("Json message sent from menu to confirm dish to order -> %s", dishId));
        rabbitTemplate.convertAndSend(exchange, "order.dish.confirmed", dishId, message -> {
            message.getMessageProperties().getHeaders().put("order-id", orderId);
            return message;
        });
    }
}
