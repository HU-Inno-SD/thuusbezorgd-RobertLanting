package nl.hu.inno.stock.rabbitmq;

import nl.hu.inno.stock.presentation.recieveDTO.IngredientListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

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

    public void sendConfirmation(UUID orderid) {
        LOGGER.info(String.format("Json message sent from stock confirm order -> %s", orderid));
        rabbitTemplate.convertAndSend(exchange, "order.confirmed", orderid);
    }

    public void sendCancelledOrder(UUID orderId) {
        LOGGER.info(String.format("Json message sent from stock cancelled order -> %s", "not.enough.ingredients"));
        rabbitTemplate.convertAndSend(exchange, "order.cancelled", "not.enough.ingredients", message -> {
            message.getMessageProperties().getHeaders().put("order-id", orderId);
            return message;
        });
    }
}
