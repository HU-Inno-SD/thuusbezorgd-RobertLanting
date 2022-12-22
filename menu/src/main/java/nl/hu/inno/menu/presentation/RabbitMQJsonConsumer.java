package nl.hu.inno.menu.presentation;

import nl.hu.inno.menu.presentation.rabbitDTO.MenuDTO;
import nl.hu.inno.menu.repo.RabbitMQJsonProducer;
import nl.hu.inno.menu.repo.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {

    private RabbitMQJsonProducer jsonProducer;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    private MenuRepository menuRepository;

    public RabbitMQJsonConsumer(MenuRepository menuRepository, RabbitMQJsonProducer jsonProducer) {
        this.menuRepository = menuRepository;
        this.jsonProducer = jsonProducer;
    }

    @RabbitListener(queues = {"q.get-menu"})
    public void consumeGetMenuMessage() {
        LOGGER.info("Received JSON message menu on get menu q");
        jsonProducer.updateMenu(new MenuDTO(menuRepository.findAll()));
    }
}

