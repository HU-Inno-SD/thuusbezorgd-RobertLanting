package nl.hu.inno.order.rabbitmq;

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

    @Value("q.order-ingredients")
    private String ingredientsQueue;
    @Value("order.ingredients")
    private String ingredientsKey;

    @Bean
    public Queue ingredientQueue(){
        return new Queue(ingredientsQueue);
    }
    @Bean
    public Binding ingredientBinding(){
        return BindingBuilder
                .bind(ingredientQueue())
                .to(exchange())
                .with(ingredientsKey);
    }

    @Value("q.order-cancelled")
    private String cancelledQueue;
    @Value("order.cancelled")
    private String cancelledKey;

    @Bean
    public Queue cancelledQueue(){
        return new Queue(cancelledQueue);
    }
    @Bean
    public Binding cancelledBinding(){
        return BindingBuilder
                .bind(cancelledQueue())
                .to(exchange())
                .with(cancelledKey);
    }

    @Value("q.order-confirmed")
    private String confirmedQueue;

    @Value("order.confirmed")
    private String confirmedKey;

    @Bean
    public Queue confirmedQueue(){
        return new Queue(confirmedQueue);
    }

    @Bean
    public Binding confirmedBinding(){
        return BindingBuilder
                .bind(confirmedQueue())
                .to(exchange())
                .with(confirmedKey);
    }

    @Value("q.order-dish-confirmed")
    private String dishConfirmedQueue;

    @Value("order.dish.confirmed")
    private String dishConfirmedKey;

    @Bean
    public Queue dishConfirmedQueue(){
        return new Queue(dishConfirmedQueue);
    }

    @Bean
    public Binding dishConfirmedBinding(){
        return BindingBuilder
                .bind(dishConfirmedQueue())
                .to(exchange())
                .with(dishConfirmedKey);
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