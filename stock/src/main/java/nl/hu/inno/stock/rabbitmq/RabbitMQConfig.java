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

    @Value("q.get-stock")
    private String stockQueue;

    @Value("stock.get")
    private String stockKey;

    @Bean
    public Queue stockQueue(){
        return new Queue(stockQueue);
    }

    @Bean
    public Binding stockBinding(){
        return BindingBuilder
                .bind(stockQueue())
                .to(exchange())
                .with(stockKey);
    }

    @Value("q.stock-take-ingredients")
    private String ingredientsQueue;

    @Value("stock.take.ingredients")
    private String ingredientsKey;

    @Bean
    public Queue ingredientsQueue(){
        return new Queue(ingredientsQueue);
    }

    @Bean
    public Binding ingredientBinding(){
        return BindingBuilder
                .bind(ingredientsQueue())
                .to(exchange())
                .with(ingredientsKey);
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