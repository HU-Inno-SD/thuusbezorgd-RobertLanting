package nl.hu.inno.order.rabbitmq;

import nl.hu.inno.order.domain.Order;
import nl.hu.inno.order.presentation.receiveDTO.IngredientListDTO;
import nl.hu.inno.order.presentation.sendDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RabbitMQJsonProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;


    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void checkOrder(Order order) {
        LOGGER.info(String.format("Json message sent from order to get ingredients in menu -> %s", order.toString()));
        rabbitTemplate.convertAndSend(exchange, "menu.order.check", new OrderDTO(order), message -> {
            message.getMessageProperties().getHeaders().put("order-id", order.getId());
            return message;
        });
    }

    public void sendIngredients(IngredientListDTO ingredients, UUID orderid) {
        LOGGER.info(String.format("Json message sent from order to send ingredients to stock  -> %s", ingredients.toString()));
        rabbitTemplate.convertAndSend(exchange, "stock.check.ingredients", ingredients, message -> {
            message.getMessageProperties().getHeaders().put("order-id", orderid);
            return message;
        });
    }

    public void checkDish(UUID dishid, UUID orderid) {
        LOGGER.info(String.format("Json message sent from order to check dish in menu -> %s", dishid));
        rabbitTemplate.convertAndSend(exchange, "menu.dish.check", dishid, message -> {
            message.getMessageProperties().getHeaders().put("order-id", orderid);
            return message;
        });
    }
}

