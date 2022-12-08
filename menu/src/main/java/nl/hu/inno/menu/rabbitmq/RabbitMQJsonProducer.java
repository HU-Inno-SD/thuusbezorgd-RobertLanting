package nl.hu.inno.menu.rabbitmq;

import nl.hu.inno.menu.presentation.dto.MenuDTO;
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

    public void updateMenu(MenuDTO menuDTO) {
        LOGGER.info(String.format("Json message sent from menu to update menu -> %s", menuDTO.toString()));
        rabbitTemplate.convertAndSend(exchange, "order.menu.update", menuDTO);
    }
}
