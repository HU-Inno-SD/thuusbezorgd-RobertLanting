package nl.hu.inno.menu.rabbitmq;

import nl.hu.inno.menu.application.MenuService;
import nl.hu.inno.menu.presentation.recieveDTO.OrderDTO;
import nl.hu.inno.menu.presentation.sendDTO.IngredientsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class RabbitMQJsonConsumer {

    private RabbitMQJsonProducer jsonProducer;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    private MenuService menuService;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeJsonMessage(OrderDTO orderDTO) {
        LOGGER.info(String.format("Received JSON message -> %s", orderDTO.toString()));

        for (Map<UUID, Integer> id : orderDTO.getDishes())  {
            if (menuService.checkIfDishExists(id)) {
                jsonProducer.send(id, "order");
                return;
            };
        }
        jsonProducer.send(new IngredientsDTO(menuService.getAmountOfIngredientsFromDishes(orderDTO.getDishes())), "stock");
    }
}

