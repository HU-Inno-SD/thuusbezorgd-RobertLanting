package nl.hu.inno.stock.rabbitmq;

import nl.hu.inno.stock.application.StockService;
import nl.hu.inno.stock.presentation.dto.IngredientListDTO;
import nl.hu.inno.stock.presentation.dto.StockDTO;
import nl.hu.inno.stock.repo.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {

    private final RabbitMQJsonProducer jsonProducer;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    private final StockService stockService;

    private final StockRepository stockRepository;

    public RabbitMQJsonConsumer(StockService stockService, StockRepository stockRepository, RabbitMQJsonProducer jsonProducer) {
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.jsonProducer = jsonProducer;
    }

    @RabbitListener(queues = {"q.stock-take-ingredients"})
    public void consumeTakeIngredientsMessage(IngredientListDTO ingredientsListDTO) {
        LOGGER.info(String.format("Received JSON message stock on check ingredients -> %s", ingredientsListDTO.toString()));
        stockService.takeIngredients(ingredientsListDTO);
    }

    @RabbitListener(queues = {"q.get-stock"})
    public void consumeGetStockMessage() {
        LOGGER.info("Received JSON message stock on get stock q");
        jsonProducer.updateStock(new StockDTO(stockRepository.findAll()));
    }
}
