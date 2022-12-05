package nl.hu.inno.order.rabbitmq;

import nl.hu.inno.order.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
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

    public void send(Object object, String routingKey) {
        LOGGER.info(String.format("Json message sent -> %s", object.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, object);
    }

}
