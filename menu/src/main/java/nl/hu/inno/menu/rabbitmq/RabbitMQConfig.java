package nl.hu.inno.menu.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }


    @Value("q.menu-order-check")
    private String checkQueue;

    @Value("menu.order.check")
    private String checkKey;

    @Bean
    public Queue checkQueue(){
        return new Queue(checkQueue);
    }

    @Bean
    public Binding checkBinding(){
        return BindingBuilder
                .bind(checkQueue())
                .to(exchange())
                .with(checkKey);
    }

    @Value("q.menu-dish-check")
    private String dishCheckQueue;

    @Value("menu.dish.check")
    private String dishCheckKey;

    @Bean
    public Queue dishCheckQueue(){
        return new Queue(dishCheckQueue);
    }

    @Bean
    public Binding dishCheckBinding(){
        return BindingBuilder
                .bind(dishCheckQueue())
                .to(exchange())
                .with(dishCheckKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}