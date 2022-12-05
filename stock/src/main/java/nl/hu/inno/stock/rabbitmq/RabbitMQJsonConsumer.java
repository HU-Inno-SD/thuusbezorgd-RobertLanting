package nl.hu.inno.stock.rabbitmq;

import nl.hu.inno.stock.application.StockService;
import nl.hu.inno.stock.presentation.recieveDTO.IngredientsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    private StockService stockService;

    @RabbitListener(queues = {"${rabbitmq.queue.json.stock}"})
    public void consumeJsonMessage(IngredientsDTO ingredientsDTO) {
        LOGGER.info(String.format("Received JSON message -> %s", ingredientsDTO.toString()));

        for (Map<String, Integer> ingredient : ingredientsDTO.getIngredients()) {
            stockService.ingredientInStock(ingredient);
        }
    }
}
