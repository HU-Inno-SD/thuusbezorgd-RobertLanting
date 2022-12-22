package nl.hu.inno.order.repo;

import nl.hu.inno.order.presentation.rabbitDTO.IngredientListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;


    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendIngredients(IngredientListDTO ingredients) {
        LOGGER.info(String.format("Json message sent from order to send ingredients to stock  -> %s", ingredients.toString()));
        rabbitTemplate.convertAndSend(exchange, "stock.take.ingredients", ingredients);
    }

    public void getMenu() {
        LOGGER.info(String.format("Json message sent from order to get menu from menu"));
        rabbitTemplate.convertAndSend(exchange,"menu.get", "");
    }

    public void getStock() {
        LOGGER.info(String.format("Json message sent from order to get stock from stock"));
        rabbitTemplate.convertAndSend(exchange,"stock.get", "");
    }
}

