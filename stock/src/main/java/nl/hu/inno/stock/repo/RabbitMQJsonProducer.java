package nl.hu.inno.stock.repo;

import nl.hu.inno.stock.presentation.rabbitDTO.StockDTO;
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

    public void updateStock(StockDTO stockDTO) {
        LOGGER.info(String.format("Json message sent from stock to update stock -> %s", stockDTO.toString()));
        rabbitTemplate.convertAndSend(exchange, "order.stock.update", stockDTO);
    }
}
