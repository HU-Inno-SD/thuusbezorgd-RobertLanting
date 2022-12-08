package nl.hu.inno.order.rabbitmq;

import nl.hu.inno.order.application.OrderService;
import nl.hu.inno.order.presentation.rabbitDTO.MenuDTO;
import nl.hu.inno.order.presentation.rabbitDTO.StockDTO;
import nl.hu.inno.order.repo.OrderRepository;
import nl.hu.inno.order.domain.Order;
import nl.hu.inno.order.domain.OrderStatus;
import nl.hu.inno.order.presentation.rabbitDTO.IngredientListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitMQJsonConsumer {

    private RabbitMQJsonProducer jsonProducer;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    private OrderRepository orderRepository;
    private OrderService orderService;

    public RabbitMQJsonConsumer(OrderRepository orderRepository, OrderService orderService, RabbitMQJsonProducer jsonProducer) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.jsonProducer = jsonProducer;
    }

    @RabbitListener(queues = {"q.order-menu-update"})
    public void consumeMenuUpdateMessage(MenuDTO menuDTO) {
        LOGGER.info(String.format("Received JSON message order on menu update q -> %s", menuDTO.toString()));
        orderService.updateMenu(menuDTO);
    }

    @RabbitListener(queues = {"q.order-stock-update"})
    public void consumeStockUpdateMessage(StockDTO stockDTO) {
        LOGGER.info(String.format("Received JSON message order on stock update q -> %s", stockDTO.toString()));
        orderService.updateStock(stockDTO);
    }
}
