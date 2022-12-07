package nl.hu.inno.stock.rabbitmq;

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

    @Value("q.stock.check-ingredients")
    private String checkQueue;

    @Value("stock.check.ingredients")
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