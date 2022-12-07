package nl.hu.inno.send.rabbitmq;

import nl.hu.inno.send.TestObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(TestObject testObject) {
        LOGGER.info(String.format("Json message sent -> %s", testObject.toString()));
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, testObject);
    }

    public Object sendobject(Object object, Class<?> objectreturn, String routingKey) {
        LOGGER.info(String.format("Json message sent -> %s", object.toString()));
        return rabbitTemplate.convertSendAndReceiveAsType(exchange, routingKey,object, ParameterizedTypeReference.forType(objectreturn));
    }

}
