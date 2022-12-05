package nl.hu.inno.order.rabbitmq;

import nl.hu.inno.order.application.OrderService;
import nl.hu.inno.order.domain.Order;
import nl.hu.inno.order.presentation.sendDTO.OrderDTO;
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

    private OrderService orderService;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeJsonMessage(OrderDTO orderDTO) {
        LOGGER.info(String.format("Received JSON message -> %s", orderDTO.toString()));
        orderService.cancelOrder(orderDTO.getId());

    }
}
