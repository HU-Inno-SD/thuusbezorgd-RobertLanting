package nl.hu.inno.stock.rabbitmq;

import nl.hu.inno.stock.application.StockService;
import nl.hu.inno.stock.presentation.recieveDTO.IngredientListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class RabbitMQJsonConsumer {

    private RabbitMQJsonProducer jsonProducer;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    private StockService stockService;

    public RabbitMQJsonConsumer(StockService stockService, RabbitMQJsonProducer jsonProducer) {
        this.stockService = stockService;
        this.jsonProducer = jsonProducer;
    }

    @RabbitListener(queues = {"q.stock.check-ingredients"})
    public void consumeJsonMessage(IngredientListDTO ingredientsListDTO, @Header("order-id") UUID orderid) {
        LOGGER.info(String.format("Received JSON message stock on check ingredients -> %s", ingredientsListDTO.toString()));
        if (stockService.ingredientsInStock(ingredientsListDTO)) {
            jsonProducer.sendConfirmation(orderid);
        } else {
            jsonProducer.sendCancelledOrder(orderid);
        }
    }
}
