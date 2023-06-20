package com.example.stock.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class PizzaMQConfig {

    public static final String PIZZA_QUEUE = "pizza-queue";

    public static final String STOCK_QUEUE = "stock-queue";

    public static final String PIZZA_EXCHANGE = "pizza-exchange";

    public static final String PIZZA_ROUTING_KEY = "pizza-routing";

    public static final String STOCK_ROUTING_KEY = "stock-routing";

    @Bean
    public Queue pizzaQueue(){
        return new Queue(PIZZA_QUEUE);
    }

    @Bean
    public Queue stockQueue() {
        return new Queue(STOCK_QUEUE);
    }

    @Bean
    public TopicExchange pizzaExchange() {
        return new TopicExchange(PIZZA_EXCHANGE);
    }

    @Bean
    public Binding pizzaBinding(Queue pizzaQueue, TopicExchange pizzaExchange) {
        return BindingBuilder.bind(pizzaQueue).to(pizzaExchange).with(PIZZA_ROUTING_KEY);
    }

    @Bean
    public Binding stockBinding(Queue stockQueue, TopicExchange pizzaExchange) {
        return BindingBuilder.bind(stockQueue).to(pizzaExchange).with(STOCK_ROUTING_KEY);
    }

    @Bean
    public MessageConverter pizzaMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate pizzaTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(pizzaMessageConverter());
        return rabbitTemplate;
    }
}
