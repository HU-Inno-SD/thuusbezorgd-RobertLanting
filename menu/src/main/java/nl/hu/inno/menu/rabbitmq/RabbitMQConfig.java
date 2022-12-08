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


    @Value("q.get-menu")
    private String menuQueue;

    @Value("menu.get")
    private String menuKey;

    @Bean
    public Queue menuQueue(){
        return new Queue(menuQueue);
    }

    @Bean
    public Binding checkBinding(){
        return BindingBuilder
                .bind(menuQueue())
                .to(exchange())
                .with(menuKey);
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