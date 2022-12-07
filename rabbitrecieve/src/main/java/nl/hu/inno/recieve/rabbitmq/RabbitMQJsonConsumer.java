package nl.hu.inno.recieve.rabbitmq;

import nl.hu.inno.recieve.TestObject;
import nl.hu.inno.recieve.TestObject2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public Object consumeJsonMessage(TestObject testObject){
        LOGGER.info(String.format("Received JSON message -> %s", testObject.toString()));
        return new TestObject2(testObject.getName());
    }
}
